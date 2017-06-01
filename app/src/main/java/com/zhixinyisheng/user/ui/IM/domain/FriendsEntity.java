package com.zhixinyisheng.user.ui.IM.domain;

import java.util.List;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/26  11:42
 * <p/>
 * 类说明: IM 好友
 */
public class FriendsEntity {

    /**
     * careList : [{"agreeFlag":1,"canSosSet":0,"card":555555960,"createTime":"2017-02-04","datas":1,"friendsId":"fe5bcfa7217f4a1d8fbd27a3bd54e99a","frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/eea60dddfa294f59891c7190243aef79.jpg","isDoctor":1,"name":"王晓佩","payedUserID":1,"phone":"13831128915","sos":0,"state":1,"toUserId":"7566626570b04a24836ee8b0b7b17cf9","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/28f1507f10ae4a7aa799129c7d4a6d19114218.png","username":"爱吃月亮果"}]
     * careSize : 1
     * doctorList : [{"agreeFlag":1,"canSosSet":0,"card":555555975,"createTime":"2017-02-09","datas":1,"friendsId":"13ce967792644028ae8d1d07b200eb0d","frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-02/1d77ace73b13a4212b1005ae9eb573399.jpg","isDoctor":1,"name":"陈老师","payedUserID":1,"phone":"15010973395","sos":0,"state":0,"toUserId":"9803cab67f2c412fb69be3a8a9ef79ac","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/4bfb999a7864423d8b7a52edf30b411d161043.png","username":"沧海间"},{"agreeFlag":1,"canSosSet":1,"card":555555972,"createTime":"2017-02-08","datas":1,"friendsId":"4aa41ae13e9b476a9cac69bcd83ca876","frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-02/63c54ee4ea474d76bc62a4d64b4b3651.png","isDoctor":1,"name":"剧京路","payedUserID":1,"phone":"18031150493","sos":0,"state":0,"toUserId":"a443872d4f474e3987bd774f5553be28","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/3a74fa7eaa6a468ca9ba2a7615ee0b7e144029.png","username":"剧京"},{"agreeFlag":1,"canSosSet":0,"card":555555986,"createTime":"2017-02-04","datas":1,"friendsId":"d092ebb281a64f0ca157968dcd0ec77d","frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-02/187864401679b403a82f043c4982c1833.jpg","isDoctor":1,"name":"12","payedUserID":1,"phone":"13463962152","sos":0,"state":0,"toUserId":"159d7d91fd7a43dcac63c7f795b72791","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-12/d022a7d29aa74bcd89066677ab26d42c093228.png","username":"旧的"}]
     * doctorSize : 3
     * list : [{"agreeFlag":1,"card":555555974,"createTime":"2017-02-09","datas":1,"friendsId":"269de7f65677460e857efc67ee12cfd1","frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/134000e89d3842b7898b2969cba0a719.png","isDoctor":1,"name":"","payedUserID":0,"phone":"15081826721","sos":0,"state":0,"toUserId":"ca5dfc7e45284136b3700e3ce08aba3f","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/10c77b44584c4360995a0a1974c872a8152042.png","username":"我是小聪聪"},{"agreeFlag":1,"card":555555990,"createTime":"2017-02-09","datas":0,"friendsId":"be65f3b5226743a88699b32583278825","frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/116adacd187fa4049b34a1fa95d6fa24a.jpg","isDoctor":1,"name":"仙女姐姐","payedUserID":0,"phone":"13463962153","sos":0,"state":0,"toUserId":"d46ef2ce949a4a5baff1030c43cbb5a7","twoDimension":"http://222.223.218.50:8337/myPic/zhixinyisheng/user/2017-01/69b50031108d419994025676d7cf0d5d171812.png","username":""}]
     * patientList : [{"agreeFlag":1,"card":555555977,"createTime":"2017-01-24","datas":1,"friendsId":"05603856af744f6cba8ae7adec6baf45","frinedsRemark":"Berlin","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/5f2d71c7556243478eb0b20e9276703e.png","isDoctor":0,"name":"","payedUserID":2,"phone":"18510871104","sos":0,"state":0,"toUserId":"6814238dddd4414e944ca178aff758c0","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/15b7602986914088ab4f9b024f7f46fa140849.png","username":"胖子"},{"agreeFlag":1,"card":555555972,"createTime":"2017-02-08","datas":0,"friendsId":"450d5d8f2d794e53b9a76783922e1506","frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-02/63c54ee4ea474d76bc62a4d64b4b3651.png","isDoctor":1,"name":"剧京路","payedUserID":2,"phone":"18031150493","sos":0,"state":0,"toUserId":"a443872d4f474e3987bd774f5553be28","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/3a74fa7eaa6a468ca9ba2a7615ee0b7e144029.png","username":"剧京"},{"agreeFlag":1,"card":555555988,"createTime":"2017-02-09","datas":0,"friendsId":"e57438f446434257bde2745318654657","frinedsRemark":"","headUrl":"http://222.223.218.50:8337/myPic/zhixinyisheng/common/HEADURL.png","isDoctor":0,"name":"","payedUserID":2,"phone":"13463962155","sos":0,"state":0,"toUserId":"9a89f92dd08248e78e1d241ebc0e9d04","twoDimension":"http://222.223.218.50:8337/myPic/zhixinyisheng/user/2017-01/db1dbef0b56545d2b087186d1e0d1ec2113844.png","username":"天空之城"}]
     * patientSize : 3
     * result : 0000
     * size : 2
     */

