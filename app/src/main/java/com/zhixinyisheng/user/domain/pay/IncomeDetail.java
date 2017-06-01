package com.zhixinyisheng.user.domain.pay;

import java.util.List;

/**
 * 收入明细
 * Created by 焕焕 on 2017/1/12.
 */
public class IncomeDetail {

    /**
     * list : [{"createtime":1483237501000,"incomePayRecordId":18,"money":"+7","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1484187904000,"incomePayRecordId":19,"money":"+8","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483323906000,"incomePayRecordId":20,"money":"+1","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1484101508000,"incomePayRecordId":21,"money":"+2","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483410313000,"incomePayRecordId":22,"money":"+3","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1484101515000,"incomePayRecordId":23,"money":"+4","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483583119000,"incomePayRecordId":24,"money":"+5","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483583123000,"incomePayRecordId":25,"money":"+6","title":"你好啊啊啊啊啊啊啊啊啊啊啊啊--转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483669526000,"incomePayRecordId":26,"money":"+1","title":"转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"},{"createtime":1483755928000,"incomePayRecordId":27,"money":"+100","title":"转账","type":1,"userID":"a443872d4f474e3987bd774f5553be28"}]
     * result : 0000
     * retMessage : 查询成功
     * size : 10
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * createtime : 1483237501000
     * incomePayRecordId : 18
     * money : +7
     * title : 你好啊啊啊啊啊啊啊啊啊啊啊啊--转账
     * type : 1
     * userID : a443872d4f474e3987bd774f5553be28
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
        private long createtime;
        private long incomePayRecordId;
        private String money;
        private String title;
        private int type;
        private String userID;

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getIncomePayRecordId() {
            return incomePayRecordId;
        }

        public void setIncomePayRecordId(long incomePayRecordId) {
            this.incomePayRecordId = incomePayRecordId;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }
    }
}
