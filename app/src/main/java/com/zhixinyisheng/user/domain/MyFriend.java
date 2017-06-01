package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/19  17:34
 * <p/>
 * 类说明: 好友实体类
 */
public class MyFriend {

    /**
     * COUNT : 0
     * list : {"MyFriends":[{"AGREEFLAG":1,"BRACELET":1,"CREATETIME":"2016-07-21","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/doctor/2016-07/83e13a7e056c4b48957b0e7c4b492e3b.jpg","LABELID":"2","LABELNAME":"我的好友","NAME":"李超医生","PHONE":"17731821330","REGISTSTATE":"是","REMARK":"","STAR":1,"TOP":0,"TOUSERID":"1396760dc8e047b1b8e93344d74b4a41","TOUSERROLE":1,"USERLABELSID":"83a35479aa034860a36f82beef762682"},{"AGREEFLAG":1,"BRACELET":1,"CREATETIME":"2016-07-28","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/patient/2016-07/afad329d42bb4a4893d72365642be6f7.png","LABELID":"2","LABELNAME":"我的好友","NAME":"建芬","PHONE":"13603218645","REGISTSTATE":"是","STAR":1,"TOP":0,"TOUSERID":"5f18a0c240a849e39f75b867fda6d682","TOUSERROLE":2,"USERLABELSID":"cc8f80ae32ff418b857aeda388da0f1f"},{"AGREEFLAG":1,"BRACELET":1,"CREATETIME":"2016-06-28","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/patient/2016-06/f314b0b0c3d54c96827f54b39ea643aa.jpg","LABELID":"2","LABELNAME":"我的好友","NAME":"大人","PHONE":"15831966075","REGISTSTATE":"","REMARK":"","STAR":1,"TOP":0,"TOUSERID":"864394102483221","TOUSERROLE":2,"USERLABELSID":"f5ec2e0ea8384d819f1d645dc402442a"},{"AGREEFLAG":1,"BRACELET":1,"CREATETIME":"2016-07-15","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/patient/2016-07/34e661bf7de942a7a5f68d69073d2e15.jpg","LABELID":"2","LABELNAME":"我的好友","NAME":"mi'\u2006g","PHONE":"18111253804","REGISTSTATE":"","REMARK":"","STAR":1,"TOP":0,"TOUSERID":"b5aba96e01f2414d9040b4ab7c999b3c","TOUSERROLE":2,"USERLABELSID":"1728b4d263f24a1a98279f788ad1a2a3"},{"AGREEFLAG":1,"BRACELET":0,"CREATETIME":"2016-07-21","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/patient/2016-10/16b294cb3d834121b4a7be3cdf971120.jpg","LABELID":"2","LABELNAME":"我的好友","NAME":"苍穹寻梦","PHONE":"17710886650","REGISTSTATE":"","REMARK":"","STAR":0,"TOP":0,"TOUSERID":"bce011cfc52d464da8c998409569ede7","TOUSERROLE":2,"USERLABELSID":"e2511e9e7e1a4e2f972e095a91a03c70"},{"AGREEFLAG":1,"BRACELET":1,"CREATETIME":"2016-06-29","DISTURB":0,"FROMUSERID":"ed40b46f4cf04e1dbf9c600a446d721f","FROMUSERROLE":2,"HEADURL":"http://222.222.12.186:8082/picResource/sbs/patient/2016-06/968c3d5cf0dc4629bc00abb18a00d068.jpg","LABELID":"2","LABELNAME":"我的好友","NAME":"琳杰","PHONE":"18633833397","REGISTSTATE":"是","REMARK":"","STAR":1,"TOP":0,"TOUSERID":"f2c61b3f893043ae9d6feb2c9fd81bca","TOUSERROLE":2,"USERLABELSID":"e3db1e4dffcd432bbe64d816e86ae9f2"}],"tebieguanxin":[]}
     * result : 0000
     */

