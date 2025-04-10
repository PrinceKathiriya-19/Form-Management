package com.example.Form_Management.master_form;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.hibernate.sql.results.graph.instantiation.internal.ArgumentDomainResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class questionDto {

	private int formId;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int questionId;
	
	private String questionLabel;

	private String questionName;

	private String questionDescription;

	private String answerType;

	private String choices;

	private boolean questionRequired;

	private Integer createdBy;

	private Timestamp createdOn;

	private Integer modifiedBy;

	private Timestamp modifiedOn;
	
	public questionDto() {
	    // Default constructor
	}


	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	
	
	public Integer getQuestionId() {
		return questionId;
	}


	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public List<String> getChoices() {
	    return Arrays.asList(choices.split(","));  
	}

	public void setChoices(List<String> choicesList) {
	    this.choices = String.join(",", choicesList);  
	}


	public boolean isQuestionRequired() {
		return questionRequired;
	}

	public void setQuestionRequired(boolean questionRequired) {
		this.questionRequired = questionRequired;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
}
