package com.api.parkingcontrol.service;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.exception.NotFoundParkingSpotException;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.persistence.ParkingSpotEntity;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import com.api.parkingcontrol.util.MockUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotServiceTest {

    @Mock
    ParkingSpotRepository repository;

    @InjectMocks
    ParkingSpotService service;

    @Test
    public void saveParkingSpotSuccessTest() throws Exception {
        ParkingSpot mockParkingSpot = MockUtil.mockParkingSpot();
        when(repository.save(any())).thenReturn(MockUtil.mockParkingSpotEntity());
        ParkingSpotEntity entity = service.save(mockParkingSpot);
        assertNotNull(entity);
    }

    @Test
    public void saveParkingSpotWhenExistsByLicensePlateCarTest() throws Exception {
        ParkingSpot mockParkingSpot = MockUtil.mockParkingSpot();
        when(repository.existsByLicensePlateCar(Mockito.any())).thenReturn(true);
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.save(mockParkingSpot)
        );
        assertNotNull(thrown);
        assertEquals("Conflict: License Plate Car is already in use!", thrown.getBusinessMessage());
    }

    @Test
    public void saveParkingSpotWhenExistsByParkingSpotNumberTest() throws Exception{
        ParkingSpot mockParkingSpot = MockUtil.mockParkingSpot();
        when(repository.existsByParkingSpotNumber(Mockito.any())).thenReturn(true);
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.save(mockParkingSpot)
        );
        assertNotNull(thrown);
        assertEquals("Conflict: Parking Spot is already in use!", thrown.getBusinessMessage());
    }

    @Test
    public void saveParkingSpotWhenExistsByApartmentAndBlock() throws Exception{
        ParkingSpot mockParkingSpot = MockUtil.mockParkingSpot();
        when(repository.existsByApartmentAndBlock(any(),any())).thenReturn(true);
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.save(mockParkingSpot)
        );
        assertNotNull(thrown);
        assertEquals("Conflict: Parking Spot already registered for this apartment/block!", thrown.getBusinessMessage());
    }

    @Test
    public void updateParkingSpotIdTest() throws Exception{
        UUID mockUuid = UUID.randomUUID();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.of(MockUtil.mockParkingSpotEntity());
        when(repository.findById(mockUuid)).thenReturn(mockParkingSpotEntity);
        when(repository.save(any())).thenReturn(mockParkingSpotEntity.get());
        ParkingSpotEntity entityReturn = service.update(mockUuid, MockUtil.mockParkingSpot());
        assertNotNull(entityReturn);
    }

    @Test
    public void updateParkingSpotIdWhenNotFoundTest() throws Exception{
        UUID mockUuid = UUID.randomUUID();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.empty();
        when(repository.findById(mockUuid)).thenReturn(mockParkingSpotEntity);
        NotFoundParkingSpotException thrown = assertThrows(NotFoundParkingSpotException.class, () ->
                service.update(mockUuid, MockUtil.mockParkingSpot())
        );
        assertNotNull(thrown);
        assertEquals("Parking Spot not found.", thrown.getBusinessMessage());
    }

    @Test
    public void getParkingSpotFindAllTest() throws Exception {
        Page<ParkingSpotEntity> mockPageParkingSpotEntities = MockUtil.mockPageParkingSpotEntity();
        Pageable pageableMock = Mockito.mock(Pageable.class);
        when(repository.findAll(pageableMock)).thenReturn(mockPageParkingSpotEntities);
        Page<ParkingSpotEntity> pageReturn = service.findAll(pageableMock);
        assertNotNull(pageReturn);
        assertEquals(1, pageReturn.getTotalElements());
    }

    @Test
    public void getParkingSpotFindByNameTest() throws Exception{
        List<ParkingSpotEntity> mockListParkingSpotEntity = MockUtil.mockListParkingSpotEntity();
        when(repository.findByName(anyString())).thenReturn(mockListParkingSpotEntity);
        List<ParkingSpotEntity> listReturn = service.findByName("teste");
        assertNotNull(listReturn);
        assertEquals(1, listReturn.size());
    }

    @Test
    public void getParkingSpotFindByIdTest() throws Exception{
        UUID mockUuid = UUID.randomUUID();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.of(MockUtil.mockParkingSpotEntity());
        when(repository.findById(mockUuid)).thenReturn(mockParkingSpotEntity);
        Optional<ParkingSpotEntity> optionalParkingSpot = service.findById(mockUuid);
        assertNotNull(optionalParkingSpot);
        assertEquals(mockUuid,
                mockUuid);
    }

    @Test
    public void getParkingSpotNotFoundWhenFindByNameTest() throws Exception{
        when(repository.findByName(anyString())).thenReturn(new ArrayList<ParkingSpotEntity>());
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.findByName("teste")
        );
        assertNotNull(thrown);
        assertEquals("Name not found.", thrown.getBusinessMessage());
    }

    @Test
    public void getParkingSpotNotFoundWhenFindByIdTest() throws Exception{
        UUID mockUuid = MockUtil.mockParkingSpotEntity().getId();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.empty();
        when(repository.findById(mockUuid)).thenReturn(mockParkingSpotEntity);
        NotFoundParkingSpotException thrown = assertThrows(NotFoundParkingSpotException.class, () ->
                service.findById(mockUuid)
        );
        assertNotNull(thrown);
        assertEquals("Parking Spot not found.", thrown.getBusinessMessage());

    }
    @Test
    public void deleteParkingSpotTest() throws Exception{
        UUID mockUuid = MockUtil.mockParkingSpotEntity().getId();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.of(MockUtil.mockParkingSpotEntity());
        when(repository.findById(mockUuid)).thenReturn(mockParkingSpotEntity);
        doNothing().when(repository).delete(mockParkingSpotEntity.get());
        assertDoesNotThrow(() -> service.delete(mockUuid));
        verify(repository).delete(mockParkingSpotEntity.get());
    }

    @Test
    public void deleteParkingSpotWhenNotFoundTest() throws Exception{
        UUID mockUuid = MockUtil.mockParkingSpotEntity().getId();
        Optional<ParkingSpotEntity> mockParkingSpotEntity = Optional.empty();
        BusinessException thrown = assertThrows(BusinessException.class, () ->
                service.delete(mockUuid)
        );
        assertNotNull(thrown);
        assertEquals("Parking Spot not found.", thrown.getBusinessMessage());
    }
}
