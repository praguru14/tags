package com.tag.backend.entity;

import com.tag.backend.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
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

    private String otp;

    private LocalDateTime otpGeneratedTime;

    private boolean isVerified;

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Login(Long id, String email, String phone, String firstName, String lastName, String password, String bloodGroup, Roles roles, String otp, LocalDateTime otpGeneratedTime, boolean isVerified) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.roles = roles;
        this.otp = otp;
        this.otpGeneratedTime = otpGeneratedTime;
        this.isVerified = isVerified;
    }

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
        private String otp;
        private LocalDateTime otpGeneratedTime;
        private boolean isVerified;


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


        public Builder otp(String otp) {
            this.otp = otp;
            return this;
        }

        public Builder otpGeneratedTime(LocalDateTime otpGeneratedTime) {
            this.otpGeneratedTime = otpGeneratedTime;
            return this;
        }


        public Builder isVerified(boolean isVerified) {
            this.isVerified = isVerified;
            return this;
        }

        public Login build() {
            return new Login(id, email, phone, firstName, lastName, password, bloodGroup, roles, otp, otpGeneratedTime,isVerified);
        }
    }

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
