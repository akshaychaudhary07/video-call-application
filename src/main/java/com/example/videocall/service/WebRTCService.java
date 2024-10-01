package com.example.videocall.service;

import com.example.videocall.model.WebRTCMessage;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WebRTCService {
    private static final Logger logger = LoggerFactory.getLogger(WebRTCService.class);
    private final ObjectMapper objectMapper = new ObjectMapper(); // To create JsonNode

    public WebRTCMessage processMessage(WebRTCMessage message) {
        // Log the incoming message
        logger.info("Processing message: Type = {}, SDP = {}, Candidate = {}",
                message.getType(), message.getSdp(), message.getCandidate());

        switch (message.getType()) {
            case "offer":
                return handleOffer(message.getSdp());
            case "answer":
                return handleAnswer(message.getSdp());
            case "candidate":
                return handleCandidate(message.getCandidate());
            default:
                logger.warn("Unknown message type: {}", message.getType());
                throw new IllegalArgumentException("Unknown message type: " + message.getType());
        }
    }

    private WebRTCMessage handleOffer(JsonNode sdp) {
        // Implement offer handling logic (e.g., store the SDP or notify participants)
        logger.info("Handling offer: {}", sdp);

        // Acknowledgment message creation
        ObjectNode acknowledgment = objectMapper.createObjectNode();
        acknowledgment.put("message", "Offer received");

        return new WebRTCMessage("ack", null, acknowledgment);
    }

    private WebRTCMessage handleAnswer(JsonNode sdp) {
        // Implement answer handling logic (e.g., store the SDP or notify participants)
        logger.info("Handling answer: {}", sdp);

        // Acknowledgment message creation
        ObjectNode acknowledgment = objectMapper.createObjectNode();
        acknowledgment.put("message", "Answer received");

        return new WebRTCMessage("ack", null, acknowledgment);
    }

    private WebRTCMessage handleCandidate(WebRTCMessage.Candidate candidate) {
        // Implement candidate handling logic (e.g., notify other peers)
        logger.info("Handling candidate: {}", candidate.getCandidate());

        // Acknowledgment message creation
        ObjectNode acknowledgment = objectMapper.createObjectNode();
        acknowledgment.put("message", "Candidate received");

        return new WebRTCMessage("ack", candidate, acknowledgment);
    }
}
