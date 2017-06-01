package com.zhixinyisheng.user.domain.datas;

/**
 * 抗体实体类
 * Created by gjj on 2016/11/10.
 */
public class KtiDatas {

    /**
     * u1rnp : +
     * USER_ID : 2
     * antssB : +
     * antssA : +
     * antiSa : -
     * byTime : 2016-11-10
     * ana : +
     * createtime : 1478736994000
     * antibodyId : d6e0bfb086fb4ee9b499ccda961b5f44
     * zxlxbbj : +
     * dna : +
     * antscl70 : +
     * antiPM1 : +
     * aca : +
     */

    private DataDBBean dataDB;
    /**
     * dataDB : {"u1rnp":"+","USER_ID":"2","antssB":"+","antssA":"+","antiSa":"-","byTime":"2016-11-10","ana":"+","createtime":1478736994000,"antibodyId":"d6e0bfb086fb4ee9b499ccda961b5f44","zxlxbbj":"+","dna":"+","antscl70":"+","antiPM1":"+","aca":"+"}
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
        private String u1rnp;
        private String USER_ID;
        private String antssB;
        private String antssA;
        private String antiSa;
        private String byTime;
        private String ana;
        private long createtime;
        private String antibodyId;
        private String zxlxbbj;
        private String dna;
        private String antscl70;
        private String antiPM1;
        private String aca;

        public String getU1rnp() {
            return u1rnp;
        }

        public void setU1rnp(String u1rnp) {
            this.u1rnp = u1rnp;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getAntssB() {
            return antssB;
        }

        public void setAntssB(String antssB) {
            this.antssB = antssB;
        }

        public String getAntssA() {
            return antssA;
        }

        public void setAntssA(String antssA) {
            this.antssA = antssA;
        }

        public String getAntiSa() {
            return antiSa;
        }

        public void setAntiSa(String antiSa) {
            this.antiSa = antiSa;
        }

        public String getByTime() {
            return byTime;
        }

        public void setByTime(String byTime) {
            this.byTime = byTime;
        }

        public String getAna() {
            return ana;
        }

        public void setAna(String ana) {
            this.ana = ana;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getAntibodyId() {
            return antibodyId;
        }

        public void setAntibodyId(String antibodyId) {
            this.antibodyId = antibodyId;
        }

        public String getZxlxbbj() {
            return zxlxbbj;
        }

        public void setZxlxbbj(String zxlxbbj) {
            this.zxlxbbj = zxlxbbj;
        }

        public String getDna() {
            return dna;
        }

        public void setDna(String dna) {
            this.dna = dna;
        }

        public String getAntscl70() {
            return antscl70;
        }

        public void setAntscl70(String antscl70) {
            this.antscl70 = antscl70;
        }

        public String getAntiPM1() {
            return antiPM1;
        }

        public void setAntiPM1(String antiPM1) {
            this.antiPM1 = antiPM1;
        }

        public String getAca() {
            return aca;
        }

        public void setAca(String aca) {
            this.aca = aca;
        }
    }
}
