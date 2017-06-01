package com.zhixinyisheng.user.domain.datas;

/**
 * 肾功能实体类
 * Created by gjj on 2016/11/8.
 */
public class SgnDatas {

    /**
     * ADDTIME : 2016-11-08
     * BYTIME : 2016-11-08
     * CREATININE : 2
     * CREATININEFLAG : 1
     * CYSTATINC :
     * HCY :
     * MICROGLOBULIN :
     * RENALF_ID : 66ce4940a4cf477b890847838fff8842
     * STATE : 1
     * UA : 3
     * UREANITROGEN : 1
     * UREANITROGENFLAG : 1
     * USER_ID : 2
     */

    private ListBean list;
    /**
     * list : {"ADDTIME":"2016-11-08","BYTIME":"2016-11-08","CREATININE":"2","CREATININEFLAG":1,"CYSTATINC":"","HCY":"","MICROGLOBULIN":"","RENALF_ID":"66ce4940a4cf477b890847838fff8842","STATE":1,"UA":"3","UREANITROGEN":"1","UREANITROGENFLAG":1,"USER_ID":"2"}
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
        private String CREATININE;
        private int CREATININEFLAG;
        private String CYSTATINC;
        private String HCY;
        private String MICROGLOBULIN;
        private String RENALF_ID;
        private int STATE;
        private String UA;
        private String UREANITROGEN;
        private int UREANITROGENFLAG;
        private String USER_ID;

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

        public String getCREATININE() {
            return CREATININE;
        }

        public void setCREATININE(String CREATININE) {
            this.CREATININE = CREATININE;
        }

        public int getCREATININEFLAG() {
            return CREATININEFLAG;
        }

        public void setCREATININEFLAG(int CREATININEFLAG) {
            this.CREATININEFLAG = CREATININEFLAG;
        }

        public String getCYSTATINC() {
            return CYSTATINC;
        }

        public void setCYSTATINC(String CYSTATINC) {
            this.CYSTATINC = CYSTATINC;
        }

        public String getHCY() {
            return HCY;
        }

        public void setHCY(String HCY) {
            this.HCY = HCY;
        }

        public String getMICROGLOBULIN() {
            return MICROGLOBULIN;
        }

        public void setMICROGLOBULIN(String MICROGLOBULIN) {
            this.MICROGLOBULIN = MICROGLOBULIN;
        }

        public String getRENALF_ID() {
            return RENALF_ID;
        }

        public void setRENALF_ID(String RENALF_ID) {
            this.RENALF_ID = RENALF_ID;
        }

        public int getSTATE() {
            return STATE;
        }

        public void setSTATE(int STATE) {
            this.STATE = STATE;
        }

        public String getUA() {
            return UA;
        }

        public void setUA(String UA) {
            this.UA = UA;
        }

        public String getUREANITROGEN() {
            return UREANITROGEN;
        }

        public void setUREANITROGEN(String UREANITROGEN) {
            this.UREANITROGEN = UREANITROGEN;
        }

        public int getUREANITROGENFLAG() {
            return UREANITROGENFLAG;
        }

        public void setUREANITROGENFLAG(int UREANITROGENFLAG) {
            this.UREANITROGENFLAG = UREANITROGENFLAG;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }
    }
}
