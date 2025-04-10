package com.example.Form_Management.master_form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface subCharactersticRepository extends JpaRepository<subCharactersticEntity, Integer> {
	
    @Query("SELECT s FROM subCharactersticEntity s WHERE s.characteristicId = :characteristicId")
    List<subCharactersticEntity> findSubCharacteristicsByCharacteristic(@Param("characteristicId") int characteristicId);
}
