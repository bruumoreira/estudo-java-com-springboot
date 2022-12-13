package com.api.parkingcontrol.service;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.exception.NotFoundUserException;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.persistence.UserEntity;
import com.api.parkingcontrol.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Transactional
    public UserEntity save(User user) throws BusinessException {
        var userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        if (this.existsByUserName(userEntity.getUserName())) {
            throw new BusinessException("Conflict: Name is already in use!");
        }
        return userRepository.save(userEntity);
    }

    @Transactional
    public void delete(UUID id) throws BusinessException {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) {
            throw new NotFoundUserException();
        }
        userRepository.delete(userEntity.get());
    }

}
