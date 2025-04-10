package com.example.Form_Management.fill_form;

import java.time.LocalDateTime;

import com.example.Form_Management.master_form.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fill_form")
public class fillFormEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fill_id")
	private int fillId;

	@ManyToOne
	@JoinColumn(name = "form_id", nullable = false)
	private masterFormEntity masterForm;

	@Column(name = "form_name", nullable = false)
	private String formName;

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	private questionEntity question;

	@Column(name = "answer", nullable = false, columnDefinition = "TEXT")
	private String answer;

	@Column(name = "submitted_by")
	private int submittedBy;

	@Column(name = "completed_date", nullable = false, updatable = false)
	private LocalDateTime completedDate = LocalDateTime.now();

	@Column(name = "is_submitted", nullable = false)
	private Boolean isSubmitted;

	public fillFormEntity() {

	}

	public fillFormEntity(masterFormEntity masterForm, String formName, questionEntity question, String answer,
			int submittedBy, Boolean isSubmitted) {
		this.masterForm = masterForm;
		this.formName = formName;
		this.question = question;
		this.answer = answer;
		this.submittedBy = submittedBy;
		this.completedDate = LocalDateTime.now(); 
		this.isSubmitted = isSubmitted;
	}

	public int getFillId() {
		return fillId;
	}

	public void setFillId(int fillId) {
		this.fillId = fillId;
	}

	public masterFormEntity getMasterForm() {
		return masterForm;
	}

	public void setMasterForm(masterFormEntity masterForm) {
		this.masterForm = masterForm;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public questionEntity getQuestion() {
		return question;
	}

	public void setQuestion(questionEntity question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}

	public LocalDateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

}
