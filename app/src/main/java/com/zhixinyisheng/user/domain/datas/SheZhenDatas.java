package com.zhixinyisheng.user.domain.datas;

import java.util.List;

/**
 * 舌诊实体类
 * Created by 焕焕 on 2016/11/30.
 */
public class SheZhenDatas {

    /**
     * list : [{"createTime":1480470057000,"picALL":"D:/server/online/Tomcat 7.0_shenboshi/webapps/picResource/sbs/tongue/2016-11/5d35d0140b684092ab58097e7f8ff1ed.png","rgbId":22,"state":0,"tdatasId":"69703a07ef8d403b9fba599fda2ab366","userId":"a443872d4f474e3987bd774f5553be28","userPic":"http://222.222.24.133:8099/picResource/sbs/tongue/2016-11/5d35d0140b684092ab58097e7f8ff1ed.png","userResult":"此舌象显示您\u201c淡红舌、薄白苔\u201d，恭喜您，很健康！"}]
     * result : 0000
     * retMessage : 操作成功
     * size : 1
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * createTime : 1480470057000
     * picALL : D:/server/online/Tomcat 7.0_shenboshi/webapps/picResource/sbs/tongue/2016-11/5d35d0140b684092ab58097e7f8ff1ed.png
     * rgbId : 22
     * state : 0
     * tdatasId : 69703a07ef8d403b9fba599fda2ab366
     * userId : a443872d4f474e3987bd774f5553be28
     * userPic : http://222.222.24.133:8099/picResource/sbs/tongue/2016-11/5d35d0140b684092ab58097e7f8ff1ed.png
     * userResult : 此舌象显示您“淡红舌、薄白苔”，恭喜您，很健康！
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
        private long createTime;
        private String picALL;
        private int rgbId;
        private int state;
        private String tdatasId;
        private String userId;
        private String userPic;
        private String userResult;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPicALL() {
            return picALL;
        }

        public void setPicALL(String picALL) {
            this.picALL = picALL;
        }

        public int getRgbId() {
            return rgbId;
        }

        public void setRgbId(int rgbId) {
            this.rgbId = rgbId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTdatasId() {
            return tdatasId;
        }

        public void setTdatasId(String tdatasId) {
            this.tdatasId = tdatasId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUserResult() {
            return userResult;
        }

        public void setUserResult(String userResult) {
            this.userResult = userResult;
        }
    }
}
