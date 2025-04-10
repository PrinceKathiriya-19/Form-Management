package com.example.Form_Management.master_form;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_characteristic")
public class charactersticEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int characteristicId;
	
	@Column(name="characteristic_name",nullable = false)
	private String characteristicName;

	public int getCharacteristicId() {
		return characteristicId;
	}

	public void setCharacteristicId(int characteristicId) {
		this.characteristicId = characteristicId;
	}

	public String getCharacteristicName() {
		return characteristicName;
	}

	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}

	

}
