package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessages(Message message) {
        int messageTextLen = message.getMessageText().length();
        if (messageTextLen == 0 || messageTextLen > 255) {
            return null;
        }

        Account exist = this.accountRepository.findByAccountId(message.getPostedBy());
        if (exist == null) {
            return null;
        }

        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return this.messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        return this.messageRepository.findByMessageId(messageId);
    }

    @Transactional
    public int deleteMessageById(int messageId){
        return this.messageRepository.deleteByMessageId(messageId);
    }
    
    @Transactional
    public int updateMessageById(int messageId, Message newMessage){
        Message exist = this.messageRepository.findByMessageId(messageId);
        if (exist == null) {
            return 0;
        }

        int newMessageTextLen = newMessage.getMessageText().length();
        if (newMessageTextLen == 0 || newMessageTextLen > 255) {
            return 0;
        }

        return this.messageRepository.updateMessageText(messageId, newMessage.getMessageText());
    }

    public List<Message> getAllMessagesFromUser(int account_id){
        return this.messageRepository.findByPostedBy(account_id);
    }
}
