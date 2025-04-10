package com.example.Form_Management.master_form;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_form")
public class masterFormEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "form_id")
	private int formId;

	@Column(name = "form_title")
	private String formTitle;

	@Column(name = "form_alias")
	private String formAlias;

	@Column(name = "module")
	private String module;
	
    @Column(name = "characteristic")
    private String characteristic;
    
    @Column(name = "sub_characteristic")
    private String subCharacteristic;
    
    @Column(name="recurrence")
    private String Recurrence;
    
    @Column(name = "start_month")
    private String startMonth;
    
    @Column(name = "compliance_period")
    private String compliancePeriod;
    
    @Column(name = "effective_Date")
    private String effectiveDate;
    
    @Column(name = "form_active")
    private Boolean formActive;
    
    @Column(name = "form_text", columnDefinition = "TEXT")
    private String formText;
    
    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "modified_by")
    private int modifiedBy;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

    
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
		return Recurrence;
	}

	public void setRecurrence(String recurrence) {
		Recurrence = recurrence;
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

	public Boolean getFormActive() {
		return formActive;
	}

	public void setFormActive(Boolean formActive) {
		this.formActive = formActive;
	}

	public String getFormText() {
		return formText;
	}

	public void setFormText(String formText) {
		this.formText = formText;
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

	
    
	

    
    


}
