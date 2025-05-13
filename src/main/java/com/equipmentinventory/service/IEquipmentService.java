package com.equipmentinventory.service;

import java.util.List;
import java.util.Optional;

import com.equipmentinventory.entity.Equipment;

public interface IEquipmentService {
	
	public Optional<Equipment> getEquipmentById(Long id);
	
	public List<Equipment> gestListOfEquipment();
	
	public Equipment saveEquipment(Equipment equipment);
	
	public String deleteEquipment(Long id);
	
	public String updateEquipment(Equipment equipment);
	

}
