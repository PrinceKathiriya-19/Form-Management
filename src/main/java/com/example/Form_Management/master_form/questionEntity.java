package com.example.Form_Management.master_form;

import java.sql.Timestamp;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class questionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private int questionId;
	
//	@Column(name="form_id")
//	private int formId;

	@Column(name = "question_label")
	private String questionLabel;

	@Column(name = "question_name")
	private String questionName;

	
	@Column(name = "question_description", columnDefinition = "TEXT")
	private String questionDescription;

	@Column(name = "answer_type")
	private String answerType;
 
	
	@Column(name = "choice")
	 private String choices;

	@Column(name = "question_required")
	private boolean questionRequired;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "created_on", updatable = false)
	private Timestamp createdOn;

	@Column(name = "modified_by")
	private int modifiedBy;

	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	
	@ManyToOne
	@JoinColumn(name = "form_id", referencedColumnName = "form_id",nullable = false)
	private masterFormEntity masterform;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	

//	public int getFormId() {
//		return formId;
//	}
//
//	public void setFormId(int formId) {
//		this.formId = formId;
//	}

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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public masterFormEntity getMasterform() {
		return masterform;
	}

	public void setMasterform(masterFormEntity masterform) {
		this.masterform = masterform;
	}

	

	

	
	
	

	
	
	

}
