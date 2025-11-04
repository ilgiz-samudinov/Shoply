package com.example.userservice.consumer;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "#{rabbitMqProperties.queue}")
    public void handleUserMessage(UserResponse userResponse) {
        userProfileService.createUserProfile(userResponse);
    }
}
