package com.hounter.backend.scheduler;

import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.business_logic.interfaces.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
@Component
@Slf4j
public class TaskSchedulerImpl implements TaskScheduler{
    @Autowired
    private PostService postService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.HOURS)
    public void resetDatabase() throws IOException {
        log.info("Resetting database...");
//        Path path = Paths.get(ResourceUtils.getFile("classpath:data.sql").toURI());
//        String sql = new String(Files.readAllBytes(path));
//        for (String statement : sql.split(";")) {
//            if (!statement.trim().isEmpty()) {
//                jdbcTemplate.execute(statement);
//            }
//        }
        log.info("Database reset complete.");
    }

    @Override
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void checkPostExpire() {
        log.info("Checking post expiration...");
        this.postService.handlePostExpire(LocalDate.now());
    }

    @Override
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void checkPaymentExpire() {
        log.info("Checking payment expiration...");
        this.paymentService.handlePaymentExpire(LocalDate.now());
    }

    @Override
    @Scheduled(fixedDelay = 120, timeUnit = TimeUnit.SECONDS)
    public void remindPayment() {
        log.info("Reminding payment...");
        this.paymentService.handleRemindPayment(LocalDate.now());
    }

}
