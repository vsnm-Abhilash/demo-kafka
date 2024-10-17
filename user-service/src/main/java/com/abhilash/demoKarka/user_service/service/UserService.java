package com.abhilash.demoKarka.user_service.service;

import com.abhilash.demoKarka.user_service.dto.CreateUserRequestDto;
import com.abhilash.demoKarka.user_service.entity.User;
import com.abhilash.demoKarka.user_service.event.UserCreatedEvent;
import com.abhilash.demoKarka.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_USER_CREATED_TOPIC;

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public void createUser(CreateUserRequestDto createUserRequestDto){
        User user=mapper.map(createUserRequestDto,User.class);
        User savedUser=userRepository.save(user);
        UserCreatedEvent userCreatedEvent=mapper.map(savedUser,UserCreatedEvent.class);
        kafkaTemplate.send(KAFKA_USER_CREATED_TOPIC,userCreatedEvent.getId(),userCreatedEvent);
    }
}
