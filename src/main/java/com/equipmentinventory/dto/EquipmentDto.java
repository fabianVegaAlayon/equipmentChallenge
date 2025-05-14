package com.equipmentinventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {
	
	
	private long serialNumber;
	private String name;
	private String description;
	private LocalDate purchaseDate;
	private BigDecimal purchaseValue;
	private BigDecimal deprecateValue;

}
