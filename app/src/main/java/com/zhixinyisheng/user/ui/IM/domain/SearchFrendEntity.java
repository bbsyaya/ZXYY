package com.zhixinyisheng.user.ui.IM.domain;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/10/31  16:25
 * <p>
 * 类说明:
 */
public class SearchFrendEntity {


    /**
     * result : 0000
     * db : {"uid":"","sex":1,"phone":"18031150493","registrationId":"170976fa8a8e132bc4b","webchat":"","remark":"愿天下","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/5d0c92556712418f9bffa77d5aeaaea8092521.png","password":"7c4a8d09ca3762af61e59520943dc26494f8941b","attpStates":1,"username":"剧京","addtime":1475112321000,"psan":"","mycard":"","age":58,"name":"剧京","userId":"2c0fcf1171e1423097814250c23edceb","card":555555955,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/f8cd7042ce704a4c97982ce45515fd17.png","SYMPTOM":"","isDoctor":0,"health":100,"intro":"","section":"","attestation":0,"sina":"","source":2,"attp":"ggggg","address":"石家庄","isMyFrinds":"no","userStatus":0,"attpUrl":",","hospital":""}
     */

    private String result;
    /**
     * uid :
     * sex : 1
     * phone : 18031150493
     * registrationId : 170976fa8a8e132bc4b
     * webchat :
     * remark : 愿天下
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-10/5d0c92556712418f9bffa77d5aeaaea8092521.png
     * password : 7c4a8d09ca3762af61e59520943dc26494f8941b
     * attpStates : 1
     * username : 剧京
     * addtime : 1475112321000
     * psan :
     * mycard :
     * age : 58
     * name : 剧京
     * userId : 2c0fcf1171e1423097814250c23edceb
     * card : 555555955
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/f8cd7042ce704a4c97982ce45515fd17.png
     * SYMPTOM :
     * isDoctor : 0
     * health : 100.0
     * intro :
     * section :
     * attestation : 0
     * sina :
     * source : 2
     * attp : ggggg
     * address : 石家庄
     * isMyFrinds : no
     * userStatus : 0
     * attpUrl : ,
     * hospital :
     */

    private DbBean db;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public static class DbBean {
        private String uid;
        private int sex;
        private String phone;
        private String registrationId;
        private String webchat;
        private String remark;
        private String twoDimension;
        private String password;
        private int attpStates;
        private String username;
        private long addtime;
        private String psan;
        private String mycard;
        private int age;
        private String name;
        private String userId;
        private int card;
        private String headUrl;
        private String SYMPTOM;
        private int isDoctor;
        private double health;
        private String intro;
        private String section;
        private int attestation;
        private String sina;
        private int source;
        private String attp;
        private String address;
        private String isMyFrinds;
        private int userStatus;
        private String attpUrl;
        private String hospital;

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

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getSYMPTOM() {
            return SYMPTOM;
        }

        public void setSYMPTOM(String SYMPTOM) {
            this.SYMPTOM = SYMPTOM;
        }

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public double getHealth() {
            return health;
        }

        public void setHealth(double health) {
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

        public String getIsMyFrinds() {
            return isMyFrinds;
        }

        public void setIsMyFrinds(String isMyFrinds) {
            this.isMyFrinds = isMyFrinds;
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
