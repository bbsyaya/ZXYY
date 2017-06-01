package com.zhixinyisheng.user.domain.datas;

/**
 * 病毒检测实体类
 * Created by gjj on 2016/11/9.
 */
public class BduDatas {

    /**
     * CELL : +
     * VIRUSID : a7b232a991a44602990a086460b3445e
     * SH : +
     * USER_ID : d9b5b203eb98458cb31337abad51b1c3
     * EB : +
     * AIDS : +
     * CREATETIME : 1478692678000
     * BYTIME : 2016-11-09
     */

    private DataDBBean dataDB;
    /**
     * dataDB : {"CELL":"+","VIRUSID":"a7b232a991a44602990a086460b3445e","SH":"+","USER_ID":"d9b5b203eb98458cb31337abad51b1c3","EB":"+","AIDS":"+","CREATETIME":1478692678000,"BYTIME":"2016-11-09"}
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
        private String CELL;
        private String VIRUSID;
        private String SH;
        private String USER_ID;
        private String EB;
        private String AIDS;
        private long CREATETIME;
        private String BYTIME;

        public String getCELL() {
            return CELL;
        }

        public void setCELL(String CELL) {
            this.CELL = CELL;
        }

        public String getVIRUSID() {
            return VIRUSID;
        }

        public void setVIRUSID(String VIRUSID) {
            this.VIRUSID = VIRUSID;
        }

        public String getSH() {
            return SH;
        }

        public void setSH(String SH) {
            this.SH = SH;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getEB() {
            return EB;
        }

        public void setEB(String EB) {
            this.EB = EB;
        }

        public String getAIDS() {
            return AIDS;
        }

        public void setAIDS(String AIDS) {
            this.AIDS = AIDS;
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
    }
}
