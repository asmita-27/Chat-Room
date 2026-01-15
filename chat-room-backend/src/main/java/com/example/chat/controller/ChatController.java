package com.example.chat.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ChatMessage> getMessages(@RequestParam UUID roomId) {
        return chatMessageRepository.findByChatRoomId(roomId);
    }
}
