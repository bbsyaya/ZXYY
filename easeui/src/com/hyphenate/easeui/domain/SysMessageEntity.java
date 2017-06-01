package com.hyphenate.easeui.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/3  10:05
 * <p/>
 * 类说明: 系统消息实体类
 */
public class SysMessageEntity {

    /**
     * db : 1
     * list : [{"card":5994,"content":"您的好友元亚心的尿常规数据异常，请及时联系。","createTime":"2017-02-13 10:50","flag":0,"frinedsRemark":"","fromUserId":"27e1800f13a34698b58e4ad2d9b16c02","headUrl":"http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png","isDoctor":1,"name":"元亚心","payedUserID":0,"state":0,"sysMessageId":"ec0dcb8795894532b3a9fb91e1f714ad","title":"异常数据","toUserId":"4949162d803642eeb8f5ac81c683a8c4","type":6,"username":"老苑"},{"content":"恭喜您！您的医生认证资料已审核通过！","createTime":"2017-02-11 16:14","flag":1,"fromUserId":"知心医生后台自动推送","state":0,"sysMessageId":"547c08f7b41e4733b0b47404d4bc9b4d","title":"医师认证结果","toUserId":"4949162d803642eeb8f5ac81c683a8c4","type":7},{"card":5999,"content":"您的好友小女孩的尿常规数据异常，请及时联系。","createTime":"2017-02-11 15:33","flag":1,"frinedsRemark":"","fromUserId":"61bf534a669b4742baefba926da3a576","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/df89fdb08eb84c75b095cf0780a238a8.png","isDoctor":0,"name":"","payedUserID":0,"state":0,"sysMessageId":"bfb245d068024152a2492437d92db2ea","title":"异常数据","toUserId":"4949162d803642eeb8f5ac81c683a8c4","type":6,"username":"小女孩"}]
     * result : 0000
     * returnMessage : 成功
     * size : 3
     */

    private int db;
    private String result;
    private String returnMessage;
    private int size;
    /**
     * card : 5994
     * content : 您的好友元亚心的尿常规数据异常，请及时联系。
     * createTime : 2017-02-13 10:50
     * flag : 0
     * frinedsRemark :
     * fromUserId : 27e1800f13a34698b58e4ad2d9b16c02
     * headUrl : http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png
     * isDoctor : 1
     * name : 元亚心
     * payedUserID : 0
     * state : 0
     * sysMessageId : ec0dcb8795894532b3a9fb91e1f714ad
     * title : 异常数据
     * toUserId : 4949162d803642eeb8f5ac81c683a8c4
     * type : 6
     * username : 老苑
     */

    private List<ListBean> list;

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

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

    public static class ListBean implements Serializable{
        private int card;
        private String content;
        private String createTime;
        private int flag;
        private String frinedsRemark;
        private String fromUserId;
        private String headUrl;
        private int isDoctor;
        private String name;
        private int payedUserID;
        private int state;
        private String sysMessageId;
        private String title;
        private String toUserId;
        private int type;
        private String username;

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPayedUserID() {
            return payedUserID;
        }

        public void setPayedUserID(int payedUserID) {
            this.payedUserID = payedUserID;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSysMessageId() {
            return sysMessageId;
        }

        public void setSysMessageId(String sysMessageId) {
            this.sysMessageId = sysMessageId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
