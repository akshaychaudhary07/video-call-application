/*
let localStream;
let peerConnection;
const configuration = {
    iceServers: [
        { urls: "stun:stun.l.google.com:19302" } // STUN server
    ]
};

// Create meeting button click handler
document.getElementById("createMeeting").onclick = function() {
    fetch('/api/rooms/create', {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("meetingCode").innerText = `Meeting Code: ${data.roomCode}`;
        startVideoCall('host'); // Start video for the host
    });
};

// Join meeting button click handler
document.getElementById("joinMeeting").onclick = function() {
    const roomCode = document.getElementById("roomCodeInput").value;
    fetch(`/api/rooms/join/${roomCode}`)
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Room not found');
    })
    .then(data => {
        document.getElementById("joinMessage").innerText = 'Successfully joined the meeting!';
        startVideoCall('user', data.roomCode); // Start video for the user
    })
    .catch(error => {
        document.getElementById("joinMessage").innerText = error.message;
    });
};

// Start video call function
function startVideoCall(role) {
    navigator.mediaDevices.getUserMedia({ video: true, audio: true })
    .then(stream => {
        localStream = stream;
        if (role === 'host') {
            document.getElementById("hostVideo").srcObject = stream;
            document.getElementById("hostSection").style.display = 'block';
        } else {
            document.getElementById("userVideo").srcObject = stream;
            document.getElementById("joinSection").style.display = 'block';
        }
        createPeerConnection(role);
    })
    .catch(error => {
        console.error("Error accessing media devices.", error);
    });
}

// Create peer connection function
function createPeerConnection(role) {
    peerConnection = new RTCPeerConnection(configuration);
    localStream.getTracks().forEach(track => {
        peerConnection.addTrack(track, localStream);
    });

    // Handle ICE candidates
    peerConnection.onicecandidate = event => {
        if (event.candidate) {
            console.log("New ICE candidate: ", event.candidate);
            // Send the candidate to the other peer
            // You would typically send this over your signaling server
        }
    };

    // When remote track is received
    peerConnection.ontrack = event => {
        if (role === 'host') {
            document.getElementById("userVideo").srcObject = event.streams[0];
        } else {
            document.getElementById("hostJoinVideo").srcObject = event.streams[0];
        }
    };

    // For demo purposes, we will create an offer immediately after establishing the connection
    if (localStream) {
        peerConnection.createOffer()
        .then(offer => peerConnection.setLocalDescription(offer))
        .then(() => {
            console.log("Offer sent: ", peerConnection.localDescription);
            // Send the offer to the other peer via your signaling server
        });
    }
}
*/
