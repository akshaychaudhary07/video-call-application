package com.example.videocall.handler;

import com.example.videocall.model.WebRTCMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SignalingHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Session established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // Log the raw message payload
            System.out.println("Received message: " + message.getPayload());

            // Deserialize the incoming message
            WebRTCMessage webRTCMessage = gson.fromJson(message.getPayload(), WebRTCMessage.class);

            // Log the deserialized message for debugging
            System.out.println("Deserialized WebRTCMessage: " + webRTCMessage);

            // Handle the signaling message
            handleSignalingMessage(webRTCMessage, session);
        } catch (JsonSyntaxException e) {
            System.err.println("JSON syntax error: " + e.getMessage());
        } catch (Exception e) {
            // Log other types of errors for debugging
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleSignalingMessage(WebRTCMessage message, WebSocketSession session) throws IOException {
        // Check if the message is null
        if (message == null) {
            System.err.println("Received null WebRTCMessage.");
            return; // Exit if message is null
        }

        // Handle specific types of messages
        switch (message.getType()) {
            case "candidate":
                if (message.getCandidate() == null) {
                    System.err.println("Received null candidate in WebRTCMessage.");
                    return; // Exit if candidate is null
                }
                System.out.println("Received candidate: " + message.getCandidate().getCandidate());
                break;
            case "offer":
                System.out.println("Received offer: " + message.getSdp());
                break;
            case "answer":
                System.out.println("Received answer: " + message.getSdp());
                break;
            default:
                System.err.println("Unknown message type: " + message.getType());
                return;
        }

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions.values()) {
            if (!s.getId().equals(session.getId())) { // Don't send the message back to the sender
                s.sendMessage(new TextMessage(gson.toJson(message))); // Serialize the message back to JSON
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Session closed: " + session.getId());
    }
}
