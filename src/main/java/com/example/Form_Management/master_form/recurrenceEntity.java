package com.example.Form_Management.master_form;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_recurrance")
public class recurrenceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recurranceid")
	private int recurrenceId;

	@Column(name = "recurrancename", nullable = false)
	private String recurrenceName;

	public int getRecurrenceId() {
		return recurrenceId;
	}

	public void setRecurrenceId(int recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public String getRecurrenceName() {
		return recurrenceName;
	}

	public void setRecurrenceName(String recurrenceName) {
		this.recurrenceName = recurrenceName;
	}

	
	
}
