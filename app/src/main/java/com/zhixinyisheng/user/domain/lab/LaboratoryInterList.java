package com.zhixinyisheng.user.domain.lab;

/**
 * Created by 焕焕 on 2017/2/25.
 */
public class LaboratoryInterList {
    private String name;
    private String data;
    private String unit;
    private boolean isZheXianTu;
    private String colorFlag;

    public String getColorFlag() {
        return colorFlag;
    }

    public void setColorFlag(String colorFlag) {
        this.colorFlag = colorFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isZheXianTu() {
        return isZheXianTu;
    }

    public void setZheXianTu(boolean zheXianTu) {
        isZheXianTu = zheXianTu;
    }
}
