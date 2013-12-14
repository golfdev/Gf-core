package com.jinfang.golf.db;

import java.text.MessageFormat;
import java.util.Map;

import javax.sql.DataSource;

import net.paoding.rose.jade.annotation.ShardParam;
import net.paoding.rose.jade.core.SQLThreadLocal;
import net.paoding.rose.jade.exql.ExprResolver;
import net.paoding.rose.jade.exql.impl.ExprResolverImpl;
import net.paoding.rose.jade.provider.Modifier;
import net.paoding.rose.jade.provider.SQLInterpreter;
import net.paoding.rose.jade.provider.SQLInterpreterResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.util.Assert;

import com.meidusa.amoeba.parser.dbobject.Column;
import com.meidusa.amoeba.parser.dbobject.Table;

/**
 * 实现SQLInterpreter，主要找出sql中的tableName看是否有散表，如有散表就根据散表参数值来修改talbeName
 * ，使用Jade时需配置到applicationContext-jade.xml中
 * @author 
 *
 */
//按Spring语义规定，Order值越高该解释器越后执行
@Order(9000)
public class RouterInterpreter implements SQLInterpreter {

 private static final Log logger = LogFactory.getLog(RouterInterpreter.class);

 public SQLInterpreterResult interpret(DataSource dataSource, final String sql,
         Modifier modifier, Map<String, Object> parametersAsMap, final Object[] parametersAsArray) {
	 
     if (dataSource instanceof DelegatingDataSource) {
         dataSource = ((DelegatingDataSource) dataSource).getTargetDataSource();
     }
     if (!(dataSource instanceof GolfDataSource)) {
         return null;
     }
     Assert.notNull(parametersAsArray,
             "need parametersAsArray prepared before invoking this interpreter!");
     GolfDataSource gummyDataSource = (GolfDataSource) dataSource;
     //String bizName = gummyDataSource.getBizName();
     if (logger.isDebugEnabled()) {
         logger.debug("Invoking analyzing: " + sql);
     }
     SQLParseInfo parseInfo = SQLParseInfo.getParseInfo(sql);
     // 从查询的数据表获取路由配置。
     Table[] tables = parseInfo.getTables();
     
     //RoutingInfo routingInfo = null;
     Table partitionTable = null;
     GolfTablePartitionConfig tblPartitionCfg = null;
     //
     if (tables != null) {
         int beginIndex = 0;
         if (parseInfo.isInsert() && tables.length > 1) {
             // INSERT ... SELECT 查询
             beginIndex = 1;
         }

         // 查找散表配置
         for (int i = beginIndex; i < tables.length; i++) {
        	 GolfTablePartitionConfig p = gummyDataSource.getPartitionInfoByTable(tables[i].getName());
        	 if(p != null){
        		 partitionTable = tables[i];
        		 tblPartitionCfg = p;
        		 break;
        	 }
         }
     }
     if (tblPartitionCfg == null) {
         return null;
     } else {
         if (logger.isDebugEnabled()) {
             logger.debug("Find routing info: " + tblPartitionCfg.getTableName() + ", "
                     + tblPartitionCfg.getByColumn());
         }
     }
     String forwardTableName = null;

     if (tblPartitionCfg != null) {
         //用语句信息的常量进行散表。
         Column column = new Column();
         column.setName(tblPartitionCfg.getByColumn());
         column.setTable(partitionTable);
         Object columnValue = null;

         columnValue = findShardParamValue(parseInfo, column, parametersAsMap, parametersAsArray);
         if (columnValue == null) {
             throw new BadSqlGrammarException("sharding", parseInfo.getSQL(), null);
         }

         // 获得散表的名称 (注： 只考虑Hash散列的情况)
         forwardTableName = doHashRouter(tblPartitionCfg.getTargetPattern(),tblPartitionCfg.getPartitions(), tblPartitionCfg.getByColumn(), columnValue);
     }

     String byTableName = tblPartitionCfg != null ? tblPartitionCfg.getTableName() : null;
     final String sqlRewrited;
     if ((forwardTableName != null) && !forwardTableName.equals(byTableName)) {

         // 使用  SqlRewriter 拆分语句，进行所需的查找和替换。
         sqlRewrited = SqlRewriter.rewriteSqlTable(sql, byTableName, forwardTableName);

         // 输出重写日志
         if (logger.isDebugEnabled()) {
             logger.debug("Rewriting SQL: \n  From: " + sql + "\n  To:   " + sqlRewrited);
         }
     } else {
         sqlRewrited = sql;
     }
     return new RouterSQLInterpreterResult(sqlRewrited, parametersAsArray);
     
 }
 
