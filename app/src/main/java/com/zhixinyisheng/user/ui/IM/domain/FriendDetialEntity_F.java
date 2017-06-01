package com.zhixinyisheng.user.ui.IM.domain;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/14  15:11
 * <p/>
 * 类说明:
 */
public class FriendDetialEntity_F {

    /**
     * address :
     * age : 0
     * card : 555555963
     * disease :
     * headUrl : D:/server/online/Tomcat 7.0_shenboshi/webapps/picResource/sbs/common/HEADURL.png
     * hospital :
     * isDoctor : 0
     * job :
     * remark :
     * sex : 1
     * userId : dbb38516fb8e49edb51fdd5f2d75b645
     * username : yyy
     */

    private DbBean db;
    /**
     * db : {"address":"","age":0,"card":555555963,"disease":"","headUrl":"D:/server/online/Tomcat 7.0_shenboshi/webapps/picResource/sbs/common/HEADURL.png","hospital":"","isDoctor":0,"job":"","remark":"","sex":1,"userId":"dbb38516fb8e49edb51fdd5f2d75b645","username":"yyy"}
     * result : 3333
     */

    private String result;

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
        private String address;
        private int age;
        private int card;
        private String disease;
        private String headUrl;
        private String hospital;
        private int isDoctor;
        private String job;
        private String remark;
        private int sex;
        private String userId;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
