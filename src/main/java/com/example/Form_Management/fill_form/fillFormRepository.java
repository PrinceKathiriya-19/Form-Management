package com.example.Form_Management.fill_form;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface fillFormRepository extends JpaRepository<fillFormEntity, Integer> {
	
	@Query("SELECT new com.example.Form_Management.fill_form.fillFormDto(" +
		       "m.formId, " +
		       "MAX(f.formName), " +
		       "MAX(f.question.questionId), " +
		       "MAX(f.answer), " +
		       "MAX(f.submittedBy), " +
		       "MAX(u.userName), " +
		       "MAX(f.isSubmitted), " +
		       "MAX(f.completedDate)) " +
		       "FROM fillFormEntity f " +
		       "JOIN f.masterForm m " +
		       "LEFT JOIN masterUserEntity u ON m.createdBy = u.userId " +
		       "WHERE f.isSubmitted = true " +
		       "AND (:isAdmin = true OR f.submittedBy = :submittedBy) " +  // Check role dynamically
		       "GROUP BY m.formId")
		List<fillFormDto> findDistinctSubmittedForms(@Param("submittedBy") Integer submittedBy, 
		                                             @Param("isAdmin") Boolean isAdmin);

     
		 @Query("SELECT f FROM fillFormEntity f WHERE f.masterForm.formId = :formId")
		    List<fillFormEntity> findByFormId(@Param("formId") int formId);

}


/*
 * @Query("SELECT new com.example.Form_Management.fill_form.fillFormDto(" +
 * "m.formId, " + "MAX(f.formName), " + "MAX(f.question.questionId), " +
 * "MAX(f.answer), " + "MAX(f.submittedBy), " + "MAX(u.userName), " +
 * "MAX(f.isSubmitted), " + "MAX(f.completedDate)) " + "FROM fillFormEntity f "
 * + "JOIN f.masterForm m " +
 * "LEFT JOIN masterUserEntity u ON m.createdBy = u.userId " +
 * "WHERE f.isSubmitted = true " + "AND f.submittedBy = :submittedBy " +
 * "GROUP BY m.formId") List<fillFormDto>
 * findDistinctSubmittedForms(@Param("submittedBy") Integer submittedBy);
 */