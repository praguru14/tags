package com.tag.backend.entity;
import com.tag.backend.model.UserModel;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.*;

@Entity
@Table(	name = "user",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "email"),
			@UniqueConstraint(columnNames = "phone")
		})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 120)
	@Email
	private String email;

	@NotBlank
	@Size(min = 10, max = 10)
	@Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
	private String phone;

	@NotBlank
	@Size(max =50)
	private String firstName;

	@NotBlank
	@Size(max =50)
	private String lastName;

	@NotBlank
	@Size(max =3)
	@Pattern(regexp = "^(A\\+|A\\-|B\\+|B\\-|O\\+|O\\-|AB\\+|AB\\-)$", message = "Invalid blood group")
	private String bloodGroup;

	public UserModel convertToModel(UserModel userModel)
	{
		userModel.setId(this.id);
		userModel.setEmail(this.email);
		userModel.setPhone(this.phone);
		userModel.setFirstName(this.firstName);
		userModel.setLastName(this.lastName);
		userModel.setBloodGroup(this.bloodGroup);
		return userModel;
	}
}