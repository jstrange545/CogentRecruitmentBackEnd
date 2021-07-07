package cogent.recruitment.service;

import java.util.List;

import cogent.recruitment.entities.ARole;
import cogent.recruitment.entities.Role;

public interface RoleService {
	
	public List<Role> getRoles();
	
	public Role getRoleById(int id);
	
	public Role getRoleByName(ARole name);

}
