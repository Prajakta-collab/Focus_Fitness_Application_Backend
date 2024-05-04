package com.project.attendance.Exception;

import java.time.LocalDate;

public class SubscriptionExpireException extends RuntimeException{
    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    private LocalDate expireDate ;

    public SubscriptionExpireException(LocalDate expireDate) {
        super("Your Subscription is Expired at " + expireDate + " , Please Renew your Subscription");
        this.expireDate = expireDate;
    }

}
