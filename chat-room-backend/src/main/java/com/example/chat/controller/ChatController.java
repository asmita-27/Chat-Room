package com.example.chat.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.ChatRoom;
import com.example.chat.entity.User;
import com.example.chat.repository.ChatMessageRepository;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.repository.UserRepository;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam String name, @RequestParam List<UUID> memberIds) {
        ChatRoom room = new ChatRoom(name);
        Set<User> members = new HashSet<>(userRepository.findAllById(memberIds));
        room.setMembers(members);
        return chatRoomRepository.save(room);
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestParam UUID roomId, @RequestParam UUID senderId, @RequestParam String content) {
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow();
        User sender = userRepository.findById(senderId).orElseThrow();
        ChatMessage message = new ChatMessage(room, sender, content);
        return chatMessageRepository.save(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @RequestParam UUID roomId,
            @RequestParam(required = false) LocalDateTime since,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow();
        boolean isMember = room.getMembers().stream().anyMatch(u -> u.getUsername().equals(username));
        if (!isMember) {
            return ResponseEntity.status(403).build();
        }
        if (size > 100) {
            size = 100; // Safe query limit

        }
        Pageable pageable = PageRequest.of(page, size);
        List<ChatMessage> messages;
        if (since != null) {
            messages = chatMessageRepository.findByChatRoomIdAndTimestampAfter(roomId, since, pageable);
        } else {
            messages = chatMessageRepository.findByChatRoomId(roomId, pageable);
        }
        return ResponseEntity.ok(messages);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
