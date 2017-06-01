package com.zhixinyisheng.user.ui.data.synthesize_info.model;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */

public class SynthesizeInfo {

    /**
     * result : 0000
     * list : [{"content":"侯weijia╬看看具体","time":1493621100000,"title":"温馨提示","type":14,"info":"17211a90d6a24d688fb651c8bf0c18b5"},{"content":"侯weijia╬看看具体","time":1493615700000,"title":"温馨提示","type":14,"info":"17211a90d6a24d688fb651c8bf0c18b5"},{"content":"侯weijia╬啦啦啦","time":1493613900000,"title":"温馨提示","type":14,"info":"17211a90d6a24d688fb651c8bf0c18b5"},{"content":"0","time":1493621487000,"title":"步数","type":9,"info":""},{"content":"98","time":1493602458000,"title":"心率","type":1,"info":""}]
     * retMessage : 查询成功
     * size : 5
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * content : 侯weijia╬看看具体
     * time : 1493621100000
     * title : 温馨提示
     * type : 14
     * info : 17211a90d6a24d688fb651c8bf0c18b5
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
        private String content;
        private long time;
        private String title;
        private int type;
        private String info;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
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

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
