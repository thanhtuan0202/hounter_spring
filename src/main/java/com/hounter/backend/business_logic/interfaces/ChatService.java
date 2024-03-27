package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.business_logic.entities.Message;

import java.util.List;

public interface ChatService {
    List<Message> getListMessageOfUser(String username);
    List<String> getUserChatWithStaff(String staffUsername);
    void sendNewMessage(Message message);
}
