package com.example.Form_Management.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Form_Management.master_form.masterFormRepository;

@Controller
public class loginController {

    
	
	@Autowired
	masterFormRepository masterFormRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "index"; 
    }

    @GetMapping("/master_form")
    public String masterForm(Model model) {
    	Integer latestId = masterFormRepository.findMaxFormId();
    	model.addAttribute("id",latestId != null ? latestId + 1 : 1);
        return "master_form";
    }

    @GetMapping("/fill_forms")
    public String fillForm() {
        return "fill_forms";
    }

    @GetMapping("/completed_forms")
    public String completeForm() {	
        return "completed_forms";
    }

    @GetMapping("/master_users")
    public String masterUser() {
        return "master_users";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
