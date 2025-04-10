package com.example.Form_Management.fill_form;
import com.example.Form_Management.master_form.*;
import com.example.Form_Management.security.*;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fillFomrImpl implements fillFormService {
@Autowired
fillFormRepository fillRepo;
@Autowired
masterFormRepository formRepo;
@Autowired
questionRepository questionRepo;
@Autowired
HttpSession session;
	@Override
	public fillFormEntity saveFillForm(fillFormDto fillDTO) {
		System.out.println("Received isSubmitted: " + fillDTO.isSubmitted());

		Optional<masterFormEntity> masterForm = formRepo.findById(fillDTO.getFormId());
        Optional<questionEntity> question = questionRepo.findById(fillDTO.getQuestionId());

        if (masterForm.isEmpty() || question.isEmpty()) {
            throw new RuntimeException("Invalid form ID or question ID.");
        }
        // Convert answer to List<String>
        List<String> answers;
        if (fillDTO.getAnswer() instanceof String) {
            answers = Collections.singletonList((String) fillDTO.getAnswer());
        } else if (fillDTO.getAnswer() instanceof List<?>) {
            answers = ((List<?>) fillDTO.getAnswer())
                        .stream()
                        .map(Object::toString)  
                        .collect(Collectors.toList());
        } else {
            answers = Collections.emptyList();
        }

      	 Integer loggedInUser = (Integer) session.getAttribute("loggedInUserId");

        fillFormEntity fillForm = new fillFormEntity(
            masterForm.get(),
            fillDTO.getFormName(),
            question.get(),
            String.join(",", answers),
            loggedInUser,  
            fillDTO.isSubmitted()
            
        );
        System.out.println("the fill form submit successfully");

        return fillRepo.save(fillForm);
	}
	@Override
	public List<fillFormDto> getAllfillForm() {
	    try {
	    	Integer loggedInUser = (Integer) session.getAttribute("loggedInUserId");
	    	String loggedInUserRole=(String) session.getAttribute("loggedInUserRole");
	    	 boolean isAdmin = "Admin".equalsIgnoreCase(loggedInUserRole); 
	        return fillRepo.findDistinctSubmittedForms(loggedInUser,isAdmin);
	    } catch (Exception e) {
	        System.out.println("error in get fill form service: " + e.getMessage());
	        return null;
	    }
	}
	@Override
	public List<fillFormEntity> getFormDetail(int formId) {
		try {
			return fillRepo.findByFormId(formId);
		} catch (Exception e) {
			System.out.println("error in the get form detail for preview service:"+e.getMessage());

		}
		return null;
		
	}

}
