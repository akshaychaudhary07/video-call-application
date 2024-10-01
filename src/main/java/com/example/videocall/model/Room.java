package com.example.videocall.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private String roomId;
    private String roomCode;
    private List<String> participants; // List of participant IDs

    // Constructors, Getters, and Setters
    public Room(String roomId, String roomCode) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.participants = new ArrayList<>();
    }

    // Add participant
    public synchronized void addParticipant(String participantId) {
        if (!participants.contains(participantId)) {
            participants.add(participantId);
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", participants=" + participants +
                '}';
    }
}
