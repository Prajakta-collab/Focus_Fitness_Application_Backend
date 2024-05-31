package com.project.attendance.Exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SubscriptionExpireException extends RuntimeException{

    private LocalDate expireDate ;

    public SubscriptionExpireException(LocalDate expireDate) {
        super("Your Subscription is Expired at " + expireDate + " , Please Renew your Subscription");
        this.expireDate = expireDate;
    }

}
