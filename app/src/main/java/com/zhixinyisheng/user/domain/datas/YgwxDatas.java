package com.zhixinyisheng.user.domain.datas;

/**
 * 乙肝五项
 * Created by gjj on 2016/11/10.
 */
public class YgwxDatas {

    /**
     * HBVID : 353abf025bd945039982a4cc4ee4135b
     * COLORFLAG : -1
     * USER_ID : 2
     * HBEAG : +
     * CREATETIME : 1478739193000
     * BYTIME : 2016-11-10
     * HBC : +
     * HBS : +
     * HBE : +
     * HBSAG : +
     */

    private DataDBBean dataDB;
    /**
     * dataDB : {"HBVID":"353abf025bd945039982a4cc4ee4135b","COLORFLAG":-1,"USER_ID":"2","HBEAG":"+","CREATETIME":1478739193000,"BYTIME":"2016-11-10","HBC":"+","HBS":"+","HBE":"+","HBSAG":"+"}
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
        private String HBVID;
        private int COLORFLAG;
        private String USER_ID;
        private String HBEAG;
        private long CREATETIME;
        private String BYTIME;
        private String HBC;
        private String HBS;
        private String HBE;
        private String HBSAG;

        public String getHBVID() {
            return HBVID;
        }

        public void setHBVID(String HBVID) {
            this.HBVID = HBVID;
        }

        public int getCOLORFLAG() {
            return COLORFLAG;
        }

        public void setCOLORFLAG(int COLORFLAG) {
            this.COLORFLAG = COLORFLAG;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getHBEAG() {
            return HBEAG;
        }

        public void setHBEAG(String HBEAG) {
            this.HBEAG = HBEAG;
        }

        public long getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(long CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getHBC() {
            return HBC;
        }

        public void setHBC(String HBC) {
            this.HBC = HBC;
        }

        public String getHBS() {
            return HBS;
        }

        public void setHBS(String HBS) {
            this.HBS = HBS;
        }

        public String getHBE() {
            return HBE;
        }

        public void setHBE(String HBE) {
            this.HBE = HBE;
        }

        public String getHBSAG() {
            return HBSAG;
        }

        public void setHBSAG(String HBSAG) {
            this.HBSAG = HBSAG;
        }
    }
}
