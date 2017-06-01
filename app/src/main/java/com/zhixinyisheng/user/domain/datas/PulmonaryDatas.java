package com.zhixinyisheng.user.domain.datas;

import java.util.List;

/**
 * 肺活量
 * Created by 焕焕 on 2017/1/14.
 */
public class PulmonaryDatas {

    /**
     * list : [{"BYTIME":"2017-01-14","CREATETIME":1484376764000,"NUM":608,"USER_ID":"a443872d4f474e3987bd774f5553be28","VITALCAPACITYID":"056601508d044f38a043838ae9a97fe0"},{"BYTIME":"2017-01-14","CREATETIME":1484376771000,"NUM":38,"USER_ID":"a443872d4f474e3987bd774f5553be28","VITALCAPACITYID":"6d0e981e35974eb39840f8b782da3d68"},{"BYTIME":"2017-01-14","CREATETIME":1484376791000,"NUM":38,"USER_ID":"a443872d4f474e3987bd774f5553be28","VITALCAPACITYID":"ac1ea570fca045b5a338920194a7563c"}]
     * result : 0000
     * retMessage : 操作成功！
     */

    private String result;
    private String retMessage;
    /**
     * BYTIME : 2017-01-14
     * CREATETIME : 1484376764000
     * NUM : 608.0
     * USER_ID : a443872d4f474e3987bd774f5553be28
     * VITALCAPACITYID : 056601508d044f38a043838ae9a97fe0
     */

    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String BYTIME;
        private long CREATETIME;
        private double NUM;
        private String USER_ID;
        private String VITALCAPACITYID;

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public long getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(long CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public double getNUM() {
            return NUM;
        }

        public void setNUM(double NUM) {
            this.NUM = NUM;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getVITALCAPACITYID() {
            return VITALCAPACITYID;
        }

        public void setVITALCAPACITYID(String VITALCAPACITYID) {
            this.VITALCAPACITYID = VITALCAPACITYID;
        }
    }
}
