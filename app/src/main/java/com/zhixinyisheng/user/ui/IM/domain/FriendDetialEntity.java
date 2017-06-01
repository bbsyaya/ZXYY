package com.zhixinyisheng.user.ui.IM.domain;


import java.util.List;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/9  14:46
 * <p/>
 * 类说明: 好友详情实体类
 */
public class FriendDetialEntity {

    /**
     * dataList : [{"ADDTIME":1493278766000,"BYTIME":"2017-04-27","HEARTRATE_ID":"96eecd9b881a450d92a3f828b4953a74","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":95,"state":1},{"ADDTIME":1493084891000,"BYTIME":"2017-04-25","HEARTRATE_ID":"180f8878a22948ee8243df205a42581a","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":77,"state":1},{"ADDTIME":1493032107000,"BYTIME":"2017-04-24","HEARTRATE_ID":"29d59e2d6d99405facfe0c65357240ee","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":97,"state":1},{"ADDTIME":1493032013000,"BYTIME":"2017-04-24","HEARTRATE_ID":"7905f7ff018a49af818b8e84dcf5ecdb","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":89,"state":1},{"ADDTIME":1493031979000,"BYTIME":"2017-04-24","HEARTRATE_ID":"df285a5e847f42cf8baab58e2a094875","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":96,"state":1},{"ADDTIME":1493031891000,"BYTIME":"2017-04-24","HEARTRATE_ID":"e82344ca6411462e8aaf4ceb13454393","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":91,"state":1},{"ADDTIME":1493031876000,"BYTIME":"2017-04-24","HEARTRATE_ID":"456679f25f014f5d92bcdf0e83afe05a","HIGH":0,"LOW":0,"USER_ID":"e4bb0bce8ceb47c0a4aeae42f5f69ada","num":120,"state":1}]
     * dataSize : 7
     * db : {"address":"宇宙银河系太阳系第三行星地球亚洲东南部中国河北省石家庄市","age":18,"card":5001,"disease":"身体拉长术","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-04/f4bee0cf116f41ee91199719842a5f79.png","hospital":"聊城市第四人民医院","isAllAlert":0,"isDoctor":1,"job":"副主任医师","name":"啊窝额","remark":"我说我不测，你非让我测，你看，测出事了吧","section":"保健养生 增高","sex":1,"state":0,"userId":"e4bb0bce8ceb47c0a4aeae42f5f69ada","username":"啊窝额"}
     * questionDatas : 心率,120次/分,2017.04.24|高压,146.0mmHg,2017.04.24|蛋白质,++,2017.02.14|潜血,++++,2017.02.14|左肾体积,2*2*2mm,2017.02.12|右肾体积,3*3*3mm,2017.02.12|
     * result : 0000
     * retMessage : 成功!
     */

    private int dataSize;
    /**
     * address : 宇宙银河系太阳系第三行星地球亚洲东南部中国河北省石家庄市
     * age : 18
     * card : 5001
     * disease : 身体拉长术
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2017-04/f4bee0cf116f41ee91199719842a5f79.png
     * hospital : 聊城市第四人民医院
     * isAllAlert : 0
     * isDoctor : 1
     * job : 副主任医师
     * name : 啊窝额
     * remark : 我说我不测，你非让我测，你看，测出事了吧
     * section : 保健养生 增高
     * sex : 1
     * state : 0
     * userId : e4bb0bce8ceb47c0a4aeae42f5f69ada
     * username : 啊窝额
     */

    private DbBean db;
    private String questionDatas;
    private String result;
    private String retMessage;
    /**
     * ADDTIME : 1493278766000
     * BYTIME : 2017-04-27
     * HEARTRATE_ID : 96eecd9b881a450d92a3f828b4953a74
     * HIGH : 0.0
     * LOW : 0.0
     * USER_ID : e4bb0bce8ceb47c0a4aeae42f5f69ada
     * num : 95
     * state : 1
     */

    private List<DataListBean> dataList;

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getQuestionDatas() {
        return questionDatas;
    }

    public void setQuestionDatas(String questionDatas) {
        this.questionDatas = questionDatas;
    }

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DbBean {
        private String address;
        private int age;
        private int card;
        private String disease;
        private String headUrl;
        private String hospital;
        private int isAllAlert;
        private int isDoctor;
        private String job;
        private String name;
        private String remark;
        private String section;
        private int sex;
        private int state;
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

        public int getIsAllAlert() {
            return isAllAlert;
        }

        public void setIsAllAlert(int isAllAlert) {
            this.isAllAlert = isAllAlert;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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

    public static class DataListBean {
        private long ADDTIME;
        private String BYTIME;
        private String HEARTRATE_ID;
        private double HIGH;
        private double LOW;
        private String USER_ID;
        private int num;
        private int state;

        public long getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(long ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getHEARTRATE_ID() {
            return HEARTRATE_ID;
        }

        public void setHEARTRATE_ID(String HEARTRATE_ID) {
            this.HEARTRATE_ID = HEARTRATE_ID;
        }

        public double getHIGH() {
            return HIGH;
        }

        public void setHIGH(double HIGH) {
            this.HIGH = HIGH;
        }

        public double getLOW() {
            return LOW;
        }

        public void setLOW(double LOW) {
            this.LOW = LOW;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
