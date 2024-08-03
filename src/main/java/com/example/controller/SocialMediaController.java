package com.example.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.entity.*;
import com.example.exception.UserExistedException;
import com.example.service.*;;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        try {
            Account registeredAccount = this.accountService.register(account);
            return ResponseEntity.status(200).body(registeredAccount);
        } catch (UserExistedException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(409).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account foundAccount = this.accountService.login(account);
        if (foundAccount == null) {
            return ResponseEntity.status(401).build();

        }
        return ResponseEntity.status(200).body(foundAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessages(@RequestBody Message message){
        Message addedMessage = this.messageService.createMessages(message);
        if (addedMessage == null) {
            return ResponseEntity.status(400).build();

        }
        return ResponseEntity.status(200).body(addedMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = this.messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        Message foundMessage = this.messageService.getMessageById(message_id);
        return ResponseEntity.status(200).body(foundMessage);
    }
    
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id){
        int rowsAffected = this.messageService.deleteMessageById(message_id);
        if (rowsAffected == 0) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(200).body(rowsAffected);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message){
        int rowsAffected = this.messageService.updateMessageById(message_id, message);
        if (rowsAffected == 0) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(rowsAffected);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable int account_id){
        List<Message> allMessages = this.messageService.getAllMessagesFromUser(account_id);
        return ResponseEntity.status(200).body(allMessages);
    }
}
