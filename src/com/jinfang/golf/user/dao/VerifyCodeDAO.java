package com.jinfang.golf.user.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.user.model.VerifyCode;
/**
 * VerifyCodeDAO操作数据库对象
 * @author ZK
 *
 */
@DAO(catalog="golf_app")
public interface VerifyCodeDAO {
	public static String tableName="verify_code";
	public static String fieldName="number,code,expried_time";
	
	/**
	 * insert增加方法 在verify_code表中增加一条记录
	 * @param verifyCode  要添加的verifyCode对象
	 * @return 返回保存成功后的int数
	 */
	@SQL("insert into "+tableName+" ("+fieldName+") values(:1.number,:1.code,:1.expriedTime)")
	public int insert(VerifyCode verifyCode);
	
	/**
	 * update修改方法 修改verify_code表中数据
	 * @param verifyCode 被修改的verifyCode对象
	 */
	@SQL("update "+tableName+" set code=:1.code,expried_time=:1.expriedTime where number=:1.number")
	public void update(VerifyCode verifyCode);
	
	/**
	 * delete删除方法 删除verify_code中数据
	 * @param number 要删除的电话号码数据
	 */
	@SQL("delete from "+tableName+" where number=:1")
	public void delete(String number);
	
	/**
	 * get查询方法 查询verify_code表中数据
	 * @param number 要查询的电话号码的数据库数据
	 * @return 返回list集合
	 */
	@SQL("select "+fieldName+" from "+tableName+" where number=:1")
	public List<VerifyCode> get(String number);

}
