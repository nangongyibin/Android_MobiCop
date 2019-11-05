package com.ngyb.mobicop.bean;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 21:23
 */
public class SmsInfoBean {
    private String address;
    private long date;
    private int read;
    private int type;
    private String body;

    public SmsInfoBean(String address, long date, int read, int type, String body) {
        this.address = address;
        this.date = date;
        this.read = read;
        this.type = type;
        this.body = body;
    }

    @Override
    public String toString() {
        return "SmsInfoBean{" +
                "address='" + address + '\'' +
                ", date=" + date +
                ", read=" + read +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
