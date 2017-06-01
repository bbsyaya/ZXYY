package com.zhixinyisheng.user.domain.datas;

/**
 * 凝血检测实体类
 * Created by gjj on 2016/11/9.
 */
public class NxueDatas {

    /**
     * createTime : 1478686357000
     * dd : 5
     * aptt : 4
     * USER_ID : d9b5b203eb98458cb31337abad51b1c3
     * tt : 1
     * pt : 2
     * byTime : 2016-11-09
     * cruorId : 314f214e117b4cf58e762269fab1f531
     * colorFlag : 1
     * fib : 3
     */

    private DataDBBean dataDB;
    /**
     * dataDB : {"createTime":1478686357000,"dd":"5","aptt":"4","USER_ID":"d9b5b203eb98458cb31337abad51b1c3","tt":"1","pt":"2","byTime":"2016-11-09","cruorId":"314f214e117b4cf58e762269fab1f531","colorFlag":1,"fib":"3"}
     * result : 0000
     * retMessage : 查询成功
     */

    private String result;
    private String retMessage;

    public DataDBBean getDataDB() {
        return dataDB;
    }

    public void setDataDB(DataDBBean dataDB) {
        this.dataDB = dataDB;
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

    public static class DataDBBean {
        private long createTime;
        private String dd;
        private String aptt;
        private String USER_ID;
        private String tt;
        private String pt;
        private String byTime;
        private String cruorId;
        private int colorFlag;
        private String fib;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDd() {
            return dd;
        }

        public void setDd(String dd) {
            this.dd = dd;
        }

        public String getAptt() {
            return aptt;
        }

        public void setAptt(String aptt) {
            this.aptt = aptt;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getTt() {
            return tt;
        }

        public void setTt(String tt) {
            this.tt = tt;
        }

        public String getPt() {
            return pt;
        }

        public void setPt(String pt) {
            this.pt = pt;
        }

        public String getByTime() {
            return byTime;
        }

        public void setByTime(String byTime) {
            this.byTime = byTime;
        }

        public String getCruorId() {
            return cruorId;
        }

        public void setCruorId(String cruorId) {
            this.cruorId = cruorId;
        }

        public int getColorFlag() {
            return colorFlag;
        }

        public void setColorFlag(int colorFlag) {
            this.colorFlag = colorFlag;
        }

        public String getFib() {
            return fib;
        }

        public void setFib(String fib) {
            this.fib = fib;
        }
    }
}
