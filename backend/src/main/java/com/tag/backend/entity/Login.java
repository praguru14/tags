package com.tag.backend.entity;

import com.tag.backend.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class Login implements UserDetails {

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
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 200)
    private String password;

    @NotBlank
    @Size(max = 3)
    @Pattern(regexp = "^(A\\+|A\\-|B\\+|B\\-|O\\+|O\\-|AB\\+|AB\\-)$", message = "Invalid blood group")
    private String bloodGroup;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;
        private String phone;
        private String firstName;
        private String lastName;
        private String password;
        private String bloodGroup;
        private Roles roles;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder bloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public Builder roles(Roles roles) {
            this.roles = roles;
            return this;
        }

        public Login build() {
            return new Login(id, email, phone, firstName, lastName, password, bloodGroup, roles);
        }
    }

    // Other methods (getters, setters, UserDetails methods) remain the same



@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
