package com.example.videocall.controler;

import com.example.videocall.model.WebRTCMessage;
import com.example.videocall.service.WebRTCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/webrtc")
public class WebRTCController {

    private static final Logger logger = LoggerFactory.getLogger(WebRTCController.class);

    @Autowired
    private WebRTCService webRTCService;

    @PostMapping("/signal")
    public ResponseEntity<?> signal(@RequestBody WebRTCMessage message) {
        try {
            logger.info("Received signaling message: {}", message);
            WebRTCMessage responseMessage = webRTCService.processMessage(message);

            // Check for null response from the service
            if (responseMessage == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid message type");
            }

            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            logger.error("Error processing signaling message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing message: " + e.getMessage());
        }
    }
}
