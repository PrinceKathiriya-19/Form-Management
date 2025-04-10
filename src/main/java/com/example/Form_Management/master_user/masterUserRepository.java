package com.example.Form_Management.master_user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface masterUserRepository extends JpaRepository<masterUserEntity, Integer> {

	// to duplicate email
	boolean existsByUserEmail(String email);

	// to userid
	@Query("SELECT u FROM masterUserEntity u WHERE u.userId = :id")
	masterUserEntity findById(int id);

	// to update user
	@Modifying
	@Transactional
	@Query("UPDATE masterUserEntity u SET u.userName = :userName, u.userEmail = :userEmail, "
			+ "u.userContact = :userContact, u.userGender = :userGender, "
			+ "u.userValidFrom = :userValidFrom, u.userValidTo = :userValidTo, "
			+ "u.userRole = :userRole, u.active = :active, u.userImage = :userImage,u.modifiedBy = :modifiedBy,u.modifiedOn =CURRENT_TIMESTAMP WHERE u.id = :id")
	int updateUser(int id, String userName, String userEmail, String userContact, String userGender,
			String userValidFrom, String userValidTo, String userRole, String active, String userImage,int modifiedBy);

	// to delete user
	void deleteById(int id);
	
	//to login
	@Query("SELECT u FROM masterUserEntity u WHERE u.userEmail = :userEmail")
	Optional<masterUserEntity> findByUserEmail(String userEmail);

}
