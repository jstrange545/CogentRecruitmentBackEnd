package cogent.recruitment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import cogent.recruitment.entities.ARole;
import cogent.recruitment.entities.Role;

@CrossOrigin()
public interface RoleDao extends JpaRepository<Role,Integer>{
	
	Optional<Role> findByName(ARole name);

}
