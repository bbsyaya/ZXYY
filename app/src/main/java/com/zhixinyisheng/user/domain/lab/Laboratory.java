package com.zhixinyisheng.user.domain.lab;

/**
 * 化验单实体类
 * Created by 焕焕 on 2017/2/22.
 */
public class Laboratory {
    private String chineseName;
    private String resultData;
    private String unit;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
