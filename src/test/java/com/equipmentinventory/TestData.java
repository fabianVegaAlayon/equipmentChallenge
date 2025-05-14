package com.equipmentinventory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.equipmentinventory.entity.Equipment;

public class TestData {
	
	public static Equipment createEquipment()
	{
		return new Equipment(1L, "Benq", "Monitor",  LocalDate.of(2022,4,12), new BigDecimal("30000"));
	}
	
	
	
	public static Optional<Equipment> createEquipment001()
	{
		return Optional.of(new Equipment(1L, "Lenovo", "Computador de mesa",  LocalDate.of(2025,5,13), new BigDecimal("50000")));
	}
	
	public static Optional<Equipment> createEquipment002()
	{
		return Optional.of(new Equipment(1L, "Benq", "Monitor",  LocalDate.of(2022,4,12), new BigDecimal("30000")));
	}
	
	
	
	
	public static List<Equipment> createListEquipment()
	{
		return Arrays.asList(new Equipment(1L, "Lenovo", "Computador de mesa",  LocalDate.of(2025,5,13), new BigDecimal("50000")),
				new Equipment(2L, "Benq", "Monitor",  LocalDate.of(2022,4,12), new BigDecimal("30000")));
	}

}