    private int careSize;
    private int doctorSize;
    private int patientSize;
    private String result;
    private int size;
    /**
     * agreeFlag : 1
     * canSosSet : 0
     * card : 555555960
     * createTime : 2017-02-04
     * datas : 1
     * friendsId : fe5bcfa7217f4a1d8fbd27a3bd54e99a
     * frinedsRemark :
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/eea60dddfa294f59891c7190243aef79.jpg
     * isDoctor : 1
     * name : 王晓佩
     * payedUserID : 1
     * phone : 13831128915
     * sos : 0
     * state : 1
     * toUserId : 7566626570b04a24836ee8b0b7b17cf9
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-10/28f1507f10ae4a7aa799129c7d4a6d19114218.png
     * username : 爱吃月亮果
     */

    private List<CareListBean> careList;
    /**
     * agreeFlag : 1
     * canSosSet : 0
     * card : 555555975
     * createTime : 2017-02-09
     * datas : 1
     * friendsId : 13ce967792644028ae8d1d07b200eb0d
     * frinedsRemark :
     * headUrl : http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-02/1d77ace73b13a4212b1005ae9eb573399.jpg
     * isDoctor : 1
     * name : 陈老师
     * payedUserID : 1
     * phone : 15010973395
     * sos : 0
     * state : 0
     * toUserId : 9803cab67f2c412fb69be3a8a9ef79ac
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-11/4bfb999a7864423d8b7a52edf30b411d161043.png
     * username : 沧海间
     */

    private List<DoctorListBean> doctorList;
    /**
     * agreeFlag : 1
     * card : 555555974
     * createTime : 2017-02-09
     * datas : 1
     * friendsId : 269de7f65677460e857efc67ee12cfd1
     * frinedsRemark :
     * headUrl : http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/134000e89d3842b7898b2969cba0a719.png
     * isDoctor : 1
     * name :
     * payedUserID : 0
     * phone : 15081826721
     * sos : 0
     * state : 0
     * toUserId : ca5dfc7e45284136b3700e3ce08aba3f
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-11/10c77b44584c4360995a0a1974c872a8152042.png
     * username : 我是小聪聪
     */

    private List<ListBean> list;
    /**
     * agreeFlag : 1
     * card : 555555977
     * createTime : 2017-01-24
     * datas : 1
     * friendsId : 05603856af744f6cba8ae7adec6baf45
     * frinedsRemark : Berlin
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2017-02/5f2d71c7556243478eb0b20e9276703e.png
     * isDoctor : 0
     * name :
     * payedUserID : 2
     * phone : 18510871104
     * sos : 0
     * state : 0
     * toUserId : 6814238dddd4414e944ca178aff758c0
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-11/15b7602986914088ab4f9b024f7f46fa140849.png
     * username : 胖子
     */

    private List<PatientListBean> patientList;

    public int getCareSize() {
        return careSize;
    }

    public void setCareSize(int careSize) {
        this.careSize = careSize;
    }

    public int getDoctorSize() {
        return doctorSize;
    }

    public void setDoctorSize(int doctorSize) {
        this.doctorSize = doctorSize;
    }

    public int getPatientSize() {
        return patientSize;
    }

    public void setPatientSize(int patientSize) {
        this.patientSize = patientSize;
    }

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

    public List<CareListBean> getCareList() {
        return careList;
    }

    public void setCareList(List<CareListBean> careList) {
        this.careList = careList;
    }

    public List<DoctorListBean> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<DoctorListBean> doctorList) {
        this.doctorList = doctorList;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<PatientListBean> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<PatientListBean> patientList) {
        this.patientList = patientList;
    }

    public static class CareListBean {
        private int agreeFlag;
        private int canSosSet;
        private int card;
        private String createTime;
        private int datas;
        private String friendsId;
        private String frinedsRemark;
        private String headUrl;
        private int isDoctor;
        private String name;
        private int payedUserID;
        private String phone;
        private int sos;
        private int state;
        private String toUserId;
        private String twoDimension;
        private String username;

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public int getCanSosSet() {
            return canSosSet;
        }

        public void setCanSosSet(int canSosSet) {
            this.canSosSet = canSosSet;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class DoctorListBean {
        private int agreeFlag;
        private int canSosSet;
        private int card;
        private String createTime;
        private int datas;
        private String friendsId;
        private String frinedsRemark;
        private String headUrl;
        private int isDoctor;
        private String name;
        private int payedUserID;
        private String phone;
        private int sos;
        private int state;
        private String toUserId;
        private String twoDimension;
        private String username;

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public int getCanSosSet() {
            return canSosSet;
        }

        public void setCanSosSet(int canSosSet) {
            this.canSosSet = canSosSet;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class ListBean {
        private int agreeFlag;
        private int card;
        private String createTime;
        private int datas;
        private String friendsId;
        private String frinedsRemark;
        private String headUrl;
        private int isDoctor;
        private String name;
        private int payedUserID;
        private String phone;
        private int sos;
        private int state;
        private String toUserId;
        private String twoDimension;
        private String username;

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class PatientListBean {
        private int agreeFlag;
        private int card;
        private String createTime;
        private int datas;
        private String friendsId;
        private String frinedsRemark;
        private String headUrl;
        private int isDoctor;
        private String name;
        private int payedUserID;
        private String phone;
        private int sos;
        private int state;
        private String toUserId;
        private String twoDimension;
        private String username;

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
