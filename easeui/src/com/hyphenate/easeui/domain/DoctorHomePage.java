package com.hyphenate.easeui.domain;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by Yuanyx on 2017/1/10.
 */

public class DoctorHomePage implements Serializable{
    public DoctorHomePage() {
    }

    /**
     * result : 0000
     * doctorsinfoPdList : []
     * seeSum : {"count":0}
     * userPd : {"uid":"","sex":1,"phone":"18617599820","count":0,"cardPic":"","registrationId":"1a0018970aaf7495424","webchat":"","remark":"","hospitalId":"","doctorPhone":"","disease":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/17d3902425404eb79a1916a92d6e8f67094301.png","password":"3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d","city":"","attpStates":1,"username":"匿名","addtime":1479692582000,"psan":"","mycard":"","age":0,"name":"","userId":"c5f5197aed78453d84588c5feed2c3bf","province":"","grade":0,"card":555555976,"headUrl":"http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png","appraiseNums":0,"SYMPTOM":"","honor":"明天我学习一下也破也做最我做最做最也问我啥也破也破也我邪恶你这心情也破也也破桌子下微信破哦搜个你以为珍惜我热水洗澡在是学生哦珍惜我强迫人做最","isDoctor":1,"job":"","health":100,"intro":"加了","section":"","attestation":0,"sina":"","hospitalAddress":"\u2018\u2019","source":2,"attp":"离开了","address":"","userStatus":0,"attpUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/76e8510dbd524395950e508b1e958353.jpg,http://222.222.24.133:8099/picResource/sbs/user/2016-11/51f770378b5341d3b8ab0c34f0b9aa04.jpg","hospital":""}
     * retMessage : 查询成功
     */

    private String result;
    private SeeSumBean seeSum;
    private UserPdBean userPd;
    private String retMessage;

