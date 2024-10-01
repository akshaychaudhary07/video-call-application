package com.example.videocall.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any unrecognized fields
public class WebRTCMessage implements Serializable {
    @JsonProperty("type")
    private String type;  // Type of the message (e.g., "offer", "answer", "candidate")

    private Candidate candidate; // For candidate messages

    @JsonProperty("sdp")
    private JsonNode sdp; // For offer/answer messages

    // Default constructor
    public WebRTCMessage() {}

    @JsonCreator
    public WebRTCMessage(
            @JsonProperty("type") String type,
            @JsonProperty("candidate") Candidate candidate,
            @JsonProperty("sdp") JsonNode sdp) {
        this.type = Optional.ofNullable(type).orElseThrow(() -> new IllegalArgumentException("Type is required"));
        this.candidate = candidate; // Candidate can be optional
        this.sdp = sdp; // SDP can be optional
    }

    // Getters
    public String getType() {
        return type;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    @JsonProperty("sdp")
    public JsonNode getSdp() {
        return sdp;
    }

    // Setters (with optional validation)
    public void setType(String type) {
        this.type = Optional.ofNullable(type).orElseThrow(() -> new IllegalArgumentException("Type is required"));
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate; // Candidate can be optional
    }

    @JsonProperty("sdp")
    public void setSdp(JsonNode sdp) {
        this.sdp = sdp; // SDP can be optional
    }

    // Inner class to represent a candidate
    @JsonIgnoreProperties(ignoreUnknown = true) // Ignore any unrecognized fields
    public static class Candidate {
        @JsonProperty("candidate")
        private String candidate; // Candidate string

        @JsonProperty("sdpMid")
        private String sdpMid;    // Media stream identifier

        @JsonProperty("sdpMLineIndex")
        private int sdpMLineIndex; // Media line index

        private String usernameFragment; // Field for usernameFragment

        // Constructor for Candidate
        @JsonCreator
        public Candidate(
                @JsonProperty("candidate") String candidate,
                @JsonProperty("sdpMid") String sdpMid,
                @JsonProperty("sdpMLineIndex") int sdpMLineIndex,
                @JsonProperty("usernameFragment") String usernameFragment) {
            this.candidate = Optional.ofNullable(candidate).orElseThrow(() -> new IllegalArgumentException("Candidate is required"));
            this.sdpMid = sdpMid; // SDP Mid can be optional
            this.sdpMLineIndex = sdpMLineIndex; // sdpMLineIndex might be 0 or positive
            this.usernameFragment = usernameFragment; // usernameFragment can be optional
        }

        // Default constructor for deserialization
        public Candidate() {}

        // Getters
        public String getCandidate() {
            return candidate;
        }

        @JsonProperty("sdpMid")
        public String getSdpMid() {
            return sdpMid;
        }

        @JsonProperty("sdpMLineIndex")
        public int getSdpMLineIndex() {
            return sdpMLineIndex;
        }

        public String getUsernameFragment() {
            return usernameFragment;
        }

        // Setters
        public void setCandidate(String candidate) {
            this.candidate = candidate;
        }

        @JsonProperty("sdpMid")
        public void setSdpMid(String sdpMid) {
            this.sdpMid = sdpMid;
        }

        @JsonProperty("sdpMLineIndex")
        public void setSdpMLineIndex(int sdpMLineIndex) {
            this.sdpMLineIndex = sdpMLineIndex;
        }

        public void setUsernameFragment(String usernameFragment) {
            this.usernameFragment = usernameFragment;
        }
    }
}
