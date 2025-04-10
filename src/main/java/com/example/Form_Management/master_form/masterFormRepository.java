package com.example.Form_Management.master_form;
//import com.example.Form_Management.fill_form.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface masterFormRepository extends JpaRepository<masterFormEntity, Integer> {
	@Query("SELECT MAX(m.formId) FROM masterFormEntity m")
	Integer findMaxFormId();
	
	Optional<masterFormEntity> findById(int formId);
	
	@Query("SELECT m FROM masterFormEntity m " +
		       "WHERE m.formActive = true " +
		       "AND m.formId NOT IN (" +
		       "   SELECT f.masterForm.formId FROM fillFormEntity f " +
		       "   WHERE f.submittedBy = :submittedBy AND f.isSubmitted = true" +
		       ")")
		List<masterFormEntity> findUnsubmittedForms(@Param("submittedBy") Integer submittedBy);
	
	
}
 