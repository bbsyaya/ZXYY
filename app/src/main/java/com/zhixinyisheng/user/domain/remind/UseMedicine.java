package com.zhixinyisheng.user.domain.remind;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public class UseMedicine implements Parcelable{

    public UseMedicine() {
    }

    /**
     * result : 0000
     * list : [{"medicineId":"322afbf473ab45c3aaa9ee82ab4af32a","alertTime":"09:12,09:15","medicienName":"看看","count":"KKK","remark":"微信","isEat":0,"beginTime":"2017-04-21","medenimeAndPushId":461,"endTime":"2017-04-25","alertTimeBefore":1492737120000},{"medicineId":"322afbf473ab45c3aaa9ee82ab4af32a","alertTime":"09:12,09:15","medicienName":"看看","count":"KKK","remark":"微信","isEat":0,"beginTime":"2017-04-21","medenimeAndPushId":462,"endTime":"2017-04-25","alertTimeBefore":1492737300000},{"medicineId":"7f066fa4922647e6af4802230127d96a","alertTime":"09:19","medicienName":"啊","count":"哦哦哦","remark":"有备注","isEat":0,"beginTime":"2017-04-21","medenimeAndPushId":472,"endTime":"2017-04-21","alertTimeBefore":1492737540000}]
     * size : 3
     */


    private String result;
    private int size;
    private List<ListBean> list;

    protected UseMedicine(Parcel in) {
        result = in.readString();
        size = in.readInt();
        list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<UseMedicine> CREATOR = new Creator<UseMedicine>() {
        @Override
        public UseMedicine createFromParcel(Parcel in) {
            return new UseMedicine(in);
        }

        @Override
        public UseMedicine[] newArray(int size) {
            return new UseMedicine[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeInt(size);
        dest.writeTypedList(list);
    }

    public static class ListBean implements Parcelable{
        public ListBean() {
        }

        /**

         * medicineId : 322afbf473ab45c3aaa9ee82ab4af32a
         * alertTime : 09:12,09:15
         * medicienName : 看看
         * count : KKK
         * remark : 微信
         * isEat : 0
         * beginTime : 2017-04-21
         * medenimeAndPushId : 461
         * endTime : 2017-04-25
         * alertTimeBefore : 1492737120000
         */

        private String medicineId;
        private String alertTime;
        private String medicienName;
        private String count;
        private String remark;
        private int isEat;
        private String beginTime;
        private int medenimeAndPushId;
        private String endTime;
        private long alertTimeBefore;

        protected ListBean(Parcel in) {
            medicineId = in.readString();
            alertTime = in.readString();
            medicienName = in.readString();
            count = in.readString();
            remark = in.readString();
            isEat = in.readInt();
            beginTime = in.readString();
            medenimeAndPushId = in.readInt();
            endTime = in.readString();
            alertTimeBefore = in.readLong();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getMedicineId() {
            return medicineId;
        }

        public void setMedicineId(String medicineId) {
            this.medicineId = medicineId;
        }

        public String getAlertTime() {
            return alertTime;
        }

        public void setAlertTime(String alertTime) {
            this.alertTime = alertTime;
        }

        public String getMedicienName() {
            return medicienName;
        }

        public void setMedicienName(String medicienName) {
            this.medicienName = medicienName;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getIsEat() {
            return isEat;
        }

        public void setIsEat(int isEat) {
            this.isEat = isEat;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public int getMedenimeAndPushId() {
            return medenimeAndPushId;
        }

        public void setMedenimeAndPushId(int medenimeAndPushId) {
            this.medenimeAndPushId = medenimeAndPushId;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public long getAlertTimeBefore() {
            return alertTimeBefore;
        }

        public void setAlertTimeBefore(long alertTimeBefore) {
            this.alertTimeBefore = alertTimeBefore;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(medicineId);
            dest.writeString(alertTime);
            dest.writeString(medicienName);
            dest.writeString(count);
            dest.writeString(remark);
            dest.writeInt(isEat);
            dest.writeString(beginTime);
            dest.writeInt(medenimeAndPushId);
            dest.writeString(endTime);
            dest.writeLong(alertTimeBefore);
        }
    }
}
