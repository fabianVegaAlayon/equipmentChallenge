package com.equipmentinventory.service.implement;

import static com.equipmentinventory.TestData.*;
import static com.equipmentinventory.TestData.createListEquipment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.equipmentinventory.dto.EquipmentDto;
import com.equipmentinventory.entity.Equipment;
import com.equipmentinventory.repository.IEquipmentRepository;

@SpringBootTest
class EquipmentServiceImplTest {

	@Autowired
	EquipmentServiceImpl serviceImpl;
	@MockitoBean
	IEquipmentRepository iEquipmentRepository;

	@Test
	void testFindById() {
		when(iEquipmentRepository.findById(1L)).thenReturn(createEquipment001());
		Optional<EquipmentDto> equipment = serviceImpl.getEquipmentById(1L);
		assertNotNull(equipment);
		assertEquals(1L, equipment.orElseThrow().getSerialNumber());
		assertEquals("Lenovo", equipment.orElseThrow().getName());
		assertEquals("Computador de mesa", equipment.orElseThrow().getDescription());
		assertEquals("2025-05-13", equipment.orElseThrow().getPurchaseDate().toString());
		assertEquals("50000", equipment.orElseThrow().getPurchaseValue().toPlainString());
		assertEquals("50000.00", equipment.orElseThrow().getDeprecateValue().toPlainString());
	}

	@Test
	void testFindByIdWhenInvoiceNotExist() {
		when(iEquipmentRepository.findById(anyLong())).thenReturn((Optional.empty()));
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> serviceImpl.getEquipmentById(4L));
		assertEquals("The equipment 4 doesn't exist.", exception.getMessage());

	}

	@Test
	void testFindAll() {
		when(iEquipmentRepository.findAll()).thenReturn(createListEquipment());
		List<EquipmentDto> equipmentList = serviceImpl.gestListOfEquipment();
		assertNotNull(equipmentList);
		assertEquals(2, equipmentList.size());
		verify(iEquipmentRepository).findAll();
		EquipmentDto equipment =  equipmentList.getFirst();
		assertTrue(equipmentList.contains(equipment));
		assertEquals(2, equipmentList.size());
		assertEquals("50000", equipment.getPurchaseValue().toPlainString());
		assertEquals("50000.00", equipment.getDeprecateValue().toPlainString());

	}
	

	@Test
	void testFindAllWhenListIsEmpty() {
		when(iEquipmentRepository.findAll()).thenReturn(Collections.emptyList());
		List<EquipmentDto> equipmentList = serviceImpl.gestListOfEquipment();
		assertEquals(0, equipmentList.size());
		verify(iEquipmentRepository).findAll();
	}

	@Test
	void testDeleteEquipment() {
		when(iEquipmentRepository.findById(1L)).thenReturn(createEquipment001());
		String result = serviceImpl.deleteEquipment(1L);
		assertEquals("Successfully deleted equipment",result);
		verify(iEquipmentRepository).deleteById(1L);
		
	}
	
	@Test
	void testDeleteEquipmentWhenTheEquipmentDoesNotExist() {
		when(iEquipmentRepository.findById(1L)).thenReturn(Optional.empty());
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> serviceImpl.deleteEquipment(1L));
		assertEquals("The equipment 1 doesn't exist.", exception.getMessage());
		verify(iEquipmentRepository,never()).deleteById(1L);
	}

	
	@Test
	void testUpdateEquipoment() {
		when(iEquipmentRepository.findById(1L)).thenReturn(Optional.of( createEquipment()));
		Equipment equipment = createEquipment();
		assertEquals(1L, equipment.getSerialNumber());
		assertEquals("Benq", equipment.getName());
		assertEquals("Monitor", equipment.getDescription());
		
		//Updating the data
		equipment.setPurchaseDate( LocalDate.of(2020,4,12));
		equipment.setPurchaseValue(new BigDecimal("35000"));
		
		when(iEquipmentRepository.save(any())).thenReturn(equipment);
		String result = serviceImpl.updateEquipment(equipment);
		assertEquals("Equipment updated Successfully",result);
		verify(iEquipmentRepository).findById(1L);
		verify(iEquipmentRepository).save(equipment);

	}
	
	@Test
	void testUpdateEquipomentWhenIdDoesntExist() {
		when(iEquipmentRepository.findById(anyLong())).thenReturn((Optional.empty()));
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> serviceImpl.updateEquipment(createEquipment()));
		assertEquals("Equipment not found", exception.getMessage());
		verify(iEquipmentRepository,never()).deleteById(anyLong());
		
	}
	

	@Test
	void testSaveEquipment() {
		when(iEquipmentRepository.save(any(Equipment.class))).thenReturn(createEquipment());
		Equipment equipment = serviceImpl.saveEquipment(createEquipment());
		assertNotNull(equipment);
		verify(iEquipmentRepository).save(equipment);
		assertEquals(1L, equipment.getSerialNumber());
		
	}
	

	@Test
	void testWhenEquipmentIsNull() {
		when(iEquipmentRepository.save(any(Equipment.class))).thenReturn(null);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> serviceImpl.saveEquipment(null));
		assertEquals("The equipment cannot be null.", exception.getMessage());
		verify(iEquipmentRepository,never()).save(any(Equipment.class));
	}
	

	@Test
	void testcalculateDepreciation() {
		when(iEquipmentRepository.findById(1L)).thenReturn(Optional.of( createEquipment()));
		Optional<EquipmentDto> equipment = serviceImpl.getEquipmentById(1L);
		assertNotNull(equipment);
		assertEquals("26400.00", equipment.orElseThrow().getDeprecateValue().toPlainString());
		
		
		
		
	}
	
	
	

}
