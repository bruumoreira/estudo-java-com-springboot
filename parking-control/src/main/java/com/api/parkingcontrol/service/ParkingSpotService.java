package com.api.parkingcontrol.service;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.exception.NotFoundParkingSpotException;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotEntity save(ParkingSpot parkingSpot) throws BusinessException {
        var parkingSpotEntity = new ParkingSpotEntity();
        BeanUtils.copyProperties(parkingSpot, parkingSpotEntity);
        parkingSpotEntity.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        if (this.existsByLicensePlateCar(parkingSpotEntity.getLicensePlateCar())) {
            throw new BusinessException("Conflict: License Plate Car is already in use!");
        }
        if (this.existsByParkingSpotNumber(parkingSpotEntity.getParkingSpotNumber())) {
            throw new BusinessException("Conflict: Parking Spot is already in use!");
        }
        if (this.existsByApartmentAndBlock(parkingSpotEntity.getApartment(), parkingSpotEntity.getBlock())) {
            throw new BusinessException("Conflict: Parking Spot already registered for this apartment/block!");
        }
        return parkingSpotRepository.save(parkingSpotEntity);
    }

    @Transactional
    public ParkingSpotEntity update(UUID id, ParkingSpot parkingSpot) throws BusinessException {
        Optional<ParkingSpotEntity> parkingSpotEntityOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotEntityOptional.isPresent()) {
            throw new NotFoundParkingSpotException();
        }
        var parkingSpotEntity = new ParkingSpotEntity();
        BeanUtils.copyProperties(parkingSpot, parkingSpotEntity);
        parkingSpotEntity.setId(parkingSpotEntityOptional.get().getId());
        parkingSpotEntity.setRegistrationDate(parkingSpotEntityOptional.get().getRegistrationDate());
        return parkingSpotRepository.save(parkingSpotEntity);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpotEntity> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotEntity> findById(UUID id) throws BusinessException {
        Optional<ParkingSpotEntity> parkingSpotEntityOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotEntityOptional.isPresent()) {
            throw new NotFoundParkingSpotException();
        }
        return parkingSpotEntityOptional;
    }

    public List<ParkingSpotEntity> findByName(String responsibleName) throws BusinessException {
        List<ParkingSpotEntity> listParkingSpotEntity = parkingSpotRepository.findByName(responsibleName);
        if (listParkingSpotEntity.isEmpty()) {
            throw new BusinessException("Name not found.");
        }
        return listParkingSpotEntity;
    }

    @Transactional
    public void delete(UUID id) throws BusinessException {
        Optional<ParkingSpotEntity> parkingSpotEntityOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotEntityOptional.isPresent()) {
            throw new NotFoundParkingSpotException();
        }
        parkingSpotRepository.delete(parkingSpotEntityOptional.get());
    }


}