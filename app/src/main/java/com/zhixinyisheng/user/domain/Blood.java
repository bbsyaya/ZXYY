package com.zhixinyisheng.user.domain;

/**
 * Created by Abert on 2016/8/13.
 * 类的作用：存储血糖的实体类
 * 修改记录
 * 修改时间
 */
public class Blood {
    String date;
    String mor_before;
    String mor_before_color;
    String mor_after;
    String mor_after_color;
    String lun_before;
    String lun_before_color;
    String lun_after;
    String lun_after_color;
    String dinner_before;
    String dinner_before_color;
    String dinner_after;
    String dinner_after_color;
    String night;
    String night_color;

    public Blood(String date, String mor_before, String mor_before_color, String mor_after, String mor_after_color, String lun_before, String lun_before_color, String lun_after, String lun_after_color, String dinner_before, String dinner_before_color, String dinner_after, String dinner_after_color, String night, String night_color) {
        this.date = date;
        this.mor_before = mor_before;
        this.mor_before_color = mor_before_color;
        this.mor_after = mor_after;
        this.mor_after_color = mor_after_color;
        this.lun_before = lun_before;
        this.lun_before_color = lun_before_color;
        this.lun_after = lun_after;
        this.lun_after_color = lun_after_color;
        this.dinner_before = dinner_before;
        this.dinner_before_color = dinner_before_color;
        this.dinner_after = dinner_after;
        this.dinner_after_color = dinner_after_color;
        this.night = night;
        this.night_color = night_color;
    }

    public Blood() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMor_before() {
        return mor_before;
    }

    public void setMor_before(String mor_before) {
        this.mor_before = mor_before;
    }

    public String getMor_before_color() {
        return mor_before_color;
    }

    public void setMor_before_color(String mor_before_color) {
        this.mor_before_color = mor_before_color;
    }

    public String getMor_after() {
        return mor_after;
    }

    public void setMor_after(String mor_after) {
        this.mor_after = mor_after;
    }

    public String getMor_after_color() {
        return mor_after_color;
    }

    public void setMor_after_color(String mor_after_color) {
        this.mor_after_color = mor_after_color;
    }

    public String getLun_before() {
        return lun_before;
    }

    public void setLun_before(String lun_before) {
        this.lun_before = lun_before;
    }

    public String getLun_before_color() {
        return lun_before_color;
    }

    public void setLun_before_color(String lun_before_color) {
        this.lun_before_color = lun_before_color;
    }

    public String getLun_after() {
        return lun_after;
    }

    public void setLun_after(String lun_after) {
        this.lun_after = lun_after;
    }

    public String getLun_after_color() {
        return lun_after_color;
    }

    public void setLun_after_color(String lun_after_color) {
        this.lun_after_color = lun_after_color;
    }

    public String getDinner_before() {
        return dinner_before;
    }

    public void setDinner_before(String dinner_before) {
        this.dinner_before = dinner_before;
    }

    public String getDinner_before_color() {
        return dinner_before_color;
    }

    public void setDinner_before_color(String dinner_before_color) {
        this.dinner_before_color = dinner_before_color;
    }

    public String getDinner_after() {
        return dinner_after;
    }

    public void setDinner_after(String dinner_after) {
        this.dinner_after = dinner_after;
    }

    public String getDinner_after_color() {
        return dinner_after_color;
    }

    public void setDinner_after_color(String dinner_after_color) {
        this.dinner_after_color = dinner_after_color;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getNight_color() {
        return night_color;
    }

    public void setNight_color(String night_color) {
        this.night_color = night_color;
    }

    @Override
    public String toString() {
        return "Blood{" +
                "date='" + date + '\'' +
                ", mor_before='" + mor_before + '\'' +
                ", mor_before_color='" + mor_before_color + '\'' +
                ", mor_after='" + mor_after + '\'' +
                ", mor_after_color='" + mor_after_color + '\'' +
                ", lun_before='" + lun_before + '\'' +
                ", lun_before_color='" + lun_before_color + '\'' +
                ", lun_after='" + lun_after + '\'' +
                ", lun_after_color='" + lun_after_color + '\'' +
                ", dinner_before='" + dinner_before + '\'' +
                ", dinner_before_color='" + dinner_before_color + '\'' +
                ", dinner_after='" + dinner_after + '\'' +
                ", dinner_after_color='" + dinner_after_color + '\'' +
                ", night='" + night + '\'' +
                ", night_color='" + night_color + '\'' +
                '}';
    }
}