    private String COUNT;
    private ListBean list;
    private String result;

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ListBean {
        /**
         * AGREEFLAG : 1
         * BRACELET : 1
         * CREATETIME : 2016-07-21
         * DISTURB : 0
         * FROMUSERID : ed40b46f4cf04e1dbf9c600a446d721f
         * FROMUSERROLE : 2
         * HEADURL : http://222.222.12.186:8082/picResource/sbs/doctor/2016-07/83e13a7e056c4b48957b0e7c4b492e3b.jpg
         * LABELID : 2
         * LABELNAME : 我的好友
         * NAME : 李超医生
         * PHONE : 17731821330
         * REGISTSTATE : 是
         * REMARK :
         * STAR : 1
         * TOP : 0
         * TOUSERID : 1396760dc8e047b1b8e93344d74b4a41
         * TOUSERROLE : 1
         * USERLABELSID : 83a35479aa034860a36f82beef762682
         */

        private List<MyFriendsBean> MyFriends;
        private List<?> tebieguanxin;

        public List<MyFriendsBean> getMyFriends() {
            return MyFriends;
        }

        public void setMyFriends(List<MyFriendsBean> MyFriends) {
            this.MyFriends = MyFriends;
        }

        public List<?> getTebieguanxin() {
            return tebieguanxin;
        }

        public void setTebieguanxin(List<?> tebieguanxin) {
            this.tebieguanxin = tebieguanxin;
        }

        public static class MyFriendsBean {
            private int AGREEFLAG;
            private int BRACELET;
            private String CREATETIME;
            private int DISTURB;
            private String FROMUSERID;
            private int FROMUSERROLE;
            private String HEADURL;
            private String LABELID;
            private String LABELNAME;
            private String NAME;
            private String PHONE;
            private String REGISTSTATE;
            private String REMARK;
            private int STAR;
            private int TOP;
            private String TOUSERID;
            private int TOUSERROLE;
            private String USERLABELSID;
            private int sos;
            private int datas;

            public int getDatas() {
                return datas;
            }

            public void setDatas(int datas) {
                this.datas = datas;
            }

            public int getSos() {
                return sos;
            }

            public void setSos(int sos) {
                this.sos = sos;
            }

            public int getAGREEFLAG() {
                return AGREEFLAG;
            }

            public void setAGREEFLAG(int AGREEFLAG) {
                this.AGREEFLAG = AGREEFLAG;
            }

            public int getBRACELET() {
                return BRACELET;
            }

            public void setBRACELET(int BRACELET) {
                this.BRACELET = BRACELET;
            }

            public String getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(String CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public int getDISTURB() {
                return DISTURB;
            }

            public void setDISTURB(int DISTURB) {
                this.DISTURB = DISTURB;
            }

            public String getFROMUSERID() {
                return FROMUSERID;
            }

            public void setFROMUSERID(String FROMUSERID) {
                this.FROMUSERID = FROMUSERID;
            }

            public int getFROMUSERROLE() {
                return FROMUSERROLE;
            }

            public void setFROMUSERROLE(int FROMUSERROLE) {
                this.FROMUSERROLE = FROMUSERROLE;
            }

            public String getHEADURL() {
                return HEADURL;
            }

            public void setHEADURL(String HEADURL) {
                this.HEADURL = HEADURL;
            }

            public String getLABELID() {
                return LABELID;
            }

            public void setLABELID(String LABELID) {
                this.LABELID = LABELID;
            }

            public String getLABELNAME() {
                return LABELNAME;
            }

            public void setLABELNAME(String LABELNAME) {
                this.LABELNAME = LABELNAME;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getPHONE() {
                return PHONE;
            }

            public void setPHONE(String PHONE) {
                this.PHONE = PHONE;
            }

            public String getREGISTSTATE() {
                return REGISTSTATE;
            }

            public void setREGISTSTATE(String REGISTSTATE) {
                this.REGISTSTATE = REGISTSTATE;
            }

            public String getREMARK() {
                return REMARK;
            }

            public void setREMARK(String REMARK) {
                this.REMARK = REMARK;
            }

            public int getSTAR() {
                return STAR;
            }

            public void setSTAR(int STAR) {
                this.STAR = STAR;
            }

            public int getTOP() {
                return TOP;
            }

            public void setTOP(int TOP) {
                this.TOP = TOP;
            }

            public String getTOUSERID() {
                return TOUSERID;
            }

            public void setTOUSERID(String TOUSERID) {
                this.TOUSERID = TOUSERID;
            }

            public int getTOUSERROLE() {
                return TOUSERROLE;
            }

            public void setTOUSERROLE(int TOUSERROLE) {
                this.TOUSERROLE = TOUSERROLE;
            }

            public String getUSERLABELSID() {
                return USERLABELSID;
            }

            public void setUSERLABELSID(String USERLABELSID) {
                this.USERLABELSID = USERLABELSID;
            }

            @Override
            public String toString() {
                return "MyFriendsB{" +
                        "AGREEFLAG=" + AGREEFLAG +
                        ", BRACELET=" + BRACELET +
                        ", CREATETIME='" + CREATETIME + '\'' +
                        ", DISTURB=" + DISTURB +
                        ", FROMUSERID='" + FROMUSERID + '\'' +
                        ", FROMUSERROLE=" + FROMUSERROLE +
                        ", HEADURL='" + HEADURL + '\'' +
                        ", LABELID='" + LABELID + '\'' +
                        ", LABELNAME='" + LABELNAME + '\'' +
                        ", NAME='" + NAME + '\'' +
                        ", PHONE='" + PHONE + '\'' +
                        ", REGISTSTATE='" + REGISTSTATE + '\'' +
                        ", REMARK='" + REMARK + '\'' +
                        ", STAR=" + STAR +
                        ", TOP=" + TOP +
                        ", TOUSERID='" + TOUSERID + '\'' +
                        ", TOUSERROLE=" + TOUSERROLE +
                        ", USERLABELSID='" + USERLABELSID + '\'' +
                        ", sos=" + sos +
                        ", datas=" + datas +
                        '}';
            }
        }



        @Override
        public String toString() {
            return "ListBean{" +
                    "MyFriends=" + MyFriends +
                    ", tebieguanxin=" + tebieguanxin +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyFriend{" +
                "COUNT='" + COUNT + '\'' +
                ", list=" + list +
                ", result='" + result + '\'' +
                '}';
    }
}
