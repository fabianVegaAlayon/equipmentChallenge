package com.equipmentinventory.repository;

import static com.equipmentinventory.TestData.*;
import static com.equipmentinventory.TestData.createListEquipment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.equipmentinventory.entity.Equipment;

@SpringBootTest
class EquipmentRepositoryTest {

	@MockitoBean
	IEquipmentRepository iEquipmentRepository;
	
	
	
	@Test
	void testValidateFindById() {
		when(iEquipmentRepository.findById(1L)).thenReturn(createEquipment001());
		
		Optional<Equipment> equipment = iEquipmentRepository.findById(1L);
		assertNotNull(equipment);
		assertEquals(1L, equipment.orElseThrow().getSerialNumber());
		assertEquals("Lenovo", equipment.orElseThrow().getName());
		assertEquals("Computador de mesa", equipment.orElseThrow().getDescription());
		assertEquals("2025-05-13", equipment.orElseThrow().getPurchaseDate().toString());
		assertEquals("50000", equipment.orElseThrow().getPurchaseValue().toPlainString());
		
		
	}
	
	@Test
	void testGetAllEquipments() {
		when(iEquipmentRepository.findAll()).thenReturn(createListEquipment());
		List<Equipment> equipmentList = iEquipmentRepository.findAll();
		Equipment equipment2 =  equipmentList.getFirst();
		assertNotNull(equipmentList);
		assertEquals(2, equipmentList.size());
		assertTrue(equipmentList.contains(equipment2));

	}
	
	@Test
	void testUpdate() {
		Equipment updateEquipment = createEquipment();
		updateEquipment.setPurchaseDate( LocalDate.of(2025,1,1));
		updateEquipment.setPurchaseValue(new BigDecimal("85000"));
		when(iEquipmentRepository.save(any())).thenReturn(updateEquipment);
		
		Equipment equipment = iEquipmentRepository.save(createEquipment());
		assertEquals("Monitor", equipment.getDescription());
		assertEquals("2025-01-01", equipment.getPurchaseDate().toString());
		assertEquals("85000", equipment.getPurchaseValue().toPlainString());
	}
	
	@Test
	void testDelete() {
		when(iEquipmentRepository.findById(1L)).thenReturn(createEquipment001());
		iEquipmentRepository.deleteById(1L);
		verify(iEquipmentRepository).deleteById(1L);
		
		
		when(iEquipmentRepository.findById(1L)).thenReturn(Optional.empty());
		Optional<Equipment> equipmentDeleted = iEquipmentRepository.findById(1L);
		assertTrue(equipmentDeleted.isEmpty());

	}
	

}
