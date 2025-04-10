package com.example.Form_Management.fill_form;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class fillFormDto {

	private int formId;
	private String formName;
	private int questionId;
	private Object answer;
	private int submittedBy;
	private String Username;
	private LocalDateTime completedDate;

	public fillFormDto(int formId, String formName, int questionId, Object answer, int submittedBy, String username,
			Boolean isSubmitted, LocalDateTime completedDate) {
		this.formId = formId;
		this.formName = formName;
		this.questionId = questionId;
		this.answer = answer;
		this.submittedBy = submittedBy;
		this.Username = username;
		this.isSubmitted = isSubmitted;
		this.completedDate = completedDate;
	}

	@JsonProperty("isSubmitted")
	private Boolean isSubmitted;

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Object getAnswer() {
		return answer;
	}

	public void setAnswer(Object answer) {
		this.answer = answer;
	}

	public Boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(Boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	@JsonProperty("Username")
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public LocalDateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}
	

}
