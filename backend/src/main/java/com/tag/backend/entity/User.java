package com.tag.backend.entity;
import com.tag.backend.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private long phone;
	private String firstName;
	private String lastName;
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