package com.jinfang.golf.db;

/**
 * 散列表配置（每一个表对应一个配置）
 * @author 
 *
 */
public class GolfTablePartitionConfig {
	
	private String tableName;
	private String byColumn;
	private int partitions;
	private String targetPattern;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getByColumn() {
		return byColumn;
	}
	public void setByColumn(String byColumn) {
		this.byColumn = byColumn;
	}
	public int getPartitions() {
		return partitions;
	}
	public void setPartitions(int partitions) {
		this.partitions = partitions;
	}
	public String getTargetPattern() {
		return targetPattern;
	}
	public void setTargetPattern(String targetPattern) {
		this.targetPattern = targetPattern;
	}
	
	public boolean isValid(){
		//保证每个字段都有值
		return !(isEmpty(tableName) || isEmpty(byColumn) || partitions < 1 || isEmpty(targetPattern));
	}
	
	private boolean isEmpty(String str){
		return (str == null || str.length() == 0 || str.length() != str.trim().length());
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((byColumn == null) ? 0 : byColumn.hashCode());
		result = prime * result + partitions;
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result
				+ ((targetPattern == null) ? 0 : targetPattern.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GolfTablePartitionConfig other = (GolfTablePartitionConfig) obj;
		if (byColumn == null) {
			if (other.byColumn != null)
				return false;
		} else if (!byColumn.equals(other.byColumn))
			return false;
		if (partitions != other.partitions)
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (targetPattern == null) {
			if (other.targetPattern != null)
				return false;
		} else if (!targetPattern.equals(other.targetPattern))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		String json = "{\"tableName\":\"topic_follower\",\"byColumn\":\"topic_id\",\"partitions\":100,\"targetPattern\":\"topic_follower_{0}\"}";
		GolfTablePartitionConfig bean = new com.google.gson.Gson().fromJson(json, GolfTablePartitionConfig.class);
		System.out.println(bean.isValid());
	}
}