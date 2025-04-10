package com.example.Form_Management.fill_form;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface fillFormService {
	 fillFormEntity saveFillForm(fillFormDto fillDTO);
	 List<fillFormDto> getAllfillForm();
	 List<fillFormEntity> getFormDetail(int formId);
}
