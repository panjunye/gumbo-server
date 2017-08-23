package io.junye.android.updater.pojo;

/**
 * Created by junye on 10/07/2017.
 */

import java.util.Date;

/**
 * 推送消息包
 * @param <Data>
 */
public class PushMsg<Data> {

    /**
     * 消息事件编码
     */
    private int event;

    /**
     * 消息时间
     */
    private Date time;

    public PushMsg() {

    }

    /**
     * 消息
     */
    private Data data;

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PushMsg{" +
                "event=" + event +
                ", time=" + time +
                ", data=" + data +
                '}';
    }
}
