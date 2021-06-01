
package com.example.wipro.controller;

 

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wipro.dto.SampleDTO;
import com.example.wipro.entity.SampleEntity;
import com.example.wipro.repository.SampleEntityRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

 

 

 

@RestController

@RequestMapping("/wiprocontroller")

public class WiproController {

	@Autowired

	private SampleEntityRepository sampleEntityRepository;

	@PostMapping("/add")

	public ResponseEntity<String> addvalues(@RequestBody(required = true) String requestBody) {

		try {

			ObjectMapper obj = new ObjectMapper();

			obj.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

			List<SampleDTO> sampleDTOLists = obj.readValue(requestBody, new TypeReference<List<SampleDTO>>() {

			});

			List<SampleEntity> sampleEntityList = new ArrayList<>();

			if (!CollectionUtils.isEmpty(sampleDTOLists)) {

				sampleDTOLists.stream().forEach(e -> {

					SampleEntity sampleEntity = new SampleEntity();

					sampleEntity.setCondition(e.isCondition());

					sampleEntity.setSampleId(e.getSampleId());

					sampleEntity.setName(e.getName());

					sampleEntityList.add(sampleEntity);

				});

				sampleEntityRepository.saveAll(sampleEntityList);

			}

		} catch (Exception e) {

			return new ResponseEntity<String>("addvalues Failed", HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>(" Values Created Successfully", HttpStatus.CREATED);

	}

	@GetMapping("/id")

	public ResponseEntity<SampleEntity> getvalues(@RequestParam(value = "id", required = false) Long id) {

		try {

			Optional<SampleEntity> sampleEntity = sampleEntityRepository.findById(id);

			if (sampleEntity.isPresent()) {

				return new ResponseEntity<SampleEntity>(sampleEntity.get(), HttpStatus.ACCEPTED);

			} else {

				return new ResponseEntity<SampleEntity>(HttpStatus.NO_CONTENT);

			}

		} catch (Exception e) {

			return new ResponseEntity<SampleEntity>(HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/validateLargestNumber")

	public ResponseEntity<String> validateLargestNumber(@RequestBody(required = true) String requestBody) {

		Integer sampleId = null;

		try {

			ObjectMapper obj = new ObjectMapper();

			obj.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

			List<SampleDTO> sampleDTOLists = obj.readValue(requestBody, new TypeReference<List<SampleDTO>>() {

			});

			Optional<SampleDTO> ss = null;

			if (!CollectionUtils.isEmpty(sampleDTOLists)) {

				ss = sampleDTOLists.stream().max(Comparator.comparing(SampleDTO::getSampleId));

				if (ss.isPresent()) {

					sampleId = ss.get().getSampleId();

				}

			}

		} catch (Exception e) {

			return new ResponseEntity<String>("validateLargestNumber Failed", HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>(sampleId.toString(), HttpStatus.CREATED);

	}

	@GetMapping("/findDuplicates")

	public ResponseEntity<String> findDuplicates(@RequestBody(required = true) String requestBody) {

		Set<String> listOfNames = null;

		try {

			ObjectMapper obj = new ObjectMapper();

			obj.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

			List<SampleDTO> sampleDTOLists = obj.readValue(requestBody, new TypeReference<List<SampleDTO>>() {

			});

			if (!CollectionUtils.isEmpty(sampleDTOLists)) {

				List<String> sampleNames = sampleDTOLists.stream().map(SampleDTO::getName).collect(Collectors.toList());

				listOfNames = sampleNames.stream()

						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()

						.filter(m -> m.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toSet());

			} else {

				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

			}

		} catch (Exception e) {

			return new ResponseEntity<String>("findDuplicates Failed", HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>(listOfNames.toString(), HttpStatus.CREATED);

	}

	@GetMapping("/removewhitespaces")

	public ResponseEntity<List<SampleDTO>> removewhitespaces(@RequestBody(required = true) String requestBody) {

		CopyOnWriteArrayList<SampleDTO> sampleDTOLists = null;

		try {

			ObjectMapper obj = new ObjectMapper();

			obj.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

			sampleDTOLists = obj.readValue(requestBody, new TypeReference<CopyOnWriteArrayList<SampleDTO>>() {

			});

			if (!CollectionUtils.isEmpty(sampleDTOLists)) {

				sampleDTOLists.stream().forEach(e -> {

					char[] strArray = e.getName().toCharArray();

					StringBuffer stringBuffer = new StringBuffer();

					for (int i = 0; i < strArray.length; i++) {

						if ((strArray[i] != ' ') && (strArray[i] != '\t')) {

							stringBuffer.append(strArray[i]);

						}

					}

					String noSpaceStr2 = stringBuffer.toString();

					e.setName(noSpaceStr2);

				});

			}

		} catch (Exception e) {

			return new ResponseEntity<List<SampleDTO>>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<List<SampleDTO>>(sampleDTOLists, HttpStatus.CREATED);

	}

}
