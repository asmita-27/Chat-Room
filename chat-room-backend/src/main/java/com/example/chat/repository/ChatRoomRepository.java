package com.example.chat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
}
