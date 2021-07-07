package cogent.recruitment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cogent.recruitment.dao.RoleDao;
import cogent.recruitment.entities.ARole;
import cogent.recruitment.entities.Role;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleDao dao;

	@Override
	public List<Role> getRoles() {
		return dao.findAll();
	}

	@Override
	public Role getRoleById(int id) {
		return dao.findById(id).get();
	}

	@Override
	public Role getRoleByName(ARole name) {
		Role role = dao.findByName(name)
				.orElseThrow(() -> new RuntimeException("No such role exists."));
		return role;
	}
	
}
