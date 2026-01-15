package com.example.chat.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User sender;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public enum DeliveryState {
        SENT,
        DELIVERED,
        READ
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryState deliveryState = DeliveryState.SENT;

    @Column
    private LocalDateTime deliveredAt;

    @Column
    private LocalDateTime readAt;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
        this.deliveryState = DeliveryState.SENT;
    }

    public ChatMessage() {
    }

    public ChatMessage(ChatRoom chatRoom, User sender, String content) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.deliveryState = DeliveryState.SENT;
    }

    public UUID getId() {
        return id;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(DeliveryState deliveryState) {
        this.deliveryState = deliveryState;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
}
