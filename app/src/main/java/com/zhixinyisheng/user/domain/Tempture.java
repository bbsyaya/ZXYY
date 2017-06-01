package com.zhixinyisheng.user.domain;

/**
 * Created by abert on 2016/8/5.
 * 类的作用：体温的实体类
 * 修改记录
 * 修改时间
 */
public class Tempture {
    String time;
    String tem_mor;
    String tem_aft;
    String tem_nit;

    public Tempture() {
    }

    public Tempture(String time, String tem_mor, String tem_aft, String tem_nit) {
        this.time = time;
        this.tem_mor = tem_mor;
        this.tem_aft = tem_aft;
        this.tem_nit = tem_nit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTem_mor() {
        return tem_mor;
    }

    public void setTem_mor(String tem_mor) {
        this.tem_mor = tem_mor;
    }

    public String getTem_aft() {
        return tem_aft;
    }

    public void setTem_aft(String tem_aft) {
        this.tem_aft = tem_aft;
    }

    public String getTem_nit() {
        return tem_nit;
    }

    public void setTem_nit(String tem_nit) {
        this.tem_nit = tem_nit;
    }

    @Override
    public String toString() {
        return "Tempture{" +
                "time='" + time + '\'' +
                ", tem_mor='" + tem_mor + '\'' +
                ", tem_aft='" + tem_aft + '\'' +
                ", tem_nit='" + tem_nit + '\'' +
                '}';
    }
}
