package com.example.Form_Management.master_user;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.mail.Transport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession; // Import session handling
import jakarta.mail.Session;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


//import jakarta.websocket.Session;

@Service
public class masterUserServiceImpl implements masterUserServices {
@Autowired
masterUserRepository userRepo;
@Autowired
private  PasswordEncoder passwordEncoder;
@Autowired
private JavaMailSender mailSender; 
	@Override
	public void insertUser(masterUserEntity userEntity,HttpSession session) {
		try {
			if (userRepo.existsByUserEmail(userEntity.getUserEmail())) {
				System.out.println("Email Already Exists");
				return;
			}
			 userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
			 Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");
			 userEntity.setCreatedBy(loggedInUserId);
			 userRepo.save(userEntity);
			 System.out.println("User Insert Successfully");
			
		}catch (Exception e) {
			System.out.println("error insert user:"+e.getMessage());
		}
		
	}
	
	@Override
	public List<masterUserEntity> getAllUsers() {
		try {
		 return userRepo.findAll();
		}catch (Exception e) {
			System.out.println("error to display grid :"+e.getMessage());
			return new ArrayList<>();
		}
		
	}
	
	 @Override
	    public masterUserEntity getUserById(int id) {
	      	 return userRepo.findById(id);
	    }
	
	
	 public boolean updateUser(int id, masterUserEntity updatedUser) {
	        try {
	            int updatedRows = userRepo.updateUser(
	                id,
	                updatedUser.getUserName(),
	                updatedUser.getUserEmail(),
	                updatedUser.getUserContact(),
	                updatedUser.getUserGender(),
	                updatedUser.getUserValidFrom(),
	                updatedUser.getUserValidTo(),
	                updatedUser.getUserRole(),
	                updatedUser.getActive(),
	                updatedUser.getUserImage(),
	                updatedUser.getModifiedBy()
	               
	            );
	           

	            if (updatedRows > 0) {
	                System.out.println("User updated successfully");
	                return true;
	            } else {
	                System.out.println("User not found with ID: " + id);
	                return false;
	            }
	        } catch (Exception e) {
	            System.out.println("Error updating user: " + e.getMessage());
	            return false;
	        }
	    }

	@Override
	public void deleteuser(int id) {
		try {
			userRepo.deleteById(id);
		}catch (Exception e) {
			System.out.println("error delete user:"+e.getMessage());
		}
		
	}

	
	//genrate the password
	@Override
	public String genratePassword(int length) {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
	    Random random = new SecureRandom();
	    StringBuilder password = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        password.append(chars.charAt(random.nextInt(chars.length())));
	    }
		return password.toString();
	}

	 @Override
	    public void sendEmail(String userEmail, String plainPassword) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setTo(userEmail);
	            helper.setSubject("Your Account Password");
	            helper.setText("Hello,\n\nYour account has been created successfully.\nYour password is: " + plainPassword +
	                            "\n\nPlease change your password after logging in.\n\nThank you!");

	            mailSender.send(message);
	            System.out.println(" Email Sent Successfully...");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	

}
