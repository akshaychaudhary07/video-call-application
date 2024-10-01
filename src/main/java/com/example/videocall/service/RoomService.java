package com.example.videocall.service;

import com.example.videocall.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private final Map<String, Room> rooms = new HashMap<>();

    // Create a new room
    public Room createRoom() {
        String roomId = UUID.randomUUID().toString();
        String roomCode = UUID.randomUUID().toString().substring(0, 6); // Unique 6 character code
        Room room = new Room(roomId, roomCode);
        rooms.put(roomId, room);
        logger.info("Room Created: ID = {}, Code = {}", roomId, roomCode);
        return room;
    }

    // Validate roomId and roomCode
    public boolean validateRoom(String roomId, String roomCode) {
        Room room = rooms.get(roomId);
        logger.info("Validating Room - ID: {}, Code: {}", roomId, roomCode);
        return room != null && room.getRoomCode().equals(roomCode);
    }

    // Add a participant to the room
    public void addParticipant(String roomId, String participantId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            room.addParticipant(participantId);
            logger.info("Participant added: {} to room {}", participantId, roomId);
        } else {
            logger.error("Failed to add participant: Room not found {}", roomId);
        }
    }

    // Get room by ID
    public Room getRoomById(String roomId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            logger.info("Room found: ID = {}", roomId);
        } else {
            logger.warn("Room not found: ID = {}", roomId);
        }
        return room;
    }
}
