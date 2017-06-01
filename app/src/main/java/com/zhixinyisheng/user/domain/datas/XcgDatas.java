package com.zhixinyisheng.user.domain.datas;

/**
 * 血常规实体类
 * Created by gjj on 2016/11/7.
 */
public class XcgDatas {

    /**
     * ADDTIME : 2016-11-07
     * BYTIME : 2016-11-07
     * CBC_ID : bbbd33233e3d4b09afb5eff346cd35fa
     * NEUT : 6
     * NEUTROPHIL : 3
     * OXYPHORASE : 6
     * OXYPHORASEFLAG : 1
     * RBC : 5
     * STATE : 1
     * USER_ID : 2
     * WBC : 2
     */

    private ListBean list;
    /**
     * list : {"ADDTIME":"2016-11-07","BYTIME":"2016-11-07","CBC_ID":"bbbd33233e3d4b09afb5eff346cd35fa","NEUT":"6","NEUTROPHIL":"3","OXYPHORASE":"6","OXYPHORASEFLAG":1,"RBC":"5","STATE":1,"USER_ID":"2","WBC":"2"}
     * result : 0000
     * retMessage : 操作成功！
     */

    private String result;
    private String retMessage;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
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

    public static class ListBean {
        private String ADDTIME;
        private String BYTIME;
        private String CBC_ID;
        private String NEUT;
        private String NEUTROPHIL;
        private String OXYPHORASE;
        private int OXYPHORASEFLAG;
        private String RBC;
        private int STATE;
        private String USER_ID;
        private String WBC;

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getCBC_ID() {
            return CBC_ID;
        }

        public void setCBC_ID(String CBC_ID) {
            this.CBC_ID = CBC_ID;
        }

        public String getNEUT() {
            return NEUT;
        }

        public void setNEUT(String NEUT) {
            this.NEUT = NEUT;
        }

        public String getNEUTROPHIL() {
            return NEUTROPHIL;
        }

        public void setNEUTROPHIL(String NEUTROPHIL) {
            this.NEUTROPHIL = NEUTROPHIL;
        }

        public String getOXYPHORASE() {
            return OXYPHORASE;
        }

        public void setOXYPHORASE(String OXYPHORASE) {
            this.OXYPHORASE = OXYPHORASE;
        }

        public int getOXYPHORASEFLAG() {
            return OXYPHORASEFLAG;
        }

        public void setOXYPHORASEFLAG(int OXYPHORASEFLAG) {
            this.OXYPHORASEFLAG = OXYPHORASEFLAG;
        }

        public String getRBC() {
            return RBC;
        }

        public void setRBC(String RBC) {
            this.RBC = RBC;
        }

        public int getSTATE() {
            return STATE;
        }

        public void setSTATE(int STATE) {
            this.STATE = STATE;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getWBC() {
            return WBC;
        }

        public void setWBC(String WBC) {
            this.WBC = WBC;
        }
    }
}
