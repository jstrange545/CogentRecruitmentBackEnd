package cogent.recruitment.dao;

import cogent.recruitment.entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewDao extends JpaRepository<Interview,Integer> {

    List<Interview> findAllByApplicationUsersId(int id);
    
    List<Interview> findAllByApplicationUsersIdAndEndTimeBefore(int id, LocalDateTime now);

    List<Interview> findAllByApplicationUsersIdAndStartTimeAfter(int id, LocalDateTime now);
    
    List<Interview> findAllByApplicationIdAndEndTimeBefore(int id, LocalDateTime now);

    List<Interview> findAllByApplicationIdAndStartTimeAfter(int id, LocalDateTime now);

    //reason for using nativeQuery because localdatetime contain colon :, the hql will consider it is a name parameter and throw name parameter not bound error
    @Modifying
    @Query(value = "update interview set start_time=?, end_time=? where id=?", nativeQuery = true)
    int updateInterviewById(LocalDateTime newStart, LocalDateTime newEnd, int id);
}
