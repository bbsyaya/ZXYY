package com.and.yzy.frame.update;

/**
 * 版本控制
 */
public class Version {

    /**
     * createTime : 1477033500000
     * downloadURL : 222.222.12.186:8082/picResource/apk/baobei.apk
     * id : 2
     * version : 1.0.1
     */

    private DbBean db;
    /**
     * db : {"createTime":1477033500000,"downloadURL":"222.222.12.186:8082/picResource/apk/baobei.apk","id":2,"version":"1.0.1"}
     * result : 2000
     * retMessage : 需要更新
     */

    private String result;
    private String retMessage;

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class DbBean {
        private long createTime;
        private String downloadURL;
        private int id;
        private String version;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
