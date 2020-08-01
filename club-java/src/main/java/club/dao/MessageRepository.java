package club.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>,PagingAndSortingRepository<Message, Long>, JpaSpecificationExecutor<Message>{

	@Query("from Message m where "
			+ "(m.receiver = :teamId and m.type='1') or "
			+ "(m.receiver = :userId and m.type='2') or "
			+ "m.type = '0' order by m.time desc" )
	List<Message> getMessageListForPlayer(@Param("userId")Long userId, @Param("teamId")Long teamId);
	
	@Query("from Message m where m.type='3' order by m.time")
	List<Message> getLatestNews();

	@Query("from Message m where m.type='3'")
	Page<Message> getLatestNews_P(Pageable pageable);
}
