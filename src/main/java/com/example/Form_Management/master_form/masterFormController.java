package com.example.Form_Management.master_form;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class masterFormController {
	@Autowired
	masterFormServices services;
	@Autowired
	masterFormRepository masterFormRepository;

	@GetMapping("/getModules")
	public ResponseEntity<List<moduleEntity>> geAllModule() {
		try {
			return ResponseEntity.ok(services.getAllModules());
		} catch (Exception e) {
			System.out.println("error to fetch module dropdown:" + e.getMessage());
		}
		return null;
	}

	@GetMapping("/getCharacteristic/{moduleId}")
	public ResponseEntity<List<charactersticEntity>> getAllCharacterstic(@PathVariable("moduleId") int moduleId) {
		try {
			return ResponseEntity.ok(services.getALLCharacterstic(moduleId));
		} catch (Exception e) {
			System.out.println("error to fetch characterstic dropdown:" + e.getMessage());
		}
		return null;

	}

	@GetMapping("/subcharacteristic/{characteristicId}")
	public ResponseEntity<List<subCharactersticEntity>> getAllSubCharacterstic(
			@PathVariable("characteristicId") int characteristicId) {
		try {
			return ResponseEntity.ok(services.getAllSubCharacterstic(characteristicId));
		} catch (Exception e) {
			System.out.println("erro to fetch subcaharcterstic dropdown:" + e.getMessage());

		}
		return null;

	}

	@GetMapping("/getRecurrence")
	public ResponseEntity<List<recurrenceEntity>> getAllRecurrance() {
		try {
			return ResponseEntity.ok(services.getALLrecurrance());
		} catch (Exception e) {
			System.out.println("error to fetch recurrance dropdown:" + e.getMessage());

		}
		return null;

	}

	@GetMapping("/getmonth")
	public ResponseEntity<List<monthEntity>> getAllmonth() {
		try {
			return ResponseEntity.ok(services.getALLmonth());
		} catch (Exception e) {
			System.out.println("error to fetch month dropdown:" + e.getMessage());

		}
		return null;

	}

	@GetMapping("/getanswer")
	public ResponseEntity<List<answerTypeEntity>> getAnswer() {
		try {
			return ResponseEntity.ok(services.getAnswer());
		} catch (Exception e) {
			System.out.println("error to fetch answer dropdown:" + e.getMessage());
		}
		return null;
	}

	// to add form
	@PostMapping("/saveForm")
	public ResponseEntity<String> saveForm(@RequestBody masterformDto formDto) {
	    System.out.println("Received form DTO: " + formDto);

		
		  if (formDto.getQuestions() == null || formDto.getQuestions().isEmpty()) {
		  System.out.println("No questions provided in DTO."); return null; } else {
		  System.out.println("Total Questions in DTO: " +
		  formDto.getQuestions().size()); }
		 
        try {
        	
            services.saveform(formDto);
            return ResponseEntity.ok("Form and questions saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving form: " + e.getMessage());
        }
    }

//	// to increment the formid
//	@GetMapping("/latest-id")
//	public ResponseEntity<Integer> getLatestFormId() {
//		Integer latestId = masterFormRepository.findMaxFormId();
//		return ResponseEntity.ok(latestId != null ? latestId + 1 : 1);
//	}
	
	//to display the form grid
	@GetMapping("/displayForm")
	public ResponseEntity<List<masterFormEntity>> displayformgrid(){
		try {
			List<masterFormEntity> formEntities=services.getallform();
			return ResponseEntity.ok(formEntities);
			
		} catch (Exception e) {
			System.out.println("error to display the form grid:"+e.getMessage());
		}
		return null;
	}
	

		@DeleteMapping("/deleteForm/{formId}")
		public ResponseEntity<String> deleteform(@PathVariable("formId") int id){
			try {
				services.deleteForm(id);
				return ResponseEntity.ok("Delete form successfully");
			} catch (Exception e) {
				System.out.println("erro in delete form controller:"+e.getMessage());
				return ResponseEntity.ok("erro in delete form");
			}
		}

	  @GetMapping("/getFormWithQuestions/{formId}")
	    public ResponseEntity<masterformDto> getFormById(@PathVariable("formId") int formId) {
	        try {
	            masterformDto formDto = services.getForm(formId);
	            return ResponseEntity.ok(formDto);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(null); 
	        }
	    }
	    
	    @PutMapping("/updateForm/{formId}")
	    public ResponseEntity<String> updateForm(
	            @PathVariable  int formId, 
	            @RequestBody masterformDto formDto) {
	        try {
	            services.updateForm(formId, formDto);
	            return ResponseEntity.ok("Form updated successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error updating form: " + e.getMessage());
	        }
	    }
	    
	    @GetMapping("/questions/{formId}")
	    public ResponseEntity<List<questionEntity>> getQuestions(@PathVariable int formId) {
	        List<questionEntity> questions = services.getQuestionsByFormId(formId);
	        
	        if (questions.isEmpty()) {
	        	 return ResponseEntity.ok(Collections.emptyList());
	        }
	        
	        return ResponseEntity.ok(questions); // 200 OK
	    }
	    
	    @GetMapping("/formDetails/{formId}")
	    public ResponseEntity<Map<String, String>> getFormDetails(@PathVariable Integer formId) {
	        Optional<masterFormEntity> form = masterFormRepository.findById(formId);
	        if (form.isPresent()) {
	            Map<String, String> response = new HashMap<>();
	            response.put("formTitle", form.get().getFormTitle());
	            response.put("formText", form.get().getFormText());
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	    
	    //fill form controller
	    @GetMapping("/unsubmitted")
	    public ResponseEntity<List<masterFormEntity>> getUnsubmittedForms() {
	    	try {
	        List<masterFormEntity> forms = services.getUnsubmittedForms();
	        return ResponseEntity.ok(forms);
	    	}catch (Exception e) {
				System.out.println("error in the fill form dropdown:"+e.getMessage());
			}
	    	return null;
	    }
	    
	    @GetMapping("/{formId}/questions")
	    public ResponseEntity<List<questionEntity>> getFormQuestions(@PathVariable int formId) {
	    	try {
	        List<questionEntity> questions = services.getQuestionsByFormId(formId);
	        return ResponseEntity.ok(questions);
	    	}catch (Exception e) {
				System.out.println("error in the  get question :"+e.getMessage());
			}
	    	return null;
	    }

	    
	}




