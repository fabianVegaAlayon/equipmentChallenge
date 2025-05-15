package com.equipmentinventory.service.implement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.equipmentinventory.dto.EquipmentDto;
import com.equipmentinventory.entity.Equipment;
import com.equipmentinventory.repository.IEquipmentRepository;
import com.equipmentinventory.service.IEquipmentService;

@Service
public class EquipmentServiceImpl implements IEquipmentService {

	private final IEquipmentRepository iEquipmentRepository;
	/*
	 * This is the rate for the deprecation value
	 */
	private final BigDecimal RATE = new BigDecimal("0.04");

	public EquipmentServiceImpl(IEquipmentRepository iEquipmentRepository) {
		this.iEquipmentRepository = iEquipmentRepository;
	}

	@Override
	public Optional<EquipmentDto> getEquipmentById(Long id) {
		Optional<Equipment> equipmentOpt = iEquipmentRepository.findById(id);
		if (equipmentOpt.isPresent()) {
			Equipment equipment = equipmentOpt.orElseThrow();
			BigDecimal deprecatedValue = calculateDepreciation(equipment);

			return Optional.ofNullable(mapToEquipmentDto(equipment, deprecatedValue));
		} else {
			throw new NoSuchElementException("The equipment " + id + " doesn't exist.");
		}

	}

	@Override

	public List<EquipmentDto> gestListOfEquipment() {
		List<Equipment> equipments = iEquipmentRepository.findAll();

		return equipments.stream().map(equipment -> {
			BigDecimal deprecatedValue = calculateDepreciation(equipment);
			return mapToEquipmentDto(equipment, deprecatedValue);
		}).collect(Collectors.toList());
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

	/**
	 * This method allow us calculate the depreciation of an equipment
	 * the formula applied is: depreciation = (purchaseValue*rate)*years
	 * value deprecate = purchaseValue - depreciation
	 * @param equipment
	 * @return if valueDeprecate is negative, the result will be BigDecimal.ZERO, 
	 * thus avoiding negative values; if positive, returns valueDeprecate as is
	 */
	private BigDecimal calculateDepreciation(Equipment equipment) {
		long years = ChronoUnit.YEARS.between(equipment.getPurchaseDate(), LocalDate.now());
		// BigDecimal rate = new BigDecimal("0.04");
		BigDecimal deprecation = equipment.getPurchaseValue().multiply(RATE).multiply(new BigDecimal(years));
		BigDecimal valueDeprecate = equipment.getPurchaseValue().subtract(deprecation);
		return valueDeprecate.max(BigDecimal.ZERO);
	}

	private EquipmentDto mapToEquipmentDto(Equipment equipment, BigDecimal deprecatedValue) {
		return new EquipmentDto(equipment.getSerialNumber(), equipment.getName(), equipment.getDescription(),
				equipment.getPurchaseDate(), equipment.getPurchaseValue(), deprecatedValue);
	}

}
