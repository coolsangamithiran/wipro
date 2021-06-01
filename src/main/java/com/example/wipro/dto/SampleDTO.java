package com.example.wipro.dto;

 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 

@JsonIgnoreProperties(ignoreUnknown = true)

public class SampleDTO {

	private String name;

	private int sampleId;

	private boolean condition;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public int getSampleId() {

		return sampleId;

	}

	public void setSampleId(int sampleId) {

		this.sampleId = sampleId;

	}

	public boolean isCondition() {

		return condition;

	}

	public void setCondition(boolean condition) {

		this.condition = condition;

	}

}
