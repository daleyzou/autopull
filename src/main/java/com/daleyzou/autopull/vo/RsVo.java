package com.daleyzou.autopull.vo;

/**
 * RsVo
 * @description TODO
 * @author daleyzou
 * @date 2020年11月16日 16:07
 * @version 1.3.1
 */

public class RsVo {
    private boolean succeeded;

    private int code;

    private String msg;

    private Object data;

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RsVo))
            return false;
        RsVo other = (RsVo)o;
        if (!other.canEqual(this))
            return false;
        if (isSucceeded() != other.isSucceeded())
            return false;
        if (getCode() != other.getCode())
            return false;
        Object this$msg = getMsg(), other$msg = other.getMsg();
        if ((this$msg == null) ? (other$msg != null) : !this$msg.equals(other$msg))
            return false;
        Object this$data = getData(), other$data = other.getData();
        return !((this$data == null) ? (other$data != null) : !this$data.equals(other$data));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RsVo;
    }



    @Override
    public String toString() {
        return "RsVo(succeeded=" + isSucceeded() + ", code=" + getCode() + ", msg=" + getMsg() + ", data=" + getData() + ")";
    }

    public RsVo(String msg) {
        this.succeeded = true;
        this.code = 200;
        this.msg = msg;
    }

    public RsVo(boolean succeeded, int code, String msg, Object data) {
        this.succeeded = succeeded;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RsVo success(Object data) {
        return new RsVo(true, 200, "success", data);
    }

    public static RsVo fail(String msg) {
        return new RsVo(true, 500, msg, null);
    }

    public static RsVo success(String msg, Object data) {
        return new RsVo(true, 200, msg, data);
    }

    public boolean successful() {
        return (this.code == 200);
    }

    public Object getData() {
        return this.data;
    }

    public boolean isSucceeded() {
        return this.succeeded;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

