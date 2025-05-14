package com.equipmentinventory.service;

import java.util.List;
import java.util.Optional;

import com.equipmentinventory.dto.EquipmentDto;
import com.equipmentinventory.entity.Equipment;

public interface IEquipmentService {
	
	public Optional<EquipmentDto> getEquipmentById(Long id);
	
	public List<EquipmentDto> gestListOfEquipment();
	
	public Equipment saveEquipment(Equipment equipment);
	
	public String deleteEquipment(Long id);
	
	public String updateEquipment(Equipment equipment);
	

}
