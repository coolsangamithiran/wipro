package com.example.wipro.entity;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 

 

@Entity

@Table(name = "SampleEntity")

public class SampleEntity {

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "ID")

	private long id;

	@Column(name = "name")

	private String Name;

	@Column(name = "sampleId")

	private int sampleId;

	@Column(name = "condition")

	private boolean condition;

	public long getId() {

		return id;

	}

	public void setId(long id) {

		this.id = id;

	}

	public String getName() {

		return Name;

	}

	public void setName(String name) {

		Name = name;

	}

	public boolean isCondition() {

		return condition;

	}

	public void setCondition(boolean condition) {

		this.condition = condition;

	}

	public int getSampleId() {

		return sampleId;

	}

	public void setSampleId(int sampleId) {

		this.sampleId = sampleId;

	}

}
