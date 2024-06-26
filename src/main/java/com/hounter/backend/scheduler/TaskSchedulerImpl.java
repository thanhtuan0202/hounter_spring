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
//        String sql = Files.readString(path, StandardCharsets.UTF_8);
//        for (String statement : sql.split(";")) {
//            if (!statement.trim().isEmpty()) {
//                jdbcTemplate.execute(statement);
//            }
//        }
//        jdbcTemplate.execute("SET GLOBAL log_bin_trust_function_creators = 1;");
//        jdbcTemplate.execute("DROP FUNCTION IF EXISTS distance_haversine;");
//        String function_sql = "CREATE FUNCTION `distance_haversine`(lat1 float, lon1 float, lat2 float, lon2 float) RETURNS float " +
//                "BEGIN " +
//                "    DECLARE d_lat FLOAT; " +
//                "    DECLARE d_lon FLOAT; " +
//                "    DECLARE a FLOAT; " +
//                "    DECLARE c FLOAT; " +
//                "    SET d_lat = RADIANS(lat2 - lat1); " +
//                "    SET d_lon = RADIANS(lon2 - lon1); " +
//                "    SET a = SIN(d_lat / 2) * SIN(d_lat / 2) + COS(RADIANS(lat1)) * COS(RADIANS(lat2)) * SIN(d_lon / 2) * SIN(d_lon / 2); " +
//                "    SET c = 2 * ATAN2(SQRT(a), SQRT(1 - a)); " +
//                "    RETURN 6371 * c; " +
//                "END";
//        jdbcTemplate.execute(function_sql);
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
