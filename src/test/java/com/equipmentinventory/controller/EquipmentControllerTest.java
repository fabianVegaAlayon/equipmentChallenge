package com.equipmentinventory.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;

import com.equipmentinventory.dto.EquipmentDto;
import com.equipmentinventory.entity.Equipment;
import com.fasterxml.jackson.databind.ObjectMapper;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EquipmentControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	ObjectMapper objectMapper;
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp(){
		objectMapper = new ObjectMapper();
	}
	
	@Order(1)
	@Test
	void testFindAll() {
		ResponseEntity<EquipmentDto[]> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment", EquipmentDto[].class);
		List<EquipmentDto> equipmentList = Arrays.asList(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		EquipmentDto firstEquipment =  equipmentList.getFirst();
		EquipmentDto lastEquipment =  equipmentList.getLast();
		assertTrue(equipmentList.contains(firstEquipment));
		assertTrue(equipmentList.contains(lastEquipment));

	}
	@Order(2)
	@Test
	void testGetById(){
		ResponseEntity<EquipmentDto> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment/2", EquipmentDto.class);
		EquipmentDto equipment =  response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
		assertNotNull(equipment);
		
		assertEquals(2L, equipment.getSerialNumber());
		assertEquals("Lenovo", equipment.getName());
		assertEquals("Computador de mesa", equipment.getDescription());
		assertEquals("2025-05-13", equipment.getPurchaseDate().toString());
		assertEquals("50000.00", equipment.getPurchaseValue().toPlainString());
		assertEquals("50000.0000", equipment.getDeprecateValue().toPlainString());

	}
	
	@Order(3)
	@Test
	void testSave(){
		Equipment equipment = new Equipment(0,"Logitech", "Teclado",  LocalDate.of(2022,4,12), new BigDecimal("15000"));
		ResponseEntity<Equipment> response = testRestTemplate.postForEntity("http://localhost:" +port + "/api/equipment",equipment ,Equipment.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
		
		Equipment createdEquipment = response.getBody();
		assertNotNull(equipment);
		
		assertEquals(3L, createdEquipment.getSerialNumber());
		assertEquals("Logitech", createdEquipment.getName());
		assertEquals("Teclado", createdEquipment.getDescription());
		assertEquals("2022-04-12", createdEquipment.getPurchaseDate().toString());
		assertEquals("15000", createdEquipment.getPurchaseValue().toPlainString());

	}
	@Order(4)
	@Test
	void testUpdate()
	{
		ResponseEntity<EquipmentDto> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment/3", EquipmentDto.class);
		EquipmentDto equipment =  response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(equipment);
		assertEquals(3L, equipment.getSerialNumber());
		assertEquals("Logitech", equipment.getName());
		assertEquals("15000.00", equipment.getPurchaseValue().toPlainString());
		assertEquals("13200.0000", equipment.getDeprecateValue().toPlainString());
		
		//actualizar aquí 
		Equipment updateEquipment = new Equipment(3L, equipment.getName(), equipment.getDescription(),  LocalDate.of(2020,02,18), new BigDecimal("10000")); 
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipment> requestEntity =  new HttpEntity<>(updateEquipment, headers);
		
		ResponseEntity<String> request = testRestTemplate.exchange(
				"http://localhost:" +port + "/api/equipment",
				HttpMethod.PUT,
				requestEntity, 
				String.class);
		
		assertEquals(HttpStatus.OK, request.getStatusCode());
		assertEquals("{\"message\":\"Equipment updated Successfully\"}", request.getBody());
		
		response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment/3", EquipmentDto.class);
		equipment =  response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(equipment);
		assertEquals(3L, equipment.getSerialNumber());
		assertEquals("Logitech", equipment.getName());
		assertEquals("10000.00", equipment.getPurchaseValue().toPlainString());
		assertEquals("8000.0000", equipment.getDeprecateValue().toPlainString());	
	}
	
	@Order(5)
	@Test
	void testDelete() 
	{
		ResponseEntity<EquipmentDto[]> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment", EquipmentDto[].class);
		List<EquipmentDto> equipmentList = Arrays.asList(response.getBody());
		assertEquals(3, equipmentList.size());
		
		
		ResponseEntity<String> request = testRestTemplate.exchange(
				"http://localhost:" +port + "/api/equipment/1",
				HttpMethod.DELETE,
				null, 
				String.class);
		
		assertEquals(HttpStatus.OK, request.getStatusCode());
		assertTrue(request.hasBody());
		assertEquals("{\"message\":\"Successfully deleted equipment\"}", request.getBody());
		
		response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment", EquipmentDto[].class);
		equipmentList = Arrays.asList(response.getBody());
		assertEquals(2, equipmentList.size());
		
	}
	
	@Order(6)
	@Test
	void testGetAllWithEmptyList() 
	{
		
		ResponseEntity<String> request = testRestTemplate.exchange(
				"http://localhost:" +port + "/api/equipment/1",
				HttpMethod.DELETE,
				null, 
				String.class);
		
		
		request = testRestTemplate.exchange(
				"http://localhost:" +port + "/api/equipment/2",
				HttpMethod.DELETE,
				null, 
				String.class);
		
		request = testRestTemplate.exchange(
				"http://localhost:" +port + "/api/equipment/3",
				HttpMethod.DELETE,
				null, 
				String.class);
		
		ResponseEntity<EquipmentDto[]> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment", EquipmentDto[].class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		
		
	} 
	
	@Order(7)
	@Test
	void testFindByIdWithEmptyTable()
	{
		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/api/equipment/2", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("The equipment 2 doesn't exist."));
		
	}
	
	
	
	

}
