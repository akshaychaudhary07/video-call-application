package com.example.videocall.controler;

import com.example.videocall.model.Room;
import com.example.videocall.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Create a room
    @PostMapping("/create")
    public ResponseEntity<Room> createRoom() {
        Room room = roomService.createRoom();
        return ResponseEntity.ok(room);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinRoom(@RequestParam String roomId,
                                           @RequestParam String roomCode,
                                           @RequestParam String participantId) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }

        boolean isValid = roomService.validateRoom(roomId, roomCode);
        if (isValid) {
            roomService.addParticipant(roomId, participantId); // Add the participant
            return ResponseEntity.ok("Joined successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid room or code");
        }
    }


    // Optional: Get room details
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomDetails(@PathVariable String roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
