package com.zhixinyisheng.user.domain;

/**
 * 认证状态实体类
 * Created by 焕焕 on 2016/12/29.
 */
public class RenZhengInfo {

    /**
     * result : 0000
     * db : {"attpStates":1,"attp":"333","attpUrl":"http://222.223.218.50:8337/myPic/zhixinyisheng/user/2016-12/11f850a2ef86b4ffaaf694e67adb8b8f5.png,http://222.223.218.50:8337/myPic/zhixinyisheng/user/2016-12/10fb0d10cf36540cabfa04f66cceff33f.png","attestation":0}
     * retMessage : 服务器暂无响应
     */

    private String result;
    /**
     * attpStates : 1
     * attp : 333
     * attpUrl : http://222.223.218.50:8337/myPic/zhixinyisheng/user/2016-12/11f850a2ef86b4ffaaf694e67adb8b8f5.png,http://222.223.218.50:8337/myPic/zhixinyisheng/user/2016-12/10fb0d10cf36540cabfa04f66cceff33f.png
     * attestation : 0
     */

    private DbBean db;
    private String retMessage;

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

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class DbBean {
        private int attpStates;
        private String attp;
        private String attpUrl;
        private int attestation;

        public int getAttpStates() {
            return attpStates;
        }

        public void setAttpStates(int attpStates) {
            this.attpStates = attpStates;
        }

        public String getAttp() {
            return attp;
        }

        public void setAttp(String attp) {
            this.attp = attp;
        }

        public String getAttpUrl() {
            return attpUrl;
        }

        public void setAttpUrl(String attpUrl) {
            this.attpUrl = attpUrl;
        }

        public int getAttestation() {
            return attestation;
        }

        public void setAttestation(int attestation) {
            this.attestation = attestation;
        }
    }
}
