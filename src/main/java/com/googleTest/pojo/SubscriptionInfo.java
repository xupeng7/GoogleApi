package com.googleTest.pojo;

public class SubscriptionInfo {

    private  Boolean autoRenewing;
    private String expireTime;
    private String orderId;

    public Boolean getAutoRenewing() {
        return autoRenewing;
    }

    public void setAutoRenewing(Boolean autoRenewing) {
        this.autoRenewing = autoRenewing;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
