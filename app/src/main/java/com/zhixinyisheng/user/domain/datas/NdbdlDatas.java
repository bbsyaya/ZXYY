package com.zhixinyisheng.user.domain.datas;

/**
 * 尿蛋白定量
 * Created by gjj on 2016/11/10.
 */
public class NdbdlDatas {

    /**
     * result : 0000
     * list : {"USER_ID":"2","ADDTIME":"2016-11-10","URINEPROTEIN_ID":"6cd6976f17f74d4eb6429ee9a77952d1","UPVALUE":"1","BYTIME":"2016-11-10"}
     * retMessage : 操作成功！
     */

    private String result;
    /**
     * USER_ID : 2
     * ADDTIME : 2016-11-10
     * URINEPROTEIN_ID : 6cd6976f17f74d4eb6429ee9a77952d1
     * UPVALUE : 1
     * BYTIME : 2016-11-10
     */

    private ListBean list;
    private String retMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class ListBean {
        private String USER_ID;
        private String ADDTIME;
        private String URINEPROTEIN_ID;
        private String UPVALUE;
        private String BYTIME;

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getURINEPROTEIN_ID() {
            return URINEPROTEIN_ID;
        }

        public void setURINEPROTEIN_ID(String URINEPROTEIN_ID) {
            this.URINEPROTEIN_ID = URINEPROTEIN_ID;
        }

        public String getUPVALUE() {
            return UPVALUE;
        }

        public void setUPVALUE(String UPVALUE) {
            this.UPVALUE = UPVALUE;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }
    }
}
