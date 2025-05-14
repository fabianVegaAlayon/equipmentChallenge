package com.equipmentinventory.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "serial_number")
	private long serialNumber;
	private String name;
	private String description;
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	@Column(name = "purchase_value")
	private BigDecimal purchaseValue;

}
