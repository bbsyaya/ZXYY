package com.zhixinyisheng.user.domain.datas;

/**
 * 尿常规
 * Created by gjj on 2016/11/10.
 */
public class NcgDatas {

    /**
     * result : 0000
     * list : {"REDBLOODCELL":"2","USER_ID":"2","PROTEIN":"","URINALYSIS_ID":"a1a84cb5922c4ef4a40d905a2cbb7ee9","ADDTIME":"2016-11-10","BYTIME":"2016-11-10","HEMAMEBA":"3","KET":"","SREC":"9","GLUCOSE":"","UBG":"","EPITHELIALCELL":"4","PROPORTION":"2","TUBE":"5","BLD":"","PATHCAST":"8","HEMAMEBAHIGH":"7","REDBLOODCELLHIGH":"6"}
     * retMessage : 操作成功！
     */

    private String result;
    /**
     * REDBLOODCELL : 2
     * USER_ID : 2
     * PROTEIN :
     * URINALYSIS_ID : a1a84cb5922c4ef4a40d905a2cbb7ee9
     * ADDTIME : 2016-11-10
     * BYTIME : 2016-11-10
     * HEMAMEBA : 3
     * KET :
     * SREC : 9
     * GLUCOSE :
     * UBG :
     * EPITHELIALCELL : 4
     * PROPORTION : 2
     * TUBE : 5
     * BLD :
     * PATHCAST : 8
     * HEMAMEBAHIGH : 7
     * REDBLOODCELLHIGH : 6
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
        private String REDBLOODCELL;
        private String USER_ID;
        private String PROTEIN;
        private String URINALYSIS_ID;
        private String ADDTIME;
        private String BYTIME;
        private String HEMAMEBA;
        private String KET;
        private String SREC;
        private String GLUCOSE;
        private String UBG;
        private String EPITHELIALCELL;
        private String PROPORTION;
        private String TUBE;
        private String BLD;
        private String PATHCAST;
        private String HEMAMEBAHIGH;
        private String REDBLOODCELLHIGH;

        public String getREDBLOODCELL() {
            return REDBLOODCELL;
        }

        public void setREDBLOODCELL(String REDBLOODCELL) {
            this.REDBLOODCELL = REDBLOODCELL;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getPROTEIN() {
            return PROTEIN;
        }

        public void setPROTEIN(String PROTEIN) {
            this.PROTEIN = PROTEIN;
        }

        public String getURINALYSIS_ID() {
            return URINALYSIS_ID;
        }

        public void setURINALYSIS_ID(String URINALYSIS_ID) {
            this.URINALYSIS_ID = URINALYSIS_ID;
        }

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

        public String getHEMAMEBA() {
            return HEMAMEBA;
        }

        public void setHEMAMEBA(String HEMAMEBA) {
            this.HEMAMEBA = HEMAMEBA;
        }

        public String getKET() {
            return KET;
        }

        public void setKET(String KET) {
            this.KET = KET;
        }

        public String getSREC() {
            return SREC;
        }

        public void setSREC(String SREC) {
            this.SREC = SREC;
        }

        public String getGLUCOSE() {
            return GLUCOSE;
        }

        public void setGLUCOSE(String GLUCOSE) {
            this.GLUCOSE = GLUCOSE;
        }

        public String getUBG() {
            return UBG;
        }

        public void setUBG(String UBG) {
            this.UBG = UBG;
        }

        public String getEPITHELIALCELL() {
            return EPITHELIALCELL;
        }

        public void setEPITHELIALCELL(String EPITHELIALCELL) {
            this.EPITHELIALCELL = EPITHELIALCELL;
        }

        public String getPROPORTION() {
            return PROPORTION;
        }

        public void setPROPORTION(String PROPORTION) {
            this.PROPORTION = PROPORTION;
        }

        public String getTUBE() {
            return TUBE;
        }

        public void setTUBE(String TUBE) {
            this.TUBE = TUBE;
        }

        public String getBLD() {
            return BLD;
        }

        public void setBLD(String BLD) {
            this.BLD = BLD;
        }

        public String getPATHCAST() {
            return PATHCAST;
        }

        public void setPATHCAST(String PATHCAST) {
            this.PATHCAST = PATHCAST;
        }

        public String getHEMAMEBAHIGH() {
            return HEMAMEBAHIGH;
        }

        public void setHEMAMEBAHIGH(String HEMAMEBAHIGH) {
            this.HEMAMEBAHIGH = HEMAMEBAHIGH;
        }

        public String getREDBLOODCELLHIGH() {
            return REDBLOODCELLHIGH;
        }

        public void setREDBLOODCELLHIGH(String REDBLOODCELLHIGH) {
            this.REDBLOODCELLHIGH = REDBLOODCELLHIGH;
        }
    }
}
