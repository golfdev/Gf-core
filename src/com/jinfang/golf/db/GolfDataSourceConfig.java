package com.jinfang.golf.db;


/**
 * 数据源配置信息
 * @author 
 *
 */
public class GolfDataSourceConfig {
	
	private String type;
	private String database;
	private String host;
	private int port;
	private String user;
	private String password;
	private String wrflag;
	private String status = "disabled";
	private int initialSize = 1;
	private int maxActive = 20;
	protected int maxWait = 10000;
	private String token = "N"; //Y表拥有令牌表读写在该server，B表没有令牌的机器不被app访问到(该字段对读写在一台机器里有效,mode=2)
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWrflag() {
		return wrflag;
	}
	public void setWrflag(String wrflag) {
		this.wrflag = wrflag;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean hasToken(int mode){
		return (mode == 1 && "Y".equals(token)) || mode == 0 || mode == 2;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + initialSize;
		result = prime * result + maxActive;
		result = prime * result + maxWait;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + port;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((wrflag == null) ? 0 : wrflag.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GolfDataSourceConfig other = (GolfDataSourceConfig) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (initialSize != other.initialSize)
			return false;
		if (maxActive != other.maxActive)
			return false;
		if (maxWait != other.maxWait)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port != other.port)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (wrflag == null) {
			if (other.wrflag != null)
				return false;
		} else if (!wrflag.equals(other.wrflag))
			return false;
		return true;
	}
}
