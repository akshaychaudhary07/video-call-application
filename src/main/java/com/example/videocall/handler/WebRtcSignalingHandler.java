package com.example.videocall.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Component;
import com.example.videocall.model.WebRTCMessage; // Adjust the import according to your package structure
import com.fasterxml.jackson.databind.ObjectMapper; // Use Jackson for JSON parsing

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebRtcSignalingHandler extends TextWebSocketHandler {
    // Store WebSocket sessions
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper for JSON

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("New WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Log the incoming message for debugging
        System.out.println("Received message: " + message.getPayload() + " from session: " + session.getId());

        // Parse the incoming message to a WebRTCMessage object
        WebRTCMessage webRTCMessage = parseMessage(message.getPayload());
        if (webRTCMessage == null) {
            System.err.println("Failed to parse message: " + message.getPayload());
            return; // Optionally send an error response back to the sender
        }

        // Relay the message to other clients
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (!webSocketSession.getId().equals(session.getId())) {
                try {
                    webSocketSession.sendMessage(new TextMessage(message.getPayload())); // Send signaling data to the other client
                } catch (Exception e) {
                    System.err.println("Error sending message to session " + webSocketSession.getId() + ": " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    // Example method to parse incoming JSON message to WebRTCMessage
    private WebRTCMessage parseMessage(String payload) {
        try {
            return objectMapper.readValue(payload, WebRTCMessage.class);
        } catch (Exception e) {
            System.err.println("Error parsing message: " + e.getMessage());
            return null; // Return null if parsing fails
        }
    }
}
