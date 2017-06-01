package com.zhixinyisheng.user.domain.datas;

/**
 * 免疫球蛋白实体类
 * Created by gjj on 2016/11/8.
 */
public class MyqdbDatas {


    /**
     * result : 0000
     * list : {"PICTURE":"","BTC4":"","BTC3":"9","STATE":1,"IGG":"1","USER_ID":"2","IGM":"3","IGK":"4","BTB":"7","IGA":"2","COQ":"6","REMARK":"","ADDTIME":"2016-11-08","CH50":"8","BYTIME":"2016-11-08","IMMUNE_ID":"c9b0ba4fb6d4417fa3301255d2041e0c","CFYDB":"","IGR":"5"}
     * retMessage : 操作成功！
     */

    private String result;
    /**
     * PICTURE :
     * BTC4 :
     * BTC3 : 9
     * STATE : 1
     * IGG : 1
     * USER_ID : 2
     * IGM : 3
     * IGK : 4
     * BTB : 7
     * IGA : 2
     * COQ : 6
     * REMARK :
     * ADDTIME : 2016-11-08
     * CH50 : 8
     * BYTIME : 2016-11-08
     * IMMUNE_ID : c9b0ba4fb6d4417fa3301255d2041e0c
     * CFYDB :
     * IGR : 5
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
        private String PICTURE;
        private String BTC4;
        private String BTC3;
        private int STATE;
        private String IGG;
        private String USER_ID;
        private String IGM;
        private String IGK;
        private String BTB;
        private String IGA;
        private String COQ;
        private String REMARK;
        private String ADDTIME;
        private String CH50;
        private String BYTIME;
        private String IMMUNE_ID;
        private String CFYDB;
        private String IGR;

        public String getPICTURE() {
            return PICTURE;
        }

        public void setPICTURE(String PICTURE) {
            this.PICTURE = PICTURE;
        }

        public String getBTC4() {
            return BTC4;
        }

        public void setBTC4(String BTC4) {
            this.BTC4 = BTC4;
        }

        public String getBTC3() {
            return BTC3;
        }

        public void setBTC3(String BTC3) {
            this.BTC3 = BTC3;
        }

        public int getSTATE() {
            return STATE;
        }

        public void setSTATE(int STATE) {
            this.STATE = STATE;
        }

        public String getIGG() {
            return IGG;
        }

        public void setIGG(String IGG) {
            this.IGG = IGG;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getIGM() {
            return IGM;
        }

        public void setIGM(String IGM) {
            this.IGM = IGM;
        }

        public String getIGK() {
            return IGK;
        }

        public void setIGK(String IGK) {
            this.IGK = IGK;
        }

        public String getBTB() {
            return BTB;
        }

        public void setBTB(String BTB) {
            this.BTB = BTB;
        }

        public String getIGA() {
            return IGA;
        }

        public void setIGA(String IGA) {
            this.IGA = IGA;
        }

        public String getCOQ() {
            return COQ;
        }

        public void setCOQ(String COQ) {
            this.COQ = COQ;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getCH50() {
            return CH50;
        }

        public void setCH50(String CH50) {
            this.CH50 = CH50;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getIMMUNE_ID() {
            return IMMUNE_ID;
        }

        public void setIMMUNE_ID(String IMMUNE_ID) {
            this.IMMUNE_ID = IMMUNE_ID;
        }

        public String getCFYDB() {
            return CFYDB;
        }

        public void setCFYDB(String CFYDB) {
            this.CFYDB = CFYDB;
        }

        public String getIGR() {
            return IGR;
        }

        public void setIGR(String IGR) {
            this.IGR = IGR;
        }
    }
}
