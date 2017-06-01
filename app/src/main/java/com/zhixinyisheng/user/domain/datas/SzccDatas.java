package com.zhixinyisheng.user.domain.datas;

/**
 * 肾脏彩超
 * Created by gjj on 2016/11/10.
 */
public class SzccDatas {
    /**
     * result : 0000
     * list : {"STATE":1,"USER_ID":"2","TBSES":"6","LEFTKIDNEYFLAG":1,"CSYSTEM":"566","ADDTIME":"2016-11-10","BYTIME":"2016-11-10","RCTK":"2","TRKBF":"566","TBAA":"5","RIGHTKIDNEY":"4*5*6","RIGHTKIDNEYFLAG":1,"KIDNEYCTE_ID":"0e5b7e29834a4ffaa7ec0582bd882061","LEFTKIDNEY":"1*2*3","TLKBF":"55"}
     * retMessage : 操作成功！
     */

    private String result;
    /**
     * STATE : 1
     * USER_ID : 2
     * TBSES : 6
     * LEFTKIDNEYFLAG : 1
     * CSYSTEM : 566
     * ADDTIME : 2016-11-10
     * BYTIME : 2016-11-10
     * RCTK : 2
     * TRKBF : 566
     * TBAA : 5
     * RIGHTKIDNEY : 4*5*6
     * RIGHTKIDNEYFLAG : 1
     * KIDNEYCTE_ID : 0e5b7e29834a4ffaa7ec0582bd882061
     * LEFTKIDNEY : 1*2*3
     * TLKBF : 55
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
        private int STATE;
        private String USER_ID;
        private String TBSES;
        private int LEFTKIDNEYFLAG;
        private String CSYSTEM;
        private String ADDTIME;
        private String BYTIME;
        private String RCTK;
        private String TRKBF;
        private String TBAA;
        private String RIGHTKIDNEY;
        private int RIGHTKIDNEYFLAG;
        private String KIDNEYCTE_ID;
        private String LEFTKIDNEY;
        private String TLKBF;

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

        public String getTBSES() {
            return TBSES;
        }

        public void setTBSES(String TBSES) {
            this.TBSES = TBSES;
        }

        public int getLEFTKIDNEYFLAG() {
            return LEFTKIDNEYFLAG;
        }

        public void setLEFTKIDNEYFLAG(int LEFTKIDNEYFLAG) {
            this.LEFTKIDNEYFLAG = LEFTKIDNEYFLAG;
        }

        public String getCSYSTEM() {
            return CSYSTEM;
        }

        public void setCSYSTEM(String CSYSTEM) {
            this.CSYSTEM = CSYSTEM;
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

        public String getRCTK() {
            return RCTK;
        }

        public void setRCTK(String RCTK) {
            this.RCTK = RCTK;
        }

        public String getTRKBF() {
            return TRKBF;
        }

        public void setTRKBF(String TRKBF) {
            this.TRKBF = TRKBF;
        }

        public String getTBAA() {
            return TBAA;
        }

        public void setTBAA(String TBAA) {
            this.TBAA = TBAA;
        }

        public String getRIGHTKIDNEY() {
            return RIGHTKIDNEY;
        }

        public void setRIGHTKIDNEY(String RIGHTKIDNEY) {
            this.RIGHTKIDNEY = RIGHTKIDNEY;
        }

        public int getRIGHTKIDNEYFLAG() {
            return RIGHTKIDNEYFLAG;
        }

        public void setRIGHTKIDNEYFLAG(int RIGHTKIDNEYFLAG) {
            this.RIGHTKIDNEYFLAG = RIGHTKIDNEYFLAG;
        }

        public String getKIDNEYCTE_ID() {
            return KIDNEYCTE_ID;
        }

        public void setKIDNEYCTE_ID(String KIDNEYCTE_ID) {
            this.KIDNEYCTE_ID = KIDNEYCTE_ID;
        }

        public String getLEFTKIDNEY() {
            return LEFTKIDNEY;
        }

        public void setLEFTKIDNEY(String LEFTKIDNEY) {
            this.LEFTKIDNEY = LEFTKIDNEY;
        }

        public String getTLKBF() {
            return TLKBF;
        }

        public void setTLKBF(String TLKBF) {
            this.TLKBF = TLKBF;
        }
    }
}
