package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 解析查询出的化验单的地址
 * Created by 焕焕 on 2016/11/9.
 */
public class HuaYanDanUrl {

    /**
     * list : [{"ADDTIME":"2016-11-09","PICTUREINFO_ID":"0bf55f28791b40b899128ac86c874b52","PSAN":"zhixinyisheng","TYPE":10,"URL":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/ffa3f7b5319341abb58518ff95cff400.jpg","userId":"2c0fcf1171e1423097814250c23edceb"},{"ADDTIME":"2016-11-09","PICTUREINFO_ID":"33c5168bf39245259ca285320566f09d","PSAN":"zhixinyisheng","TYPE":10,"URL":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/12f7c63c81254117a89fe725910aae49.jpg","userId":"2c0fcf1171e1423097814250c23edceb"},{"ADDTIME":"2016-11-09","PICTUREINFO_ID":"40232d6476ed40e79f77fd6546176986","PSAN":"zhixinyisheng","TYPE":10,"URL":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/5566c2988bfe47f3bfb80a1d8a1e2eae.jpg","userId":"2c0fcf1171e1423097814250c23edceb"}]
     * result : 0000
     * returnMessage : 查询成功
     * size : 3
     */

    private String result;
    private String returnMessage;
    private int size;
    /**
     * ADDTIME : 2016-11-09
     * PICTUREINFO_ID : 0bf55f28791b40b899128ac86c874b52
     * PSAN : zhixinyisheng
     * TYPE : 10
     * URL : http://222.222.24.133:8099/picResource/sbs/user/2016-11/ffa3f7b5319341abb58518ff95cff400.jpg
     * userId : 2c0fcf1171e1423097814250c23edceb
     */

    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
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
        private String ADDTIME;
        private String PICTUREINFO_ID;
        private String PSAN;
        private int TYPE;
        private String URL;
        private String userId;

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getPICTUREINFO_ID() {
            return PICTUREINFO_ID;
        }

        public void setPICTUREINFO_ID(String PICTUREINFO_ID) {
            this.PICTUREINFO_ID = PICTUREINFO_ID;
        }

        public String getPSAN() {
            return PSAN;
        }

        public void setPSAN(String PSAN) {
            this.PSAN = PSAN;
        }

        public int getTYPE() {
            return TYPE;
        }

        public void setTYPE(int TYPE) {
            this.TYPE = TYPE;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
