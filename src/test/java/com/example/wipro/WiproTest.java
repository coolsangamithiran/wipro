package com.example.wipro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import com.example.wipro.controller.WiproController;
import com.example.wipro.dto.SampleDTO;
import com.example.wipro.entity.SampleEntity;
import com.example.wipro.repository.SampleEntityRepository;

@PowerMockIgnore({ "sun.misc.Launcher.*", "com.sun.*", "javax.*", "javax.ws.*", "org.mockito.*", "javax.management.*",
		"org.w3c.dom.*", "org.apache.logging.*", "org.slf4j.*" })
public class WiproTest {
	@InjectMocks
	WiproController wiproController;
	@Mock
	private SampleEntityRepository sampleEntityRepository;

	public void config() {
		MockitoAnnotations.initMocks(this);
	}

	@ObjectFactory
	public IObjectFactory getObjectFactory() {
		return new org.powermock.modules.testng.PowerMockObjectFactory();
	}

	String requestBody = "[{\r\n" + "\"name\":\"sanga\",\r\n" + "\"sampleId\":3,\r\n" + "\"condition\":true\r\n"
			+ "\r\n" + "},{\r\n" + "\"name\":\"vikcy\",\r\n" + "\"sampleId\":4,\r\n" + "\"condition\":true\r\n" + "\r\n"
			+ "}]";

	@Test(priority = 1)
	public void addvalues() throws Exception {
		config();
		List<SampleEntity> sampleEntityList = new ArrayList<>();
		Mockito.when(sampleEntityRepository.saveAll(Mockito.any())).thenReturn(sampleEntityList);
		ResponseEntity<String> responseEntity = wiproController.addvalues(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 201);
	}

	@Test(priority = 2)
	public void addvaluesException() throws Exception {
		config();
		Mockito.when(sampleEntityRepository.saveAll(Mockito.any())).thenThrow(Exception.class);
		ResponseEntity<String> responseEntity = wiproController.addvalues(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
	}

	@Test(priority = 3)
	public void getvalues() throws Exception {
		config();
		SampleEntity sampleEntity = new SampleEntity();
		sampleEntity.setId(1L);
		Optional<SampleEntity> sampleEntityv = Optional.ofNullable(sampleEntity);
		Mockito.when(sampleEntityRepository.findById(Mockito.anyLong())).thenReturn(sampleEntityv);
		ResponseEntity<SampleEntity> responseEntity = wiproController.getvalues(1L);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test(priority = 4)
	public void getvaluesNoContent() throws Exception {
		config();
		SampleEntity sampleEntity = null;
		Optional<SampleEntity> sampleEntityv = Optional.ofNullable(sampleEntity);
		Mockito.when(sampleEntityRepository.findById(Mockito.anyLong())).thenReturn(sampleEntityv);
		ResponseEntity<SampleEntity> responseEntity = wiproController.getvalues(1L);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 204);
	}

	@Test(priority = 5)
	public void getvaluesException() throws Exception {
		config();
		Mockito.when(sampleEntityRepository.findById(Mockito.anyLong())).thenThrow(Exception.class);
		ResponseEntity<SampleEntity> responseEntity = wiproController.getvalues(1L);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
	}

	@Test(priority = 6)
	public void validateLargestNumber() throws Exception {
		config();
		ResponseEntity<String> responseEntity = wiproController.validateLargestNumber(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test(priority = 7)
	public void findDuplicates() throws Exception {
		config();
		String requestBody = "[{\r\n" + "      \"name\": \"sanga\",\r\n" + "      \"sampleId\": 3,\r\n"
				+ "      \"condition\": true\r\n" + "\r\n" + "}, {\r\n" + "      \"name\": \"sanga\",\r\n"
				+ "      \"sampleId\": 4,\r\n" + "      \"condition\": true\r\n" + "\r\n" + "}]";
		ResponseEntity<String> responseEntity = wiproController.findDuplicates(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test(priority = 8)
	public void findDuplicatesException() throws Exception {
		config();
		String requestBody = "[{\r\n" + "      \"name\": null,\r\n" + "      \"sampleId\": 3,\r\n"
				+ "      \"condition\": true\r\n" + "\r\n" + "}, {\r\n" + "      \"name\": \"vikcy\",\r\n"
				+ "      \"sampleId\": 4,\r\n" + "      \"condition\": true\r\n" + "\r\n" + "}]";
		ResponseEntity<String> responseEntity = wiproController.findDuplicates(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
	}

	@Test(priority = 9)
	public void removewhitespaces() throws Exception {
		config();
		String requestBody = "[{\r\n" + "      \"name\": \"sanga mithiran\",\r\n" + "      \"sampleId\": 3,\r\n"
				+ "      \"condition\": true\r\n" + "\r\n" + "}, {\r\n" + "      \"name\": \"vicky kaushal\",\r\n"
				+ "      \"sampleId\": 4,\r\n" + "      \"condition\": true\r\n" + "\r\n" + "}]";
		ResponseEntity<List<SampleDTO>> responseEntity = wiproController.removewhitespaces(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test(priority = 10)
	public void removewhitespacesException() throws Exception {
		config();
		String requestBody = "[{\r\n" + "      \"name\": null,\r\n" + "      \"sampleId\": 3,\r\n"
				+ "      \"condition\": true\r\n" + "\r\n" + "}, {\r\n" + "      \"name\": \"vicky kaushal\",\r\n"
				+ "      \"sampleId\": 4,\r\n" + "      \"condition\": true\r\n" + "\r\n" + "}]";
		ResponseEntity<List<SampleDTO>> responseEntity = wiproController.removewhitespaces(requestBody);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
	}
}
