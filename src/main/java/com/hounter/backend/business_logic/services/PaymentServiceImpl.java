package com.hounter.backend.business_logic.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.application.DTO.VNPayResDTO;
import com.hounter.backend.business_logic.entities.*;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.config.VnpayConfig;
import com.hounter.backend.data_access.repositories.PaymentRepository;
import com.hounter.backend.data_access.repositories.PostCostRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.PostNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    private final String serverAddress = Inet4Address.getLocalHost().getHostAddress();
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCostRepository postCostRepository;
    @Autowired
    private NotificationService notificationService;
    @PersistenceContext
    protected EntityManager em;
    public PaymentServiceImpl() throws UnknownHostException {
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void savePaymentOfPost(PostCost postCost, Long userId) {
        Payment payment = new Payment();
        payment.setCreateAt(postCost.getDate());
        payment.setExpireAt(postCost.getDate().plusDays(7));
        Cost cost = postCost.getCost();
        Integer days = postCost.getActiveDays();
        Integer total_price = cost.getPrice() * days;
        // if(days >= 7 && days < 30) {
        //     total_price -= total_price * cost.getDiscount_7days();
        // }
        // else if(days >= 30){
        //     total_price -= total_price * cost.getDiscount_30days();
        // }
        payment.setTotalPrice(total_price);
        payment.setPostCost(postCost);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCustomer(postCost.getPost().getCustomer());
        payment.setPaymentInfo("Thanh toan cho bai dang: " + postCost.getPost().getId());
        payment.setPostNum(postCost.getPost().getId());
        this.paymentRepository.save(payment); 
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void savePaymentOfPost(Payment payment, PostCost postCost) {
        payment.setCreateAt(LocalDate.now());
        payment.setExpireAt(LocalDate.now().plusDays(7));
        payment.setTotalPrice(postCost.getCost().getPrice() * postCost.getActiveDays());
        payment.setStatus(PaymentStatus.PENDING);
        this.paymentRepository.save(payment); 
    }

    @Override
    public void confirmSuccessPayment(Long postId,String transactionNo, String bankCode, Integer amount) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if(optionalPost.isEmpty()){
            throw new PostNotFoundException("Post not found.");
        }
        Post post = optionalPost.get();
        Payment payment = this.paymentRepository.findByPostCost(this.postCostRepository.findByPost(post));
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(PaymentStatus.COMPLETE);
        payment.setPaymentId(transactionNo);
        payment.setTotalPrice(amount / 100);
        payment.setPaymentMethod(bankCode);
        post.setStatus(Status.active);
        post.setExpireAt(LocalDate.now().plusDays(this.postCostRepository.findByPost(post).getActiveDays()));
        Notify notify = new Notify(NotificationService.TITLE_COMPLETE, "", String.format(NotificationService.CONTENT_COMPLETE, payment.getPaymentId()),
                LocalDate.now().toString(), false, post.getCustomer().getId().intValue());
        this.notificationService.createNotification(notify);
        this.paymentRepository.save(payment);
    }

    @Override
    public CreatePaymentDTO createPaymentOfPost(String xForwardedFor, String remoteAddr, Long postId, Long amount, Long userId) throws IOException {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if(optionalPost.isEmpty()){
            throw new PostNotFoundException("Post not found.");
        }
        Post post = optionalPost.get();
        if(!Objects.equals(post.getCustomer().getId(), userId)){
            throw new PostNotFoundException("Post not found.");
        }
        Payment payment = this.paymentRepository.findByPostCost(this.postCostRepository.findByPost(post));
        if(payment.getStatus() == PaymentStatus.COMPLETE){
            return new CreatePaymentDTO("", "Payment has been completed", "Payment has been completed");
        }
        if(payment.getStatus() == PaymentStatus.EXPIRED){
            return new CreatePaymentDTO("", "Payment has been expired", "Payment has been expired");
        }
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long vnp_amount = payment.getTotalPrice() * 100;
        String bankCode = "NCB";

        String vnp_TxnRef = post.getId().toString();
        String vnp_IpAddr = serverAddress;

        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", payment.getPaymentInfo());
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        return new CreatePaymentDTO(paymentUrl, "Ok", "Successfully");
    }

    @Override
    public VNPayResDTO getPaymentInfo(String orderId, String vnp_TransDate, String xForwardedFor, String remoteAddr) throws IOException {
        String vnp_RequestId = VnpayConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "querydr";
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
        String vnp_TxnRef = orderId;
        String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + vnp_TxnRef;
        //String vnp_TransactionNo = req.getParameter("transactionNo");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_IpAddr = serverAddress;

        JsonObject vnp_Params = new JsonObject ();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
//        vnp_Params.addProperty("vnp_TransactionNo", "14307221");
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef, vnp_TransDate, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hash_Data);

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL (VnpayConfig.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        VNPayResDTO results = objectMapper.readValue(response.toString(), VNPayResDTO.class);
        return results;
    }

    @Override
    public List<Payment> getListPaymentOfCustomer(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Root<Payment> paymenRoot = cq.from(Payment.class);
        List<Predicate> predicates = new ArrayList<>();
        if(fromDate != null){
            LocalDate from = LocalDate.parse(fromDate, formatter);
            predicates.add(cb.greaterThanOrEqualTo(paymenRoot.get("createAt"), from));
        }
        if(toDate != null){
            LocalDate to = LocalDate.parse(toDate, formatter);
            predicates.add(cb.lessThanOrEqualTo(paymenRoot.get("createAt"), to));
        }
        if(status != null){
            predicates.add(cb.equal(paymenRoot.get("status"), status));
        }
        if(transactionId != null && !transactionId.isEmpty()){
            predicates.add(cb.equal(paymenRoot.get("paymentId"), transactionId));
        }
        if(customerId != null && customerId > 0){
            predicates.add(cb.equal(paymenRoot.get("customer").get("id"), customerId));
        }
        if (postNum != null && postNum > 0) {
            predicates.add(cb.equal(paymenRoot.get("postNum"), postNum));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(paymenRoot.get("createAt")));
        return em.createQuery(cq).setMaxResults(pageSize)
                .setFirstResult(pageNo * pageSize)
                .getResultList();
    }
    @Override
    public Payment getPaymentByPostNum(Long postNum) {
        return this.paymentRepository.findByPostNum(postNum);
    }

}
