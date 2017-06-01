package com.zhixinyisheng.user.domain;

/**
 * 返回的标准数据类型实体基类
 */
public class ResultInfo {
    private String result;
    private String returnMessage;
    private String db;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

}
