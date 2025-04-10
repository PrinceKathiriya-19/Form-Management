package com.example.Form_Management.master_user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@RestController
public class masterUserController {
	@Autowired
	masterUserRepository userRepository;
	@Autowired
	masterUserServices services;
	@Autowired
	private  PasswordEncoder passwordEncoder;

	private final String uploadDir = "C:\\projectPhoto\\";

	@PostMapping("/saveuser")
	public ResponseEntity<String> createUser(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("contact") String contact,
			@RequestParam("gender") String gender, @RequestParam("validFrom") String validFrom,
			@RequestParam("validTo") String validTo, @RequestParam("role") String role,
			@RequestParam(value = "image", required = false) MultipartFile imageFile,
			HttpSession session ){
		System.out.println(" /saveuser endpoint hit!"); // ✅ Debug
		try {

			if (userRepository.existsByUserEmail(email)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Exists");
			}
			
	        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");
	        System.out.println(" Logged in User ID: " + loggedInUserId);
	        if (loggedInUserId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
	        }
	       
	        String plainPassword =services.genratePassword(8); // 8-character password
	        String encryptedPassword = passwordEncoder.encode(plainPassword);

	        
	        
			// Image 
			String imageName = "default.png";
			if (imageFile != null && !imageFile.isEmpty()) {
				Files.createDirectories(Paths.get(uploadDir));
				imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
				Path filePath = Paths.get(uploadDir, imageName);
				Files.write(filePath, imageFile.getBytes());
			}

			
			masterUserEntity user = new masterUserEntity();
			user.setUserName(fname + " " + lname);
			user.setUserEmail(email);
			user.setUserContact(contact);
			user.setUserGender(gender);
			user.setUserValidFrom(validFrom);
			user.setUserValidTo(validTo);
			user.setUserRole(role);
			user.setUserImage(imageName);
			user.setPassword(encryptedPassword);
			user.setCreatedBy(loggedInUserId);
			user.setActive("YES");

			
			userRepository.save(user);
			 services.sendEmail(email, plainPassword);

			return ResponseEntity.ok("User created successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating user: " + e.getMessage());
		}
	}

	@GetMapping("/displaygrid")
	public ResponseEntity<List<masterUserEntity>> getalluser() {
		List<masterUserEntity> userEntities = services.getAllUsers();
		System.out.println(userEntities);
		return ResponseEntity.ok(userEntities);
	}

@PutMapping("/updateuser/{id}")
public ResponseEntity<String> updateUser(
        @PathVariable int id,
        @RequestParam("fname") String fname,
        @RequestParam("lname") String lname,
        @RequestParam("email") String email,
        @RequestParam("contact") String contact,
        @RequestParam("gender") String gender,
        @RequestParam("validFrom") String validFrom,
        @RequestParam("validTo") String validTo,
        @RequestParam("role") String role,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        @RequestParam("active") String active,
        HttpSession session) {

    try {
        masterUserEntity existingUser = services.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        }

       
        String imageName = existingUser.getUserImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            Files.createDirectories(Paths.get(uploadDir));
            imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, imageName);
            Files.write(filePath, imageFile.getBytes());
        }
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        
        existingUser.setUserName(fname + " " + lname);
        existingUser.setUserEmail(email);
        existingUser.setUserContact(contact);
        existingUser.setUserGender(gender);
        existingUser.setUserValidFrom(validFrom);
        existingUser.setUserValidTo(validTo);
        existingUser.setUserRole(role);
        existingUser.setUserImage(imageName);
        existingUser.setActive(active);
        existingUser.setModifiedBy(loggedInUserId);

        boolean isUpdated = services.updateUser(id, existingUser);

        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
    }

}

@DeleteMapping("/deleteuser/{userId}")
public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId){
	try {
		services.deleteuser(userId);
		return ResponseEntity.ok("delete user successfully");
	} catch (Exception e) {
		return ResponseEntity.ok("Error deleting user");
	}
}










}
