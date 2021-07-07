package cogent.recruitment.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import cogent.recruitment.entities.User;

@CrossOrigin()
public interface UserDao extends JpaRepository<User, Integer>{
	
	Set<User> findByrolesId(int roleId);
	
	@Query(value = "from User a where a.firstName LIKE CONCAT('%',:text,'%') or a.lastName LIKE CONCAT('%',:text,'%') or CONCAT(a.firstName,' ',a.lastName) LIKE CONCAT('%',:text,'%')")
	Optional<Set<User>> findByname(@Param("text") String name);
	
	@Query("select a from User a where a.email = :email")
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
}
