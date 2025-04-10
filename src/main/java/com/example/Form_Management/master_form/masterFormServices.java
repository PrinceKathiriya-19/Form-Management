package com.example.Form_Management.master_form;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface masterFormServices {
	public List<moduleEntity> getAllModules();

	public List<charactersticEntity> getALLCharacterstic(int moduleid);

	public List<subCharactersticEntity> getAllSubCharacterstic(int characteristicId);

	public List<recurrenceEntity> getALLrecurrance();

	public List<monthEntity> getALLmonth();

	public List<answerTypeEntity> getAnswer();

	// save form
	public void saveform(masterformDto formDto);
	
	//display the form
    public List<masterFormEntity> getallform();
	
	//public questionEntity saveQuestion(questionEntity question);
    
    //to populate the form field
    public masterformDto getForm(int id);
    //to delete form
    public void deleteForm(int id);
    //to update the form
    public void updateForm( Integer id,masterformDto formDto);
    
    //get question for preview
    List<questionEntity> getQuestionsByFormId(int formId);
    
    //to get gropdwon for the fill form
    public List<masterFormEntity> getUnsubmittedForms();
}
