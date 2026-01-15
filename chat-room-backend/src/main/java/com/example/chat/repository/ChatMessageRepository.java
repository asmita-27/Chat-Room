package com.example.chat.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    List<ChatMessage> findByChatRoomId(UUID chatRoomId);

    List<ChatMessage> findByChatRoomId(UUID chatRoomId, Pageable pageable);

    List<ChatMessage> findByChatRoomIdAndTimestampAfter(UUID chatRoomId, java.time.LocalDateTime timestamp);

    List<ChatMessage> findByChatRoomIdAndTimestampAfter(UUID chatRoomId, java.time.LocalDateTime timestamp, Pageable pageable);
}
