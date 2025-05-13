package com.equipmentinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipmentinventory.entity.Equipment;

@Repository
public interface IEquipmentRepository extends JpaRepository<Equipment, Long>{

}
