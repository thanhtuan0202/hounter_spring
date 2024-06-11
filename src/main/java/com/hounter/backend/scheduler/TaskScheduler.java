package com.hounter.backend.scheduler;

import java.io.IOException;

public interface TaskScheduler {
    void checkPostExpire();
    void checkPaymentExpire();
    void remindPayment();
    void resetDatabase() throws IOException;
}
