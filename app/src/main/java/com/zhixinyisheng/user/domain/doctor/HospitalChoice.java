package com.zhixinyisheng.user.domain.doctor;

import java.util.List;

/**
 * 医院选择
 * Created by 焕焕 on 2017/1/20.
 */
public class HospitalChoice {

    /**
     * list : [{"address":"晋城市阳城县西街兴仁巷6号","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"070d46d9afa341deb1f44d47704d707c","name":"阳城县人民医院","province":"山西"},{"address":"山西省晋城市沁水县北坛路33号","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"07258f9b204a4187b7692ce0630707a3","name":"沁水县妇幼保健院","province":"山西"},{"address":"阳城县新西街","city":"晋城","createtime":1484815944000,"grade":"","hospitalId":"220f361e13074a4ba578d0e9ce8a81d0","name":"阳城县中医院","province":"山西"},{"address":"晋城市沁水县城关","city":"晋城","createtime":1484815944000,"grade":"","hospitalId":"2b052b90e98c466b96712a1d88b5cc30","name":"沁水县中医院","province":"山西"},{"address":"晋城市阳城县凤凰北路98号","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"2d927a5369c64a40a51a9bd4f6986af3","name":"阳城县眼科医院","province":"山西"},{"address":"山西省晋城市城区西段58号","city":"晋城","createtime":1484815946000,"grade":"","hospitalId":"2fca7678eb874a50ab42a0035229c611","name":"晋城市红十字博爱医院","province":"山西"},{"address":"陵川县西关","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"38077454bf184f88a8696f12cd030981","name":"陵川县中医院","province":"山西"},{"address":"山西省晋城市东环路","city":"晋城","createtime":1484815944000,"grade":"","hospitalId":"428dbad79dbf42e291e244620a82c349","name":"晋城市职业病医院","province":"山西"},{"address":"沁水县北坛路15号","city":"晋城","createtime":1484815944000,"grade":"","hospitalId":"5b88de4d07334fa69ae0fe41fb67442d","name":"沁水县人民医院","province":"山西"},{"address":"阳城县建设北路19号","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"6d5b27cb3bd94a1584d746ed62c7f5bb","name":"阳城县妇幼保健院","province":"山西"},{"address":"陵川县","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"6db3f19bc80d4c2490b8cf1545b77768","name":"陵川县妇幼保健院","province":"山西"},{"address":"阳城县西街109号","city":"晋城","createtime":1484815946000,"grade":"","hospitalId":"7f5b723a778d433fa5d05bf2490b751f","name":"阳城县市职工医院","province":"山西"},{"address":"陵川县梅园西街","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"8305b7a2e8294e019a7e12eed4bb3ec7","name":"陵川县城关医院","province":"山西"},{"address":"阳城县凤凰东街","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"90fa589597064aa6aa9dc133292840fb","name":"阳城县城关医院","province":"山西"},{"address":"山西省晋城市城区北石店东上村","city":"晋城","createtime":1484815946000,"grade":"","hospitalId":"911b08be18ff4bbda1e013f56cfd72cb","name":"晋城煤业集团凤凰山矿医院","province":"山西"},{"address":"山西省晋城市凤台西街2753号","city":"晋城","createtime":1484815943000,"grade":"三级甲等/ 儿童医院 / 医保定点","hospitalId":"ac21d86c955c49e093352c75a7dce95f","name":"晋城市儿童医院","province":"山西"},{"address":"沁水县端氏","city":"晋城","createtime":1484815944000,"grade":"","hospitalId":"b43e87c9fa61424cb638ee7b75824195","name":"沁水县第二人民医院","province":"山西"},{"address":"陵川县附城","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"ba1cc9c714144c3e9edff8fd54ab66ff","name":"陵川县第二人民医院","province":"山西"},{"address":"阳城县桥东路4号","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"c07377f0aece40049a177710d0770dc4","name":"阳城县肿瘤医院","province":"山西"},{"address":"泽州县巴公一村","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"c6658f1c20bf46bd9ed6a422f7f00a29","name":"泽州县第二人民医院","province":"山西"},{"address":"高平市长平街180号","city":"晋城","createtime":1484815945000,"grade":"","hospitalId":"ca0ed00fc4604791903bccf744891b50","name":"高平市妇幼保健院","province":"山西"},{"address":"山西省晋城市煤业集团王台铺矿","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"cb13542496214dfe922367cad952082a","name":"晋城市煤业集团王台铺矿医院","province":"山西"},{"address":"山西省晋城市城区新市东街16号","city":"晋城","createtime":1484815946000,"grade":"","hospitalId":"d5081967635e409f86f723b669d67b4c","name":"城区计生妇幼保健服务中心","province":"山西"},{"address":"陵川县北关","city":"晋城","createtime":1484815943000,"grade":"","hospitalId":"e4123de975cc4c91902354b6051484c7","name":"陵川县人民医院","province":"山西"}]
     * result : 0000
     * retMessage : 医院查询成功
     * size : 24
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * address : 晋城市阳城县西街兴仁巷6号
     * city : 晋城
     * createtime : 1484815943000
     * grade :
     * hospitalId : 070d46d9afa341deb1f44d47704d707c
     * name : 阳城县人民医院
     * province : 山西
     */

    private List<ListBean> list;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String address;
        private String city;
        private long createtime;
        private String grade;
        private String hospitalId;
        private String name;
        private String province;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}
