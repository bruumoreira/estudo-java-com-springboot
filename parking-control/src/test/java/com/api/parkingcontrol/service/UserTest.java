package com.api.parkingcontrol.service;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import com.api.parkingcontrol.persistence.UserEntity;
import com.api.parkingcontrol.repository.UserRepository;
import com.api.parkingcontrol.util.MockUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    public void getUserFindAllTest() throws Exception {
        Page<UserEntity> mockPageUserEntity= MockUtil.mockPageUserEntity();
        Pageable pageableMock = Mockito.mock(Pageable.class);
        when(repository.findAll(pageableMock)).thenReturn(mockPageUserEntity);
        Page<UserEntity> pageReturn = service.findAll(pageableMock);
        assertNotNull(pageReturn);
        assertEquals(1, pageReturn.getTotalElements());
    }

    @Test
    public void getUserFindByIdTest() throws Exception{
        UUID mockUuid = UUID.randomUUID();
        Optional<UserEntity> mockUserEntity = Optional.of(MockUtil.mockUserEntity());
        when(repository.findById(mockUuid)).thenReturn(mockUserEntity);
        Optional<UserEntity> optionalUser = service.findById(mockUuid);
        assertNotNull(optionalUser);
        assertEquals(mockUuid,
                mockUuid);
    }

    @Test
    public void saveUserWhenExistsByUserNameTest() throws Exception {
        User mockUser = MockUtil.mockUser();
        when(repository.existsByUserName(Mockito.any())).thenReturn(true);
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.save(mockUser)
        );
        assertNotNull(thrown);
        assertEquals("Conflict: Name is already in use!", thrown.getBusinessMessage());
    }

    @Test
    public void saveUserTest() throws Exception{
        User mockUser = MockUtil.mockUser();
        UserEntity mockUserEntity = MockUtil.mockUserEntity();
        when(repository.save(any())).thenReturn(mockUserEntity);
        UserEntity saveReturn = service.save(mockUser);
        assertNotNull(saveReturn);
        verify(repository).save(any());
    }

    @Test
    public void deleteUserTest() throws Exception{
        UUID mockUuid = MockUtil.mockParkingSpotEntity().getId();
        Optional<UserEntity> mockUserEntity = Optional.of(MockUtil.mockUserEntity());
        when(repository.findById(mockUuid)).thenReturn(mockUserEntity);
        doNothing().when(repository).delete(mockUserEntity.get());
        assertDoesNotThrow(() -> service.delete(mockUuid));
        verify(repository).delete(mockUserEntity.get());
    }

    @Test
    public void deleteUserTestWhenNotFoundTest() throws Exception{
        UUID mockUuid = MockUtil.mockUserEntity().getId();
        Optional<UserEntity> mockUserEntity = Optional.empty();
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.delete(mockUuid)
        );
        assertNotNull(thrown);
        assertEquals("User not found.", thrown.getBusinessMessage());
    }
}