    protected DoctorHomePage(Parcel in) {
        result = in.readString();
        userPd = in.readParcelable(UserPdBean.class.getClassLoader());
        retMessage = in.readString();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SeeSumBean getSeeSum() {
        return seeSum;
    }

    public void setSeeSum(SeeSumBean seeSum) {
        this.seeSum = seeSum;
    }

    public UserPdBean getUserPd() {
        return userPd;
    }

    public void setUserPd(UserPdBean userPd) {
        this.userPd = userPd;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }


    public static class SeeSumBean {
        public SeeSumBean() {
        }

        /**
         * count : 0
         */

        private int count;

        protected SeeSumBean(Parcel in) {
            count = in.readInt();
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class UserPdBean implements Serializable{
        public UserPdBean() {
        }

        /**
         * uid :
         * sex : 1
         * phone : 18617599820
         * count : 0
         * cardPic :
         * registrationId : 1a0018970aaf7495424
         * webchat :
         * remark :
         * hospitalId :
         * doctorPhone :
         * disease :
         * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-11/17d3902425404eb79a1916a92d6e8f67094301.png
         * password : 3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d
         * city :
         * attpStates : 1
         * username : 匿名
         * addtime : 1479692582000
         * psan :
         * mycard :
         * age : 0
         * name :
         * userId : c5f5197aed78453d84588c5feed2c3bf
         * province :
         * grade : 0
         * card : 555555976
         * headUrl : http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png
         * appraiseNums : 0
         * SYMPTOM :
         * honor : 明天我学习一下也破也做最我做最做最也问我啥也破也破也我邪恶你这心情也破也也破桌子下微信破哦搜个你以为珍惜我热水洗澡在是学生哦珍惜我强迫人做最
         * isDoctor : 1
         * job :
         * health : 100
         * intro : 加了
         * section :
         * attestation : 0
         * sina :
         * hospitalAddress : ‘’
         * source : 2
         * attp : 离开了
         * address :
         * userStatus : 0
         * attpUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/76e8510dbd524395950e508b1e958353.jpg,http://222.222.24.133:8099/picResource/sbs/user/2016-11/51f770378b5341d3b8ab0c34f0b9aa04.jpg
         * hospital :
         */
        private String goodPercent;
        private String goodNums;
        private String frinedsRemark;

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getGoodPercent() {
            return goodPercent;
        }

        public void setGoodPercent(String goodPercent) {
            this.goodPercent = goodPercent;
        }

        public String getGoodNums() {
            return goodNums;
        }

        public void setGoodNums(String goodNums) {
            this.goodNums = goodNums;
        }

        private String uid;
        private int sex;
        private String phone;
        private int count;
        private String cardPic;
        private String registrationId;
        private String webchat;
        private String remark;
        private String hospitalId;
        private String doctorPhone;
        private String disease;
        private String twoDimension;
        private String password;
        private String city;
        private int attpStates;
        private String username;
        private long addtime;
        private String psan;
        private String mycard;
        private int age;
        private String name;
        private String userId;
        private String province;
        private int grade;
        private String card;
        private String headUrl;
        private int appraiseNums;
        private String SYMPTOM;
        private String honor;
        private int isDoctor;
        private String job;
        private int health;
        private String intro;
        private String section;
        private int attestation;
        private String sina;
        private String hospitalAddress;
        private int source;
        private String attp;
        private String address;
        private int userStatus;
        private String attpUrl;
        private String hospital;
        private int payedUserID;
        private int num;
        public int getNum(){
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }
        public int getPayedUserID() {
            return payedUserID;
        }

        public void setPayedUserID(int payedUserID) {
            this.payedUserID = payedUserID;
        }

        protected UserPdBean(Parcel in) {
            uid = in.readString();
            sex = in.readInt();
            phone = in.readString();
            count = in.readInt();
            cardPic = in.readString();
            registrationId = in.readString();
            webchat = in.readString();
            remark = in.readString();
            hospitalId = in.readString();
            doctorPhone = in.readString();
            disease = in.readString();
            twoDimension = in.readString();
            password = in.readString();
            city = in.readString();
            attpStates = in.readInt();
            username = in.readString();
            addtime = in.readLong();
            psan = in.readString();
            mycard = in.readString();
            age = in.readInt();
            name = in.readString();
            userId = in.readString();
            province = in.readString();
            grade = in.readInt();
            card = in.readString();
            headUrl = in.readString();
            appraiseNums = in.readInt();
            SYMPTOM = in.readString();
            honor = in.readString();
            isDoctor = in.readInt();
            job = in.readString();
            health = in.readInt();
            intro = in.readString();
            section = in.readString();
            attestation = in.readInt();
            sina = in.readString();
            hospitalAddress = in.readString();
            source = in.readInt();
            attp = in.readString();
            address = in.readString();
            userStatus = in.readInt();
            attpUrl = in.readString();
            hospital = in.readString();
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCardPic() {
            return cardPic;
        }

        public void setCardPic(String cardPic) {
            this.cardPic = cardPic;
        }

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }

        public String getWebchat() {
            return webchat;
        }

        public void setWebchat(String webchat) {
            this.webchat = webchat;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getDoctorPhone() {
            return doctorPhone;
        }

        public void setDoctorPhone(String doctorPhone) {
            this.doctorPhone = doctorPhone;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getAttpStates() {
            return attpStates;
        }

        public void setAttpStates(int attpStates) {
            this.attpStates = attpStates;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public String getPsan() {
            return psan;
        }

        public void setPsan(String psan) {
            this.psan = psan;
        }

        public String getMycard() {
            return mycard;
        }

        public void setMycard(String mycard) {
            this.mycard = mycard;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getAppraiseNums() {
            return appraiseNums;
        }

        public void setAppraiseNums(int appraiseNums) {
            this.appraiseNums = appraiseNums;
        }

        public String getSYMPTOM() {
            return SYMPTOM;
        }

        public void setSYMPTOM(String SYMPTOM) {
            this.SYMPTOM = SYMPTOM;
        }

        public String getHonor() {
            return honor;
        }

        public void setHonor(String honor) {
            this.honor = honor;
        }

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public int getAttestation() {
            return attestation;
        }

        public void setAttestation(int attestation) {
            this.attestation = attestation;
        }

        public String getSina() {
            return sina;
        }

        public void setSina(String sina) {
            this.sina = sina;
        }

        public String getHospitalAddress() {
            return hospitalAddress;
        }

        public void setHospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getAttp() {
            return attp;
        }

        public void setAttp(String attp) {
            this.attp = attp;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getAttpUrl() {
            return attpUrl;
        }

        public void setAttpUrl(String attpUrl) {
            this.attpUrl = attpUrl;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

    }
}
