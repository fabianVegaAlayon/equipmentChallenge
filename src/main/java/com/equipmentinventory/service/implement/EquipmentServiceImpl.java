package com.equipmentinventory.service.implement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.equipmentinventory.entity.Equipment;
import com.equipmentinventory.repository.IEquipmentRepository;
import com.equipmentinventory.service.IEquipmentService;

@Service
public class EquipmentServiceImpl implements IEquipmentService {

	private final IEquipmentRepository iEquipmentRepository;

	public EquipmentServiceImpl(IEquipmentRepository iEquipmentRepository) {
		this.iEquipmentRepository = iEquipmentRepository;
	}

	@Override
	public Optional<Equipment> getEquipmentById(Long id) {
		Optional<Equipment> equipment = iEquipmentRepository.findById(id);
		return equipment;
	}

	@Override
	public List<Equipment> gestListOfEquipment() {

		return iEquipmentRepository.findAll();
	}

	@Override
	public Equipment saveEquipment(Equipment equipment) {
		if (equipment == null) {
			throw new IllegalArgumentException("The equipment cannot be null.");
		}

		return iEquipmentRepository.save(equipment);
	}

	@Override
	public String deleteEquipment(Long id) {
		if (iEquipmentRepository.findById(id).isPresent()) {
			iEquipmentRepository.deleteById(id);
			return "Successfully deleted equipment";
		} else {
			throw new NoSuchElementException("The equipment " + id + " doesn't exist.");
		}

	}

	@Override
	public String updateEquipment(Equipment equipment) {
		Optional<Equipment> equipmentToUpdate = iEquipmentRepository.findById(equipment.getSerialNumber());
		if (equipmentToUpdate.isPresent()) {
			iEquipmentRepository.save(equipment);
			return "Equipment updated Successfully";
		} else {
			throw new NoSuchElementException("Equipment not found");
		}

	}

}
