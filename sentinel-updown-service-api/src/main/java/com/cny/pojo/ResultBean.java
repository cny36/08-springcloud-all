package com.cny.pojo;

/**
 * @author : chennengyuan
 */
public class ResultBean<T> {
    private int statusCode;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }
}
