package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);

    @Query(value = "SELECT * FROM tb_parking_spot tps WHERE LOWER(responsible_name) like concat('%', lower(:name))", nativeQuery = true)
    List<ParkingSpotEntity> findByName(@Param("name") String responsibleName);

    List<ParkingSpotEntity> findByBrandCar(String brandCar); // se for muito simples

    @Query(value = "SELECT * FROM tb_parking_spot WHERE brand_car = :brandCar", nativeQuery = true)
        // se for mais complexa
    List<ParkingSpotEntity> findByBrandCarNative(@Param("brandCar") String brandCar);

    @Query(value = "SELECT p FROM ParkingSpotEntity p WHERE p.brandCar = ?1")
        // se for muito simples
    List<ParkingSpotEntity> findByBrandCarJpql(String brandCar);


}