	 private String doHashRouter(String pattern, int partitions,String column, Object columnValue) {
		 long longValue = -1;
		 if (columnValue instanceof Number) {
			 longValue = ((Number) columnValue).longValue();
	     } else {
	         try {
	             // 转换成字符串处理
	        	 longValue = Long.parseLong(String.valueOf(columnValue));
	         } catch (NumberFormatException e) {
	             if (logger.isWarnEnabled()) {
	                 logger.warn("Column \'" + column + "\' must be number, but: " + columnValue);
	             }
	
	             throw new BadSqlGrammarException("HashRouter.convert", "Column \'" + column  
	                     + "\' must be number, but: " + columnValue, null);
	         }
	    }
		
		 int value = (int) (longValue % partitions);
		 String name = MessageFormat.format(pattern, value);

         // 输出日志
         if (logger.isDebugEnabled()) {
             logger.debug("Routing on [" + column + " = " + columnValue + ", "
                     + columnValue.getClass() + "]: " + name);
         }

         return name;
	}

	
// 查找散表参数
 protected static Object findShardParamValue(SQLParseInfo parseInfo, Column column,
         Map<String, Object> parametersAsMap, Object[] parametersAsArray) {

     SQLThreadLocal local = SQLThreadLocal.get();

     String shardBy = local.getModifier().getShardBy();
     Object value = null;
     if (shardBy != null) {
         value = parametersAsMap.get(shardBy);
         if (logger.isDebugEnabled()) {
             logger.debug("get shard param value '" + value + "' by @ShardBy (" + shardBy + ")");
         }
         return value;
     } else {
         ShardParam shardParam = local.getModifier().getAnnotation(ShardParam.class);
         if (shardParam != null) {
             // 检查外部提供的散表参数
             Column shardColumn = SQLParseInfo.newColumn(shardParam.name());

             try {
                 ExprResolver exprResolver = new ExprResolverImpl(parametersAsMap);
                 value = exprResolver.executeExpr(shardParam.value());

             } catch (Exception e) {
                 if (logger.isWarnEnabled()) {
                     logger.warn("Can't execute @ShardParam expr: " + shardParam.value(), e);
                 }
                 throw new InvalidDataAccessApiUsageException("Can't execute @ShardParam expr: "
                         + shardParam.value(), e);
             }

             if (ShardParam.WIDECARD.equals(shardColumn.getName())) {
                 // 匹配所有散表列
                 return value;

             } else if (shardColumn.getTable() == null) {

                 // 模糊的匹配散表列
                 if (shardColumn.getName().equals(column.getName())) {
                     return value;
                 }

             } else if (shardColumn.equals(column)) {
                 // 精确的匹配散表列
                 return value;
             }
             // 如果针对该列进行散表，则必须包含该列作为查询条件。
             throw new BadSqlGrammarException("interpreter.findShardParamValue@ShardParam",
                     "SQL [" + parseInfo.getSQL() + "] Query without shard parameter: " // NL
                             + column.getSql(), null);

         } else if (parseInfo.containsParam(column)) {
             // 获取语句中的散表参数
             value = parseInfo.getParam(column);
             if (value != null) {
                 if (logger.isDebugEnabled()) {
                     logger.debug("find shard param value '" + value + "' by column "
                             + column.getName() + " [constants]");
                 }
             }
             if (value == null) {
                 int index = parseInfo.getColumnIndex(column) - 1;
                 if (index >= 0 && index < parametersAsArray.length) {
                     value = parametersAsArray[index];
                     if (logger.isDebugEnabled()) {
                         logger.debug("find shard param value '" + value + "' by column's arg "
                                 + column.getName() + " [index=" + index + " (beginwiths 0)]");
                     }
                 } else {
                     // 如果针对该列进行散表，则必须包含该列作为查询条件。
                     throw new BadSqlGrammarException("interpreter.findShardParamValue", "SQL ["
                             + parseInfo.getSQL() + "] Query without shard parameter: " // NL
                             + column.getSql(), null);
                 }

             }
             return value;
         } else {

             // 如果针对该列进行散表，则必须包含该列作为查询条件。
             throw new BadSqlGrammarException("interpreter.findShardParamValue", "SQL ["
                     + parseInfo.getSQL() + "] Query without shard parameter: " // NL
                     + column.getSql(), null);
         }
     }
 }
}
