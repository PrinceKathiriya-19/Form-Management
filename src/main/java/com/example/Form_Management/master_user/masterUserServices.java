package com.example.Form_Management.master_user;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public interface masterUserServices {
	public void insertUser(masterUserEntity userEntity,HttpSession session);

	public List<masterUserEntity> getAllUsers();

	masterUserEntity getUserById(int id);

	boolean updateUser(int id, masterUserEntity updatedUser);

	public void deleteuser(int id);
	
	public String genratePassword(int length);
	
	public void sendEmail(String userEmail, String plainPassword);
}
