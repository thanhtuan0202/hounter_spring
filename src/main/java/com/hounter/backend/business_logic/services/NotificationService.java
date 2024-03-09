package com.hounter.backend.business_logic.services;

import com.google.firebase.database.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Notify {
        String email;
        String phoneNumber;
        String profile_picture;
        String username;
    }
    private final FirebaseDatabase firebaseDatabase;

    public NotificationService(){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }
    public void getDateTest(){
        DatabaseReference firebaseReference = firebaseDatabase.getReference("users");
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                    for(DataSnapshot snapshot: snapshots){
                        Notify post = snapshot.getValue(Notify.class);
                        System.out.println("Data: "+ snapshot.getKey() + snapshot.getValue() + post.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
