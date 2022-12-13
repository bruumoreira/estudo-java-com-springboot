package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import com.api.parkingcontrol.service.ParkingSpotService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Api(tags = "parking")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpot parkingSpot) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpot));
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        try {
            Optional<ParkingSpotEntity> parkingSpotModelOptional = parkingSpotService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/filter/name/{name}")
    public ResponseEntity<Object> getListParkingSpotBy(@PathVariable(value = "name") String responsibleName) {
        try {
            List<ParkingSpotEntity> listParkingSpot = parkingSpotService.findByName(responsibleName);
            return ResponseEntity.status(HttpStatus.OK).body(listParkingSpot);
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        try {
            parkingSpotService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpot parkingSpot) {
        try {
            parkingSpotService.update(id, parkingSpot);
            return ResponseEntity.status(HttpStatus.OK).body("Parking Spot updated successfully.");
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

}