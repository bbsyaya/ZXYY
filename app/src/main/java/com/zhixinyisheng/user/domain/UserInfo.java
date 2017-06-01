package com.zhixinyisheng.user.domain;

/**
 * 登录之后,返回的个人信息
 */
public class UserInfo {

    /**
     * SYMPTOM :
     * address : 交际距离土体图图拜拜
     * addtime : 1479451229000
     * age : 25
     * appraiseNums : 9
     * attestation : 1
     * attp : 555
     * attpStates : 2
     * attpUrl : http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/1071e2740f789439b957c23b4fb852fd7.png,http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/429c32fc916a4744b61eeb6489e18f59.png
     * card : 555555972
     * cardPic : http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/18b20c4df00c0436db7eacf117a0f1185.png,http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/140feed988e5c4f88be8b6c0c7c0d641c.png
     * city : 石家庄
     * disease : 肾病
     * doctorPhone : 18031150463
     * goodNums : 8
     * goodPercent : 0.7500
     * grade : 1
     * headUrl : http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-01/940f10cc23774bd8a26f636f34bfdcbd.png
     * health : 100.0
     * honor : 兔兔我我
     * hospital : 辛集市中医院
     * hospitalAddress :
     * hospitalId : 0532dc0293b149b38f30b9bb1c9b134d
     * intro : Lloyd
     * isDoctor : 1
     * job : 住院医师
     * mycard :
     * name : 剧京路
     * password : 7c4a8d09ca3762af61e59520943dc26494f8941b
     * phone : 18031150493
     * province : 河北
     * psan :
     * qq : 5555
     * registrationId : 170976fa8a8e132bc4b
     * remark : 5句图兔兔酷兔兔涂抹YY紧急集合5句5句图
     * secret : 3f619a658ee44260cb8aa6692c1da483e6ff91de
     * section : 外科 股骨头坏死
     * sectionId : 087402f4619442ba973484f65c5e699f
     * sex : 1
     * sina :
     * source : 2
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-11/3a74fa7eaa6a468ca9ba2a7615ee0b7e144029.png
     * uid :
     * userId : a443872d4f474e3987bd774f5553be28
     * userStatus : 0
     * username : 剧京
     * webchat : jjj
     */

    private String SYMPTOM;
    private String address;
    private long addtime;
    private int age;
    private int appraiseNums;
    private int attestation;
    private String attp;
    private int attpStates;
    private String attpUrl;
    private int card;
    private String cardPic;
    private String city;
    private String disease;
    private String doctorPhone;
    private int goodNums;
    private String goodPercent;
    private int grade;
    private String headUrl;
    private double health;
    private String honor;
    private String hospital;
    private String hospitalAddress;
    private String hospitalId;
    private String intro;
    private int isDoctor;
    private String job;
    private String mycard;
    private String name;
    private String password;
    private String phone;
    private String province;
    private String psan;
    private String qq;
    private String registrationId;
    private String remark;
    private String secret;
    private String section;
    private String sectionId;
    private int sex;
    private String sina;
    private int source;
    private String twoDimension;
    private String uid;
    private String userId;
    private int userStatus;
    private String username;
    private String webchat;
    private int laboratoryHave;
    private String email;
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLaboratoryHave() {
        return laboratoryHave;
    }

    public void setLaboratoryHave(int laboratoryHave) {
        this.laboratoryHave = laboratoryHave;
    }

    public String getSYMPTOM() {
        return SYMPTOM;
    }

    public void setSYMPTOM(String SYMPTOM) {
        this.SYMPTOM = SYMPTOM;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAppraiseNums() {
        return appraiseNums;
    }

    public void setAppraiseNums(int appraiseNums) {
        this.appraiseNums = appraiseNums;
    }

    public int getAttestation() {
        return attestation;
    }

    public void setAttestation(int attestation) {
        this.attestation = attestation;
    }

    public String getAttp() {
        return attp;
    }

    public void setAttp(String attp) {
        this.attp = attp;
    }

    public int getAttpStates() {
        return attpStates;
    }

    public void setAttpStates(int attpStates) {
        this.attpStates = attpStates;
    }

    public String getAttpUrl() {
        return attpUrl;
    }

    public void setAttpUrl(String attpUrl) {
        this.attpUrl = attpUrl;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public String getCardPic() {
        return cardPic;
    }

    public void setCardPic(String cardPic) {
        this.cardPic = cardPic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public int getGoodNums() {
        return goodNums;
    }

    public void setGoodNums(int goodNums) {
        this.goodNums = goodNums;
    }

    public String getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(String goodPercent) {
        this.goodPercent = goodPercent;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getMycard() {
        return mycard;
    }

    public void setMycard(String mycard) {
        this.mycard = mycard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPsan() {
        return psan;
    }

    public void setPsan(String psan) {
        this.psan = psan;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getTwoDimension() {
        return twoDimension;
    }

    public void setTwoDimension(String twoDimension) {
        this.twoDimension = twoDimension;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat;
    }
}
