package com.example.eventbus;
//自定义事件
public class CustomEvent<T> {
//    flag 判断事件类型
    private int flag;
//    发送的数据
    private T data;
    public CustomEvent(int flag){
        this.flag = flag;
    }
    public CustomEvent(int flag, T data) {
        this.flag = flag;
        this.data = data;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
