package com.hounter.backend.business_logic.services;

import com.google.firebase.database.*;
import com.hounter.backend.business_logic.entities.Notify;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    private final FirebaseDatabase firebaseDatabase;
    public static final String NOTIFICATION_PATH = "notifications";
    public static final String TITLE_PENDING = "Hoàn tất thanh toán";
    public static final String TITLE_COMPLETE = "Thanh toán thành công";
    public static final String TITLE_EXPIRED = "Đơn hàng đã hết hạn";
    public static final String TITLE_REFUNDED = "Đã hoàn tiền";
    public static final String CONTENT_PENDING = "Đơn hàng giá trị [%s] của bạn chưa được thanh toán. Vui lòng thanh toán trước [%s]. Nếu không, đơn hàng sẽ bị huỷ.";
    public static final String CONTENT_COMPLETE = "Đơn hàng của bạn đã được thanh toán thành công. Mã đơn hàng: [%s]";
    public static final String CONTENT_EXPIRED = "Đơn hàng [%s] của bạn đã hết hạn và huỷ bởi hệ thống.";
    public static final String CONTENT_REFUNDED = "Đơn hàng của bạn đã được hoàn tiền. Mã đơn hàng: [%s]";

    public NotificationService(){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public List<Notify> getNotificationOfUser(Long userId){
        CompletableFuture<List<Notify>> future = new CompletableFuture<>();
        DatabaseReference firebaseReference = firebaseDatabase.getReference(NOTIFICATION_PATH);
         firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notify> notifies = new ArrayList<>();
                if(dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                    for(DataSnapshot snapshot: snapshots){
                        Notify notify = snapshot.getValue(Notify.class);
                        if(notify.getUserId().equals(userId.intValue())){
                            System.out.println(snapshot.getKey());
                            notifies.add(notify);
                        }
                    }
                    System.out.println(notifies.size());
                    future.complete(notifies);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        System.out.println(future.join().size());
        return future.join();
    }

    public void createNotification(Notify notify){
        DatabaseReference firebaseReference = firebaseDatabase.getReference(NOTIFICATION_PATH);
        DatabaseReference newNotify = firebaseReference.push();
        newNotify.setValue(notify, (databaseError, databaseReference) -> {
            if(databaseError != null){
                System.out.println("Data could not be saved " + databaseError.getMessage());
            }else{
                System.out.println("Data saved successfully.");
            }
        });
    }


}
