package com.zhixinyisheng.user.domain.datas;

import java.util.List;

/**
 * 血糖实体类
 * Created by 焕焕 on 2017/1/13.
 */
public class BloodSugerDatas {

    /**
     * list : [{"BREAKFASTAFTER":"9.0","BREAKFASTAFTERC":1,"BREAKFASTBEFORE":"8.0","BREAKFASTBEFOREC":3,"DINNERAFTER":"13.0","DINNERAFTERC":3,"DINNERBEFORE":"12.0","DINNERBEFOREC":3,"LUNCHAFTER":"11.0","LUNCHAFTERC":2,"LUNCHBEFORE":"10.0","LUNCHBEFOREC":3,"SLEEPBEFORE":"14.0","SLEEPBEFOREC":3,"USER_ID":"a443872d4f474e3987bd774f5553be28","bloodSugarID":"3304f00a8da140eca18bc312043e3f51","byTime":"2017-01-13","colorFlag":3,"createTime":1484295386000,"rangeValue":3,"type":1}]
     * result : 0000
     * size : 1
     */

    private String result;
    private int size;
    /**
     * BREAKFASTAFTER : 9.0
     * BREAKFASTAFTERC : 1
     * BREAKFASTBEFORE : 8.0
     * BREAKFASTBEFOREC : 3
     * DINNERAFTER : 13.0
     * DINNERAFTERC : 3
     * DINNERBEFORE : 12.0
     * DINNERBEFOREC : 3
     * LUNCHAFTER : 11.0
     * LUNCHAFTERC : 2
     * LUNCHBEFORE : 10.0
     * LUNCHBEFOREC : 3
     * SLEEPBEFORE : 14.0
     * SLEEPBEFOREC : 3
     * USER_ID : a443872d4f474e3987bd774f5553be28
     * bloodSugarID : 3304f00a8da140eca18bc312043e3f51
     * byTime : 2017-01-13
     * colorFlag : 3
     * createTime : 1484295386000
     * rangeValue : 3
     * type : 1
     */

    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String BREAKFASTAFTER;
        private int BREAKFASTAFTERC;
        private String BREAKFASTBEFORE;
        private int BREAKFASTBEFOREC;
        private String DINNERAFTER;
        private int DINNERAFTERC;
        private String DINNERBEFORE;
        private int DINNERBEFOREC;
        private String LUNCHAFTER;
        private int LUNCHAFTERC;
        private String LUNCHBEFORE;
        private int LUNCHBEFOREC;
        private String SLEEPBEFORE;
        private int SLEEPBEFOREC;
        private String USER_ID;
        private String bloodSugarID;
        private String byTime;
        private int colorFlag;
        private long createTime;
        private int rangeValue;
        private int type;

        public String getBREAKFASTAFTER() {
            return BREAKFASTAFTER;
        }

        public void setBREAKFASTAFTER(String BREAKFASTAFTER) {
            this.BREAKFASTAFTER = BREAKFASTAFTER;
        }

        public int getBREAKFASTAFTERC() {
            return BREAKFASTAFTERC;
        }

        public void setBREAKFASTAFTERC(int BREAKFASTAFTERC) {
            this.BREAKFASTAFTERC = BREAKFASTAFTERC;
        }

        public String getBREAKFASTBEFORE() {
            return BREAKFASTBEFORE;
        }

        public void setBREAKFASTBEFORE(String BREAKFASTBEFORE) {
            this.BREAKFASTBEFORE = BREAKFASTBEFORE;
        }

        public int getBREAKFASTBEFOREC() {
            return BREAKFASTBEFOREC;
        }

        public void setBREAKFASTBEFOREC(int BREAKFASTBEFOREC) {
            this.BREAKFASTBEFOREC = BREAKFASTBEFOREC;
        }

        public String getDINNERAFTER() {
            return DINNERAFTER;
        }

        public void setDINNERAFTER(String DINNERAFTER) {
            this.DINNERAFTER = DINNERAFTER;
        }

        public int getDINNERAFTERC() {
            return DINNERAFTERC;
        }

        public void setDINNERAFTERC(int DINNERAFTERC) {
            this.DINNERAFTERC = DINNERAFTERC;
        }

        public String getDINNERBEFORE() {
            return DINNERBEFORE;
        }

        public void setDINNERBEFORE(String DINNERBEFORE) {
            this.DINNERBEFORE = DINNERBEFORE;
        }

        public int getDINNERBEFOREC() {
            return DINNERBEFOREC;
        }

        public void setDINNERBEFOREC(int DINNERBEFOREC) {
            this.DINNERBEFOREC = DINNERBEFOREC;
        }

        public String getLUNCHAFTER() {
            return LUNCHAFTER;
        }

        public void setLUNCHAFTER(String LUNCHAFTER) {
            this.LUNCHAFTER = LUNCHAFTER;
        }

        public int getLUNCHAFTERC() {
            return LUNCHAFTERC;
        }

        public void setLUNCHAFTERC(int LUNCHAFTERC) {
            this.LUNCHAFTERC = LUNCHAFTERC;
        }

        public String getLUNCHBEFORE() {
            return LUNCHBEFORE;
        }

        public void setLUNCHBEFORE(String LUNCHBEFORE) {
            this.LUNCHBEFORE = LUNCHBEFORE;
        }

        public int getLUNCHBEFOREC() {
            return LUNCHBEFOREC;
        }

        public void setLUNCHBEFOREC(int LUNCHBEFOREC) {
            this.LUNCHBEFOREC = LUNCHBEFOREC;
        }

        public String getSLEEPBEFORE() {
            return SLEEPBEFORE;
        }

        public void setSLEEPBEFORE(String SLEEPBEFORE) {
            this.SLEEPBEFORE = SLEEPBEFORE;
        }

        public int getSLEEPBEFOREC() {
            return SLEEPBEFOREC;
        }

        public void setSLEEPBEFOREC(int SLEEPBEFOREC) {
            this.SLEEPBEFOREC = SLEEPBEFOREC;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getBloodSugarID() {
            return bloodSugarID;
        }

        public void setBloodSugarID(String bloodSugarID) {
            this.bloodSugarID = bloodSugarID;
        }

        public String getByTime() {
            return byTime;
        }

        public void setByTime(String byTime) {
            this.byTime = byTime;
        }

        public int getColorFlag() {
            return colorFlag;
        }

        public void setColorFlag(int colorFlag) {
            this.colorFlag = colorFlag;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getRangeValue() {
            return rangeValue;
        }

        public void setRangeValue(int rangeValue) {
            this.rangeValue = rangeValue;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
