package com.tag.backend.model;

import com.tag.backend.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel
{
	private long id;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private String bloodGroup;

	public User convertToEntity(User user)
	{
		user.setEmail(this.email);
		user.setPhone(this.phone);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setBloodGroup(this.bloodGroup);
		return user;
	}
}