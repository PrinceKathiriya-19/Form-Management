package com.example.Form_Management.master_form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface questionRepository extends JpaRepository<questionEntity, Integer> {
	List<questionEntity> findByMasterformFormId(int formId);
    List<questionEntity> findByMasterform_FormId(int id);
    


}
