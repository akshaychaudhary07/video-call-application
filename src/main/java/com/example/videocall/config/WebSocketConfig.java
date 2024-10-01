package com.example.videocall.config;

import com.example.videocall.handler.WebRtcSignalingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    private final WebRtcSignalingHandler signalingHandler;

    public WebSocketConfig(WebRtcSignalingHandler signalingHandler) {
        this.signalingHandler = signalingHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalingHandler, "/webrtc-signaling") // Corrected to use the handler bean
                .setAllowedOrigins("http://localhost:3000") // Make sure this matches your frontend
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

//    @Override
//    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
//        converters.removeIf(StringHttpMessageConverter.class::isInstance);
//    }
}
