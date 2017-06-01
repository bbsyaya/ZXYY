package com.hyphenate.easeui.domain;

import java.util.List;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/12  10:32
 * <p/>
 * 类说明:
 */
public class FriendsEntity  {

    /**
     * result : 0000
     * careList : [{"datas":0,"createTime":"2016-10-16","phone":"15251254425","username":"dnn","toUserId":"3","friendsId":"6","isDoctor":0,"state":1,"agreeFlag":1,"headUrl":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=860590796,1721820213&fm=80&w=179&h=119&img.PNG","fromUserId":"2","frinedsRemark":"东江南","twoDimension":"二维码董江宁","sos":0},{"datas":0,"createTime":"2016-11-05","phone":"13624159613","username":"","toUserId":"dbb38516fb8e49edb51fdd5f2d75b645","friendsId":"64959ee6a6fe4c5d911989eb4f14c709","isDoctor":0,"state":1,"agreeFlag":0,"headUrl":"D:/server/online/Tomcat 7.0_shenboshi/webapps/picResource/sbs/common/HEADURL.png","fromUserId":"2","frinedsRemark":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/02078efa20ee43ef86222cabfa904a51090404.png","sos":0},{"datas":0,"createTime":"2016-11-05","phone":"13463962153","username":"","toUserId":"a0255593c31c4b829b054a3d39b06451","friendsId":"7c326e29f8be482990972a25492e2c16","isDoctor":0,"state":1,"agreeFlag":0,"headUrl":"http://222.222.12.186:8082/picResource/sbs/common/DoctorHeadURL.png","fromUserId":"2","frinedsRemark":"","twoDimension":"http://222.222.12.186:8082/picResource/sbs/patient/2016-10/3cb5d272e77c46648b87a7c061286433135337.png","sos":0}]
     * list : [{"datas":0,"createTime":"2016-11-05","phone":"15081126813","username":"wj","toUserId":"75b8e7253dbc42759f506b280e39de6c","friendsId":"14e5d27b70a74375886a01adbcc0ef76","isDoctor":0,"state":0,"agreeFlag":0,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/09e8347525604fdaa2c78fbf097c292f.jpg","fromUserId":"2","frinedsRemark":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/24aba009846c40e0a4e8176b80907b0a115147.png","sos":0},{"datas":0,"createTime":"2016-11-05","phone":"15010973395","username":"沧海间","toUserId":"7cb271b4d8864e0db943107f4cf49ac7","friendsId":"84fe0815c2c145958d8eefee34b97dd5","isDoctor":0,"state":0,"agreeFlag":0,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/c6572f9483a1477e939db34ec01a56a6.jpg","fromUserId":"2","frinedsRemark":"沧海间","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/b5176714212f4c71b421b1ca1d4ece34153908.png","sos":0},{"datas":0,"createTime":"2016-11-09","phone":"15373011312","username":"","toUserId":"82ce3bd175984373a1ba3668da16f619","friendsId":"89dcc144056745a29df5b04db6a32192","isDoctor":0,"state":0,"agreeFlag":0,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/363bdc8b75e0406db86987bdf3490c4c.png","fromUserId":"2","frinedsRemark":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/88b07fa37b254ab98e24f4b4e2d6825e101041.png","sos":0},{"datas":0,"createTime":"2016-11-05","phone":"13831128915","username":"爱吃月亮果","toUserId":"7566626570b04a24836ee8b0b7b17cf9","friendsId":"cd6ab75ae295440f9937dd68f963c42d","isDoctor":0,"state":0,"agreeFlag":0,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/eea60dddfa294f59891c7190243aef79.jpg","fromUserId":"2","frinedsRemark":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/28f1507f10ae4a7aa799129c7d4a6d19114218.png","sos":0}]
     * careSize : 3
     * size : 4
     */

    private String result;
    private int careSize;
    private int size;
    /**
     * datas : 0
     * createTime : 2016-10-16
     * phone : 15251254425
     * username : dnn
     * toUserId : 3
     * friendsId : 6
     * isDoctor : 0
     * state : 1
     * agreeFlag : 1
     * headUrl : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=860590796,1721820213&fm=80&w=179&h=119&img.PNG
     * fromUserId : 2
     * frinedsRemark : 东江南
     * twoDimension : 二维码董江宁
     * sos : 0
     */

    private List<CareListBean> careList;
    /**
     * datas : 0
     * createTime : 2016-11-05
     * phone : 15081126813
     * username : wj
     * toUserId : 75b8e7253dbc42759f506b280e39de6c
     * friendsId : 14e5d27b70a74375886a01adbcc0ef76
     * isDoctor : 0
     * state : 0
     * agreeFlag : 0
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/09e8347525604fdaa2c78fbf097c292f.jpg
     * fromUserId : 2
     * frinedsRemark :
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-10/24aba009846c40e0a4e8176b80907b0a115147.png
     * sos : 0
     */

    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCareSize() {
        return careSize;
    }

    public void setCareSize(int careSize) {
        this.careSize = careSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CareListBean> getCareList() {
        return careList;
    }

    public void setCareList(List<CareListBean> careList) {
        this.careList = careList;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CareListBean {
        private int datas;
        private String createTime;
        private String phone;
        private String username;
        private String toUserId;
        private String friendsId;
        private int isDoctor;
        private int state;
        private int agreeFlag;
        private String headUrl;
        private String fromUserId;
        private String frinedsRemark;
        private String twoDimension;
        private int sos;

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }
    }

    public static class ListBean {
        private int datas;
        private String createTime;
        private String phone;
        private String username;
        private String toUserId;
        private String friendsId;
        private int isDoctor;
        private int state;
        private int agreeFlag;
        private String headUrl;
        private String fromUserId;
        private String frinedsRemark;
        private String twoDimension;
        private int sos;

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }
    }
}
