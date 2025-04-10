package com.example.Form_Management.fill_form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Form_Management.master_form.questionRepository;

@RestController
public class fillFormController {
@Autowired
fillFormService services;
@Autowired
questionRepository questionRepo;



@PostMapping("/submit-fill-form")
public ResponseEntity<String> saveFillForm(@RequestBody List<fillFormDto> fillDTOList) {
    System.out.println("Received Data: " + fillDTOList);

    for (fillFormDto response : fillDTOList) {
    	 System.out.println("Processing Form ID: " + response.getFormId());
         System.out.println("Received isSubmitted: " + response.isSubmitted());
        services.saveFillForm(response); 
    }
    return ResponseEntity.ok("Form submitted successfully!");
    
}

@GetMapping("/getFillForm")
public ResponseEntity<?> getallform() {
    try {
        List<fillFormDto> forms = services.getAllfillForm();
        return ResponseEntity.ok(forms); // Return JSON response
    } catch (Exception e) {
        System.out.println("Error in get fill form: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching forms");
    }
}


@GetMapping("/getFormDetails/{formId}")
public ResponseEntity<?> getFormDetails(@PathVariable int formId) {
    List<fillFormEntity> filledForms = services.getFormDetail(formId);

    if (filledForms.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form not found");
    }

    fillFormEntity firstEntry = filledForms.get(0);

    Map<String, Object> response = new HashMap<>();
    response.put("completedDate", firstEntry.getCompletedDate());
    response.put("formName", firstEntry.getMasterForm().getFormTitle()); 
    response.put("description", firstEntry.getMasterForm().getFormText()); 

    
    List<Map<String, String>> questionsList = new ArrayList<>();
    for (fillFormEntity entry : filledForms) {
        Map<String, String> questionData = new HashMap<>();
        questionData.put("title", entry.getQuestion().getQuestionName());
        questionData.put("description", entry.getQuestion().getQuestionDescription());
        questionData.put("answer", entry.getAnswer());

        questionsList.add(questionData);
    }
    
    response.put("questions", questionsList);

    return ResponseEntity.ok(response);
}

}
