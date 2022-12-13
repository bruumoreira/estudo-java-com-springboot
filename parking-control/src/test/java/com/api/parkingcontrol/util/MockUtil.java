package com.api.parkingcontrol.util;

import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import com.api.parkingcontrol.persistence.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockUtil {

    public static Page<ParkingSpotEntity> mockPageParkingSpotEntity(){
        Page<ParkingSpotEntity> mockPageParkingSpotEntity = new PageImpl(MockUtil.mockListParkingSpotEntity());
        return mockPageParkingSpotEntity;
    }

    public static ParkingSpot mockParkingSpot() {
        ParkingSpot parkingSpotMock = new ParkingSpot();
        parkingSpotMock.setParkingSpotNumber("56");
        parkingSpotMock.setLicensePlateCar("EFCS-123");
        parkingSpotMock.setBrandCar("FIAT");
        parkingSpotMock.setModelCar("ARGO");
        parkingSpotMock.setColorCar("Vermelho");
        parkingSpotMock.setResponsibleName("Bruna Stefani");
        parkingSpotMock.setApartment("56");
        parkingSpotMock.setBlock("C");
        return parkingSpotMock;
    }

    public static final List<ParkingSpotEntity> mockListParkingSpotEntity() {
        List<ParkingSpotEntity> mockListParkingSpotEntity = new ArrayList<ParkingSpotEntity>();
        mockListParkingSpotEntity.add(MockUtil.mockParkingSpotEntity());
        return mockListParkingSpotEntity;
    }

    public static ParkingSpotEntity mockParkingSpotEntity() {
        ParkingSpotEntity parkingSpotEntityMock = new ParkingSpotEntity();
        parkingSpotEntityMock.setId(UUID.randomUUID());
        parkingSpotEntityMock.setParkingSpotNumber("56");
        parkingSpotEntityMock.setLicensePlateCar("EFCS-123");
        parkingSpotEntityMock.setBrandCar("FIAT");
        parkingSpotEntityMock.setModelCar("ARGO");
        parkingSpotEntityMock.setColorCar("Vermelho");
        parkingSpotEntityMock.setResponsibleName("Bruna Stefani");
        parkingSpotEntityMock.setApartment("56");
        parkingSpotEntityMock.setBlock("C");
        return parkingSpotEntityMock;
    }

    public static User mockUser() {
        return User.builder()
                .userName("Leo")
                .userAge(28)
                .userGender("Masculino")
                .userProfession("Assessor")
                .build();
    }

    public static UserEntity mockUserEntity() {
        UserEntity userEntityMock = new UserEntity();
        userEntityMock.setId(UUID.randomUUID());
        userEntityMock.setUserName("Leo");
        userEntityMock.setUserAge(28);
        userEntityMock.setUserGender("Masculino");
        userEntityMock.setUserProfession("Assessor");
        return userEntityMock;
    }

    public static Page<UserEntity> mockPageUserEntity(){
        Page<UserEntity> mockPageUserEntity = new PageImpl(MockUtil.mockListUserEntity());
        return mockPageUserEntity;
    }

    public static final List<UserEntity> mockListUserEntity() {
        List<UserEntity> mockListUserEntity = new ArrayList<UserEntity>();
        mockListUserEntity.add(MockUtil.mockUserEntity());
        return mockListUserEntity;
    }
}

