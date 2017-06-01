package com.zhixinyisheng.user.domain.datas;

/**
 * 电解质
 * Created by gjj on 2016/11/10.
 */
public class DianjzDatas {

    /**
     * calcium : 5
     * magnesium : 4
     * USER_ID : 2
     * phosphorus : 6
     * phosphorusFlag : 3
     * sodium : 2
     * bytime : 2016-11-10
     * carbonDioxide : 7
     * createtime : 1478741499000
     * electrolyteId : 0dc2906493e14e758e4e55d2d89da70f
     * AG : 8
     * calciumFlag : 3
     * potassium : 1
     * potassiumFlag : 3
     * chlorin : 3
     */

    private DataDBBean dataDB;
    /**
     * dataDB : {"calcium":"5","magnesium":"4","USER_ID":"2","phosphorus":"6","phosphorusFlag":3,"sodium":"2","bytime":"2016-11-10","carbonDioxide":"7","createtime":1478741499000,"electrolyteId":"0dc2906493e14e758e4e55d2d89da70f","AG":"8","calciumFlag":3,"potassium":"1","potassiumFlag":3,"chlorin":"3"}
     * result : 0000
     * retMessage : 成功
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
        private String calcium;
        private String magnesium;
        private String USER_ID;
        private String phosphorus;
        private int phosphorusFlag;
        private String sodium;
        private String bytime;
        private String carbonDioxide;
        private long createtime;
        private String electrolyteId;
        private String AG;
        private int calciumFlag;
        private String potassium;
        private int potassiumFlag;
        private String chlorin;

        public String getCalcium() {
            return calcium;
        }

        public void setCalcium(String calcium) {
            this.calcium = calcium;
        }

        public String getMagnesium() {
            return magnesium;
        }

        public void setMagnesium(String magnesium) {
            this.magnesium = magnesium;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getPhosphorus() {
            return phosphorus;
        }

        public void setPhosphorus(String phosphorus) {
            this.phosphorus = phosphorus;
        }

        public int getPhosphorusFlag() {
            return phosphorusFlag;
        }

        public void setPhosphorusFlag(int phosphorusFlag) {
            this.phosphorusFlag = phosphorusFlag;
        }

        public String getSodium() {
            return sodium;
        }

        public void setSodium(String sodium) {
            this.sodium = sodium;
        }

        public String getBytime() {
            return bytime;
        }

        public void setBytime(String bytime) {
            this.bytime = bytime;
        }

        public String getCarbonDioxide() {
            return carbonDioxide;
        }

        public void setCarbonDioxide(String carbonDioxide) {
            this.carbonDioxide = carbonDioxide;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getElectrolyteId() {
            return electrolyteId;
        }

        public void setElectrolyteId(String electrolyteId) {
            this.electrolyteId = electrolyteId;
        }

        public String getAG() {
            return AG;
        }

        public void setAG(String AG) {
            this.AG = AG;
        }

        public int getCalciumFlag() {
            return calciumFlag;
        }

        public void setCalciumFlag(int calciumFlag) {
            this.calciumFlag = calciumFlag;
        }

        public String getPotassium() {
            return potassium;
        }

        public void setPotassium(String potassium) {
            this.potassium = potassium;
        }

        public int getPotassiumFlag() {
            return potassiumFlag;
        }

        public void setPotassiumFlag(int potassiumFlag) {
            this.potassiumFlag = potassiumFlag;
        }

        public String getChlorin() {
            return chlorin;
        }

        public void setChlorin(String chlorin) {
            this.chlorin = chlorin;
        }
    }
}
