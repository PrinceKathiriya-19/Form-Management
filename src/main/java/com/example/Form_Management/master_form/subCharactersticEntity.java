package com.example.Form_Management.master_form;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mst_subcharacteristic")
public class subCharactersticEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subcharacteristicid")
	private int subcharacteristicId;

	@Column(name = "subcharacteristicname", nullable = false)
	private String subcharacteristicName;
	
	@Column(name="characteristicid")
	private int characteristicId;

	public int getSubcharacteristicId() {
		return subcharacteristicId;
	}

	public void setSubcharacteristicId(int subcharacteristicId) {
		this.subcharacteristicId = subcharacteristicId;
	}

	public String getSubcharacteristicName() {
		return subcharacteristicName;
	}

	public void setSubcharacteristicName(String subcharacteristicName) {
		this.subcharacteristicName = subcharacteristicName;
		
		
	}

	public int getCharacteristicId() {
		return characteristicId;
	}

	public void setCharacteristicId(int characteristicId) {
		this.characteristicId = characteristicId;
	}

}
