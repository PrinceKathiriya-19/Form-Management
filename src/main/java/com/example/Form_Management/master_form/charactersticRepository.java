package com.example.Form_Management.master_form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface charactersticRepository extends JpaRepository<charactersticEntity, Integer> {
	
	
	 @Query("SELECT c FROM charactersticEntity c " +
	           "JOIN CharactersticModuleMappingEntity cm ON c.characteristicId = cm.characteristicId " +
	           "WHERE cm.moduleId = :moduleId")
	    List<charactersticEntity> findCharacteristicsByModule(@Param("moduleId") int moduleId);
}
