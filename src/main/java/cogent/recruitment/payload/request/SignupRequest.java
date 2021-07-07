package cogent.recruitment.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cogent.recruitment.entities.Role;
 
public class SignupRequest {
	
	@NotBlank
	@Size(max = 45)
	private String firstName;
	
	@NotBlank
	@Size(max = 45)
	private String lastName;
	
	@NotBlank
	@Size(max = 100)
	@Email
	private String email;
	
	@NotBlank
	@Size(max = 255)
	private String password;
	
	@NotBlank
	@Size(max = 20)
	private String phone; 
	
	@Size(max = 100)
	private String linkedin;
    
    private Set<Role> roles;
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public Set<Role> getRoles() {
      return this.roles;
    }
    
    public void setRoles(Set<Role> roles) {
      this.roles = roles;
    }
}
