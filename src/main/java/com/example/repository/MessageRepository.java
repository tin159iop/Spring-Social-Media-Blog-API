package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findAll();
    public Message findByMessageId(int messageId);
    public int deleteByMessageId(int messageId);
    public List<Message> findByPostedBy(int postedBy);

    @Modifying
    @Query("UPDATE Message m SET m.messageText = ?2 WHERE m.messageId = ?1")
    public int updateMessageText(int messageId, String messageText);
}
