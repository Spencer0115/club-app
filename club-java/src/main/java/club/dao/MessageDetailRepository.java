package club.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.MessageDetail;

@Repository
public interface MessageDetailRepository extends CrudRepository<MessageDetail, Long>,PagingAndSortingRepository<MessageDetail, Long>, JpaSpecificationExecutor<MessageDetail>{
	
	@Query("from MessageDetail md where md.messageId = :messageId order by md.time desc")
	public List<MessageDetail> findByMessageId(@Param("messageId") Long messageId);
}
