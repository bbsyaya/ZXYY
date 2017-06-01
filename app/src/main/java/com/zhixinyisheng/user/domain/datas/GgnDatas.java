package com.zhixinyisheng.user.domain.datas;

/**
 * 肝功能实体类
 * Created by gjj on 2016/11/8.
 */
public class GgnDatas {

    /**
     * result : 0000
     * list : {"HDL":"","STATE":1,"TBIL":"7","USER_ID":"2","ALT":"1","CHOLESTEROL":"","ADDTIME":"2016-11-08","GLOBULOSE":"6","BYTIME":"2016-11-08","PAT":"2","RICIM":"5","AST":"3","TPO":"4","TRIGLYCERIDE":"8","ALTFLAG":1,"LIVERF_ID":"cd4c2119c5b748e184e68d0a9b96cc3c"}
     * retMessage : 操作成功！
     */

    private String result;
    /**
     * HDL :
     * STATE : 1
     * TBIL : 7
     * USER_ID : 2
     * ALT : 1
     * CHOLESTEROL :
     * ADDTIME : 2016-11-08
     * GLOBULOSE : 6
     * BYTIME : 2016-11-08
     * PAT : 2
     * RICIM : 5
     * AST : 3
     * TPO : 4
     * TRIGLYCERIDE : 8
     * ALTFLAG : 1
     * LIVERF_ID : cd4c2119c5b748e184e68d0a9b96cc3c
     */

    private ListBean list;
    private String retMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class ListBean {
        private String HDL;
        private int STATE;
        private String TBIL;
        private String USER_ID;
        private String ALT;
        private String CHOLESTEROL;
        private String ADDTIME;
        private String GLOBULOSE;
        private String BYTIME;
        private String PAT;
        private String RICIM;
        private String AST;
        private String TPO;
        private String TRIGLYCERIDE;
        private int ALTFLAG;
        private String LIVERF_ID;

        public String getHDL() {
            return HDL;
        }

        public void setHDL(String HDL) {
            this.HDL = HDL;
        }

        public int getSTATE() {
            return STATE;
        }

        public void setSTATE(int STATE) {
            this.STATE = STATE;
        }

        public String getTBIL() {
            return TBIL;
        }

        public void setTBIL(String TBIL) {
            this.TBIL = TBIL;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getALT() {
            return ALT;
        }

        public void setALT(String ALT) {
            this.ALT = ALT;
        }

        public String getCHOLESTEROL() {
            return CHOLESTEROL;
        }

        public void setCHOLESTEROL(String CHOLESTEROL) {
            this.CHOLESTEROL = CHOLESTEROL;
        }

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getGLOBULOSE() {
            return GLOBULOSE;
        }

        public void setGLOBULOSE(String GLOBULOSE) {
            this.GLOBULOSE = GLOBULOSE;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getPAT() {
            return PAT;
        }

        public void setPAT(String PAT) {
            this.PAT = PAT;
        }

        public String getRICIM() {
            return RICIM;
        }

        public void setRICIM(String RICIM) {
            this.RICIM = RICIM;
        }

        public String getAST() {
            return AST;
        }

        public void setAST(String AST) {
            this.AST = AST;
        }

        public String getTPO() {
            return TPO;
        }

        public void setTPO(String TPO) {
            this.TPO = TPO;
        }

        public String getTRIGLYCERIDE() {
            return TRIGLYCERIDE;
        }

        public void setTRIGLYCERIDE(String TRIGLYCERIDE) {
            this.TRIGLYCERIDE = TRIGLYCERIDE;
        }

        public int getALTFLAG() {
            return ALTFLAG;
        }

        public void setALTFLAG(int ALTFLAG) {
            this.ALTFLAG = ALTFLAG;
        }

        public String getLIVERF_ID() {
            return LIVERF_ID;
        }

        public void setLIVERF_ID(String LIVERF_ID) {
            this.LIVERF_ID = LIVERF_ID;
        }
    }
}
