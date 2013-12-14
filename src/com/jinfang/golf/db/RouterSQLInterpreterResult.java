package com.jinfang.golf.db;

import net.paoding.rose.jade.provider.SQLInterpreterResult;

public class RouterSQLInterpreterResult implements SQLInterpreterResult {

    private String sql;

    private Object[] parameters;

    public RouterSQLInterpreterResult(String sql, Object[] parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getSQL() {
        return sql;
    }
}