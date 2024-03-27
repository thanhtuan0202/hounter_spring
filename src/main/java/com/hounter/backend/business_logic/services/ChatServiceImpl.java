package com.hounter.backend.business_logic.services;

import com.google.firebase.database.*;
import com.hounter.backend.business_logic.entities.Message;
import com.hounter.backend.business_logic.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final FirebaseDatabase firebaseDatabase;
    public static final String CHAT_PATH = "messages";
    public ChatServiceImpl() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public List<Message> getListMessageOfUser(String username) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        DatabaseReference firebaseReference = firebaseDatabase.getReference(CHAT_PATH + "/" + username);
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                future.complete(messages);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        return future.join();
    }

    @Override
    public List<String> getUserChatWithStaff(String staffUsername) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        DatabaseReference firebaseReference = firebaseDatabase.getReference(CHAT_PATH + "/" + staffUsername);
        return null;
    }

    @Override
    public void sendNewMessage(Message message) {
        DatabaseReference firebaseReference = firebaseDatabase.getReference(CHAT_PATH + "/" + message.getSenderUsername());
        DatabaseReference newMessage = firebaseReference.push();
        newMessage.setValue(message, (databaseError, databaseReference) -> {
            if(databaseError != null){
                System.out.println("Message could not be saved " + databaseError.getMessage());
            }else{
                System.out.println("Message saved successfully.");
            }
        });
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverUsername()),"/message", message);
    }
}
