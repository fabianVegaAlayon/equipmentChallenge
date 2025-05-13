package com.equipmentinventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipmentinventory.entity.Equipment;
import com.equipmentinventory.service.IEquipmentService;



@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

	private final IEquipmentService iEquipmentService;

	public EquipmentController(IEquipmentService iEquipmentService) {
		this.iEquipmentService = iEquipmentService;
	}

	/*
	 * Aquí toca hacerle el ajuste para el retorno con la info relacionada con el
	 * dto que nos retornará el calculo de la depreciación
	 * 
	 */

	@GetMapping
	public ResponseEntity<List<Equipment>> findAll() {
		List<Equipment> equipmentList = iEquipmentService.gestListOfEquipment();
		if (equipmentList.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(equipmentList);
		}

	}

	/*
	 * Revisar lo que puede retornar al estar forzando el estatus y la
	 * excepcion que se lanza en el servicio 
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<?> findEquipmentById(@PathVariable Long id) {
		Optional<Equipment> equipment = iEquipmentService.getEquipmentById(id);
		if (equipment.isPresent()) {

			return ResponseEntity.ok(equipment);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}
	
	@PutMapping
	public ResponseEntity<Map<String, String>> updateEquipment(@RequestBody Equipment equipment){
		String message =  iEquipmentService.updateEquipment(equipment);
		Map<String, String> response = new HashMap<>();
	    response.put("message", message);
	    return  ResponseEntity.ok(response);
		
	}
	
	@PostMapping
	public ResponseEntity<Equipment> saveEquipment(@RequestBody Equipment equipment){
		Equipment equipmentToSave = iEquipmentService.saveEquipment(equipment);
		return ResponseEntity.status(HttpStatus.CREATED).body(equipmentToSave);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteEquipment(@PathVariable Long id) {
		String message = iEquipmentService.deleteEquipment(id);
		Map<String, String> response = new HashMap<>();
	    response.put("message", message);
		return  ResponseEntity.ok(response);		
	}
	
	
	
	 

}
