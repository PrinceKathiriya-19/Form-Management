package com.example.Form_Management.master_form;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class masterformDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int formId;
	private String formTitle;
	private String formAlias;
	private String module;
	private String characteristic;
	private String subCharacteristic;
	private String recurrence;
	private String startMonth;
	private String compliancePeriod;
	private String effectiveDate;
	private boolean formActive;
	private String formText;
	private List<questionDto> questions;

	public masterformDto() {
		
	}
	

	public int getFormId() {
		return formId;
	}


	public void setFormId(int formId) {
		this.formId = formId;
	}


	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormAlias() {
		return formAlias;
	}

	public void setFormAlias(String formAlias) {
		this.formAlias = formAlias;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getSubCharacteristic() {
		return subCharacteristic;
	}

	public void setSubCharacteristic(String subCharacteristic) {
		this.subCharacteristic = subCharacteristic;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getCompliancePeriod() {
		return compliancePeriod;
	}

	public void setCompliancePeriod(String compliancePeriod) {
		this.compliancePeriod = compliancePeriod;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isFormActive() {
		return formActive;
	}

	public void setFormActive(boolean formActive) {
		this.formActive = formActive;
	}

	public String getFormText() {
		return formText;
	}

	public void setFormText(String formText) {
		this.formText = formText;
	}

	public List<questionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<questionDto> questions) {
		this.questions = questions;
	}

}
