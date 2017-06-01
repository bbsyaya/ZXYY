package com.zhixinyisheng.user.ui.IM.domain;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/22  16:01
 * <p/>
 * 类说明:
 */
public class FindFrinedEntity {


    /**
     * SYMPTOM :
     * address : 董江宁地址
     * addtime : 1476600199000
     * age : 30
     * attestation : 0
     * attp :
     * attpStates : 0
     * attpUrl :
     * card : 555555949
     * headUrl : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=860590796,1721820213&fm=80&w=179&h=119&img.PNG
     * health : 100.0
     * hospital :
     * intro :
     * isDoctor : 0
     * mycard :
     * name : 董江宁
     * password : 7c4a8d09ca3762af61e59520943dc26494f8941b
     * phone : 15251254425
     * psan :
     * registrationId :
     * remark : 备注-董江宁
     * section :
     * sex : 1
     * sina :
     * source : 0
     * twoDimension : 二维码董江宁
     * uid :
     * userId : 3
     * userStatus : 0
     * username : dnn
     * webchat :
     */

    private DbBean db;
    /**
     * db : {"SYMPTOM":"","address":"董江宁地址","addtime":1476600199000,"age":30,"attestation":0,"attp":"","attpStates":0,"attpUrl":"","card":555555949,"headUrl":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=860590796,1721820213&fm=80&w=179&h=119&img.PNG","health":100,"hospital":"","intro":"","isDoctor":0,"mycard":"","name":"董江宁","password":"7c4a8d09ca3762af61e59520943dc26494f8941b","phone":"15251254425","psan":"","registrationId":"","remark":"备注-董江宁","section":"","sex":1,"sina":"","source":0,"twoDimension":"二维码董江宁","uid":"","userId":"3","userStatus":0,"username":"dnn","webchat":""}
     * result : 0000
     */

    private String result;

    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class DbBean {
        private String SYMPTOM;
        private String address;
        private long addtime;
        private int age;
        private int attestation;
        private String attp;
        private int attpStates;
        private String attpUrl;
        private int card;
        private String headUrl;
        private double health;
        private String hospital;
        private String intro;
        private int isDoctor;
        private String mycard;
        private String name;
        private String password;
        private String phone;
        private String psan;
        private String registrationId;
        private String remark;
        private String section;
        private int sex;
        private String sina;
        private int source;
        private String twoDimension;
        private String uid;
        private String userId;
        private int userStatus;
        private String username;
        private String webchat;

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

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
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

        public String getPsan() {
            return psan;
        }

        public void setPsan(String psan) {
            this.psan = psan;
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

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
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

        @Override
        public String toString() {
            return "DbBean{" +
                    "SYMPTOM='" + SYMPTOM + '\'' +
                    ", address='" + address + '\'' +
                    ", addtime=" + addtime +
                    ", age=" + age +
                    ", attestation=" + attestation +
                    ", attp='" + attp + '\'' +
                    ", attpStates=" + attpStates +
                    ", attpUrl='" + attpUrl + '\'' +
                    ", card=" + card +
                    ", headUrl='" + headUrl + '\'' +
                    ", health=" + health +
                    ", hospital='" + hospital + '\'' +
                    ", intro='" + intro + '\'' +
                    ", isDoctor=" + isDoctor +
                    ", mycard='" + mycard + '\'' +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", psan='" + psan + '\'' +
                    ", registrationId='" + registrationId + '\'' +
                    ", remark='" + remark + '\'' +
                    ", section='" + section + '\'' +
                    ", sex=" + sex +
                    ", sina='" + sina + '\'' +
                    ", source=" + source +
                    ", twoDimension='" + twoDimension + '\'' +
                    ", uid='" + uid + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userStatus=" + userStatus +
                    ", username='" + username + '\'' +
                    ", webchat='" + webchat + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FindFrinedEntity{" +
                "db=" + db +
                ", result='" + result + '\'' +
                ", a='" + a + '\'' +
                '}';
    }
}
