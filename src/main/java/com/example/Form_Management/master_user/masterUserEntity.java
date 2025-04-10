package com.example.Form_Management.master_user;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_user")
public class masterUserEntity implements UserDetails {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    private int userId;

	    @Column(name = "user_name",  nullable = false)
	    private String userName;

	    @Column(name = "user_email", nullable = false, unique = true)
	    private String userEmail;
	    
	    @Column(name = "user_password")
	     private String password;

	    @Column(name = "user_contact", nullable = false, unique = true)
	    private String userContact;

	    @Column(name = "user_gender")
	    private String userGender;

	    @Column(name = "user_validfrom")
	    private String userValidFrom;

	    @Column(name = "user_validto")
	    private String userValidTo;

	    @Column(name = "user_role")
	    private String userRole;

	    @Column(name = "user_image", length = 300)
	    private String userImage; 
	    
	    @Column(name = "user_active", length = 3)
	    private String active = "YES";

	    @Column(name = "created_by")
	    private int createdBy;
        
	    @CreationTimestamp
	    @Column(name = "created_on", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private Timestamp createdOn;

	    @Column(name = "modified_by")
	    private int modifiedBy;
         
	    @CreationTimestamp
	    @Column(name = "modified_on", insertable = false)
	    private Timestamp modifiedOn;

	    
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(() -> "ROLE_" + userRole);
	    }

	   
	    public String getPassword() {
	        return password;
	    }
	    
	    public void setPassword(String password) {
			this.password = password;
		}

	    public String getUsername() {
	        return userName;
	    }

	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    public boolean isEnabled() {
	        return active.equalsIgnoreCase("YES");
	    }
	    
		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}

		

		public String getUserContact() {
			return userContact;
		}

		public void setUserContact(String userContact) {
			this.userContact = userContact;
		}

		public String getUserGender() {
			return userGender;
		}

		public void setUserGender(String userGender) {
			this.userGender = userGender;
		}

		public String getUserValidFrom() {
			return userValidFrom;
		}

		public void setUserValidFrom(String userValidFrom) {
			this.userValidFrom = userValidFrom;
		}

		public String getUserValidTo() {
			return userValidTo;
		}

		public void setUserValidTo(String userValidTo) {
			this.userValidTo = userValidTo;
		}

		public String getUserRole() {
			return userRole;
		}

		public void setUserRole(String userRole) {
			this.userRole = userRole;
		}

		public String getUserImage() {
			return userImage;
		}

		public void setUserImage(String userImage) {
			this.userImage = userImage;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public int getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(int createdBy) {
			this.createdBy = createdBy;
		}

		public Timestamp getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Timestamp createdOn) {
			this.createdOn = createdOn;
		}

		public int getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(int modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public Timestamp getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(Timestamp modifiedOn) {
			this.modifiedOn = modifiedOn;
		}
	    

		
	    
	    
}
