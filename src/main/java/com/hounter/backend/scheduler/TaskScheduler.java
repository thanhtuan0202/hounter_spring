package com.hounter.backend.scheduler;

public interface TaskScheduler {
    void checkPostExpire();
    void checkPaymentExpire();
    void remindPayment();

}
