package com.example.Form_Management.master_form;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;


@Service
public class masterFormImpl implements masterFormServices {
	@Autowired
	moduleRepository moduleRepo;
	@Autowired
	charactersticRepository charactersticRepo;
	@Autowired
	subCharactersticRepository subCharactersticRepo;
	@Autowired
	recurranceRepository recurranceRepo;
	@Autowired
	monthRepository monthRepo;
	@Autowired
	answerTypeRepository answerTypeRepo;
	@Autowired
	masterFormRepository formRepo;
	@Autowired
	questionRepository questionRepo;
	@Autowired
	HttpSession session;

	@Override
	public List<moduleEntity> getAllModules() {
		try {
			return moduleRepo.findAll();
		} catch (Exception e) {
			System.out.println("error in module dropdown service:" + e.getMessage());
		}
		return null;

	}

	public List<charactersticEntity> getALLCharacterstic(int moduleId) {
		try {
			return charactersticRepo.findCharacteristicsByModule(moduleId);
		} catch (Exception e) {
			System.out.println("error in characterstic dropdown service:" + e.getMessage());
		}
		return null;

	}

	public List<subCharactersticEntity> getAllSubCharacterstic(int characteristicId) {
		try {
			return subCharactersticRepo.findSubCharacteristicsByCharacteristic(characteristicId);
		} catch (Exception e) {
			System.out.println("error in sub subcharcterstic dropdown service:" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<recurrenceEntity> getALLrecurrance() {
		try {
			return recurranceRepo.findAll();
		} catch (Exception e) {
			System.out.println("error in recurrence dropdown service:" + e.getMessage());
		}
		return null;

	}

	@Override
	public List<monthEntity> getALLmonth() {
		try {
			return monthRepo.findAll();
		} catch (Exception e) {
			System.out.println("error in month dropdown service:" + e.getMessage());
		}

		return null;
	}

	@Override
	public List<answerTypeEntity> getAnswer() {
		try {
			return answerTypeRepo.findAll();
		} catch (Exception e) {
			System.out.println("error in answer service:" + e.getMessage());
		}
		return null;

	}

	//save form
	@Override
	public void saveform(masterformDto formDto) {
        try {
        	 Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");
            // Convert DTO to Entity
            masterFormEntity formEntity = new masterFormEntity();
            formEntity.setFormTitle(formDto.getFormTitle());
            formEntity.setFormAlias(formDto.getFormAlias());
            formEntity.setModule(formDto.getModule());
            formEntity.setCharacteristic(formDto.getCharacteristic());
            formEntity.setSubCharacteristic(formDto.getSubCharacteristic());
            formEntity.setRecurrence(formDto.getRecurrence());
            formEntity.setStartMonth(formDto.getStartMonth());
            formEntity.setCompliancePeriod(formDto.getCompliancePeriod());
            formEntity.setEffectiveDate(formDto.getEffectiveDate());
            formEntity.setFormActive(formDto.isFormActive());
            formEntity.setFormText(formDto.getFormText());
            formEntity.setCreatedBy(loggedInUserId);
            formEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));

            // Save the form
            masterFormEntity savedForm = formRepo.save(formEntity);
            System.out.println("Form inserted successfully");
            

           
         // Save questions
            List<questionEntity> questions = new ArrayList<>();
            for (questionDto qDto : formDto.getQuestions()) {
                questionEntity question = new questionEntity();
                question.setQuestionLabel(qDto.getQuestionLabel());
                question.setQuestionName(qDto.getQuestionName());
                question.setQuestionDescription(qDto.getQuestionDescription());
                question.setAnswerType(qDto.getAnswerType());
                question.setChoices(qDto.getChoices());
                question.setQuestionRequired(qDto.isQuestionRequired());
                question.setCreatedBy(loggedInUserId);
                question.setCreatedOn(new Timestamp(System.currentTimeMillis()));

                
                
               
                question.setMasterform(savedForm); 

                questions.add(question);
            }
            questionRepo.saveAll(questions);
            System.out.println("Questions inserted successfully");
        } catch (Exception e) {
           // System.out.println("Error in saveform service: " + e.getMessage());
        	e.printStackTrace();
//            throw new RuntimeException("Failed to save form and questions");
        }
    }

//	public questionEntity saveQuestion(questionEntity question) {
//
//		try {
//
//			return questionRepo.save(question);
//
//		} catch (Exception e) {
//			System.out.println("error in saved question service:" + e.getMessage());
//		}
//		return null;
//
//	}

	@Override
	public List<masterFormEntity> getallform() {
		try {
			return formRepo.findAll();
		} catch (Exception e) {
			System.out.println("error in display form service:" + e.getMessage());
		}
		return null;
	}

	@Override
	public void deleteForm(int id) {
		try {
			formRepo.deleteById(id);
		}catch (Exception e) {
			System.out.println("Error in Delete Form Service:"+e.getMessage());
		}
		
		
	}
	
	//fetch the form
	@Override
	public masterformDto getForm(int formId) {
	    try {
	    	masterFormEntity formEntity;
	    	Optional<masterFormEntity> optionalForm = formRepo.findById(formId);

	    	if (optionalForm.isPresent()) {
	    	    formEntity = optionalForm.get();
	    	} else {
	    	    throw new RuntimeException("Form not found with ID: " + formId);
	    	}

	        // Map Entity to DTO
	        masterformDto formDto = new masterformDto();
	        formDto.setFormId(formEntity.getFormId());
	        formDto.setFormTitle(formEntity.getFormTitle());
	        formDto.setFormAlias(formEntity.getFormAlias());
	        formDto.setModule(formEntity.getModule());
	        formDto.setCharacteristic(formEntity.getCharacteristic());
	        formDto.setSubCharacteristic(formEntity.getSubCharacteristic());
	        formDto.setRecurrence(formEntity.getRecurrence());
	        formDto.setStartMonth(formEntity.getStartMonth());
	        formDto.setCompliancePeriod(formEntity.getCompliancePeriod());
	        formDto.setEffectiveDate(formEntity.getEffectiveDate());
	        formDto.setFormActive(formEntity.getFormActive());
	        formDto.setFormText(formEntity.getFormText());

	        List<questionEntity> questionEntities = questionRepo.findByMasterformFormId(formId);

	        // Map questions to DTO
	        if (questionEntities != null && !questionEntities.isEmpty()) {
	            List<questionDto> questionDtos = new ArrayList<>();

	            for (questionEntity question : questionEntities) {
	                questionDto qDto = new questionDto();
	                qDto.setQuestionId(question.getQuestionId());
	                qDto.setQuestionLabel(question.getQuestionLabel());
	                qDto.setQuestionName(question.getQuestionName());
	                qDto.setQuestionDescription(question.getQuestionDescription());
	                qDto.setAnswerType(question.getAnswerType());
	                qDto.setChoices(question.getChoices());
	                qDto.setQuestionRequired(question.isQuestionRequired());
	                qDto.setCreatedBy(question.getCreatedBy());
	                qDto.setModifiedBy(question.getModifiedBy());

	                questionDtos.add(qDto); 
	            }

	            formDto.setQuestions(questionDtos); 
	        }

	        return formDto;

	    } catch (Exception e) {
	        System.out.println("Error in getFormWithQuestions service: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Failed to retrieve form and questions");
	    }
	}

	//update form
	@Override
	public void updateForm(Integer id,masterformDto formDto) {
	    try {
       	 Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");

	        // Check if form exists
	    	Optional<masterFormEntity> existingFormOptional = formRepo.findById(id);
	        if (existingFormOptional.isEmpty()) {
	            throw new RuntimeException("Form not found with ID: " + formDto.getFormId());
	        }

	        masterFormEntity existingForm = existingFormOptional.get();

	        // Update form details
	        existingForm.setFormTitle(formDto.getFormTitle());
	        existingForm.setFormAlias(formDto.getFormAlias());
	        existingForm.setModule(formDto.getModule());
	        existingForm.setCharacteristic(formDto.getCharacteristic());
	        existingForm.setSubCharacteristic(formDto.getSubCharacteristic());
	        existingForm.setRecurrence(formDto.getRecurrence());
	        existingForm.setStartMonth(formDto.getStartMonth());
	        existingForm.setCompliancePeriod(formDto.getCompliancePeriod());
	        existingForm.setEffectiveDate(formDto.getEffectiveDate());
	        existingForm.setFormActive(formDto.isFormActive());
	        existingForm.setFormText(formDto.getFormText());
	        existingForm.setModifiedBy(loggedInUserId);
	        existingForm.setModifiedOn(new Timestamp(System.currentTimeMillis()));

	        // Save updated form details
	        masterFormEntity updatedForm = formRepo.save(existingForm);
	        System.out.println("Form updated successfully");

	        // Handle questions update logic
	        // Fetch existing questions from the database
	        List<questionEntity> existingQuestions = questionRepo.findByMasterform_FormId(updatedForm.getFormId());
	        Map<Integer, questionEntity> existingQuestionsMap = new HashMap<>();

	        for (questionEntity q : existingQuestions) {
	            existingQuestionsMap.put(q.getQuestionId(), q);
	        }


	        List<questionEntity> updatedQuestions = new ArrayList<>();
	        
	        for (questionDto qDto : formDto.getQuestions()) {
	            questionEntity question;
	            if (qDto.getQuestionId() != null && qDto.getQuestionId() > 0 && existingQuestionsMap.containsKey(qDto.getQuestionId()))  {
	              
	                question = existingQuestionsMap.get(qDto.getQuestionId());
	            } else {
	                // Create new question
	                question = new questionEntity();
	                question.setCreatedBy(loggedInUserId);
	                question.setCreatedOn(new Timestamp(System.currentTimeMillis()));
	            }
	            // Update question details
	            question.setQuestionLabel(qDto.getQuestionLabel());
	            question.setQuestionName(qDto.getQuestionName());
	            question.setQuestionDescription(qDto.getQuestionDescription());
	            question.setAnswerType(qDto.getAnswerType());
	            question.setChoices(qDto.getChoices() != null ? qDto.getChoices() : new ArrayList<>());
	            question.setQuestionRequired(qDto.isQuestionRequired());
	            question.setModifiedBy(loggedInUserId);
	            question.setModifiedOn(new Timestamp(System.currentTimeMillis()));

	            // Set Foreign Key relationship
	            question.setMasterform(updatedForm);
	            updatedQuestions.add(question);
	        }
	        Set<Integer> updatedQuestionIds = formDto.getQuestions().stream()
	                .map(questionDto::getQuestionId)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());

	        List<questionEntity> questionsDelete = existingQuestions.stream()
	                .filter(q -> !updatedQuestionIds.contains(q.getQuestionId()))
	                .collect(Collectors.toList());

	        questionRepo.deleteAll(questionsDelete); 
	        questionRepo.saveAll(updatedQuestions); 

	        System.out.println("Questions updated successfully");
	        // Save all updated questions
	        questionRepo.saveAll(updatedQuestions);
	        System.out.println("Questions updated successfully");

	    } catch (Exception e) {
	        System.out.println("Error in updateForm service: " + e.getMessage());
	        throw new RuntimeException("Failed to update form and questions");
	    }
	}

	//to get question
	@Override
	public List<questionEntity> getQuestionsByFormId(int formId) {
		try {
			return questionRepo.findByMasterform_FormId(formId);
		} catch (Exception e) {
			System.out.println("error in get question  service:"+e.getMessage());
		}
		return null;
		
	}

	//to get dropdown for the fill form
	@Override
	public List<masterFormEntity> getUnsubmittedForms() {
		try {
	      	 Integer loggedInUser = (Integer) session.getAttribute("loggedInUserId");
			return formRepo.findUnsubmittedForms(loggedInUser);
		} catch (Exception e) {
			System.out.println("error in fill dropdown service:"+e.getMessage());
		}
		return null;
		
	}



	
}
