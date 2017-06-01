package com.zhixinyisheng.user.domain;

/**
 * 好友
 * Created by Administrator on 2016/10/27.
 */
public class Person {

    /**
     * result : 0000
     * db : {"uid":"","sex":0,"phone":"13831128915","cardPic":"","registrationId":"171976fa8a8c3015d38","webchat":"","remark":"","hospitalId":"","doctorPhone":"","disease":"","twoDimension":"http://222.222.24.133:8099/picResource/sbs/user/2016-10/28f1507f10ae4a7aa799129c7d4a6d19114218.png","password":"7c4a8d09ca3762af61e59520943dc26494f8941b","city":"","attpStates":1,"username":"爱吃月亮果","addtime":1475206938000,"psan":"","mycard":"","age":0,"name":"王晓佩","userId":"7566626570b04a24836ee8b0b7b17cf9","province":"","grade":"","card":555555960,"headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/eea60dddfa294f59891c7190243aef79.jpg","SYMPTOM":"","honor":"","isDoctor":0,"job":"","health":100,"intro":"","section":"","attestation":0,"sina":"","hospitalAddress":"\u2018\u2019","source":1,"attp":"123456789012345","address":"","userStatus":0,"attpUrl":"http://222.222.24.133:8099/picResource/sbs/user/2016-11/b7e8e09986b6423abcb826c9b053ab70.jpg,http://222.222.24.133:8099/picResource/sbs/user/2016-11/8a7d315c6136451caf7dfc79f6373eb4.jpg","hospital":""}
     * returnMessage : 查询成功
     */

    private String result;
    /**
     * uid :
     * sex : 0
     * phone : 13831128915
     * cardPic :
     * registrationId : 171976fa8a8c3015d38
     * webchat :
     * remark :
     * hospitalId :
     * doctorPhone :
     * disease :
     * twoDimension : http://222.222.24.133:8099/picResource/sbs/user/2016-10/28f1507f10ae4a7aa799129c7d4a6d19114218.png
     * password : 7c4a8d09ca3762af61e59520943dc26494f8941b
     * city :
     * attpStates : 1
     * username : 爱吃月亮果
     * addtime : 1475206938000
     * psan :
     * mycard :
     * age : 0
     * name : 王晓佩
     * userId : 7566626570b04a24836ee8b0b7b17cf9
     * province :
     * grade :
     * card : 555555960
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/eea60dddfa294f59891c7190243aef79.jpg
     * SYMPTOM :
     * honor :
     * isDoctor : 0
     * job :
     * health : 100.0
     * intro :
     * section :
     * attestation : 0
     * sina :
     * hospitalAddress : ‘’
     * source : 1
     * attp : 123456789012345
     * address :
     * userStatus : 0
     * attpUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-11/b7e8e09986b6423abcb826c9b053ab70.jpg,http://222.222.24.133:8099/picResource/sbs/user/2016-11/8a7d315c6136451caf7dfc79f6373eb4.jpg
     * hospital :
     */

    private DbBean db;
    private String returnMessage;

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

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public static class DbBean {
        private String uid;
        private int sex;
        private String phone;
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
        private String grade;
        private int card;
        private String headUrl;
        private String SYMPTOM;
        private String honor;
        private int isDoctor;
        private String job;
        private double health;
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

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
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
