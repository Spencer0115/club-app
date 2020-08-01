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

import club.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>,PagingAndSortingRepository<Event, Long>, JpaSpecificationExecutor<Event> {
	
    public Page<Event> findByClubIdAndType(Long clubId,String type,Pageable pageable);
    
    public Page<Event> findByClubId(Long clubId,Pageable pageable);
    
    @Query("from Event e where e.name like %:name% ")
	public List<Event> findByNameLike(@Param("name")String name);

	/**
	 * 0：games 1:team training
	 * @param id
	 * @return
	 */
	@Query("from Event e where e.type in ('0','1') and e.participantId like %:id% and e.status != -1 and e.addedTime>=:time")
	public List<Event> getEventListByTeamId(@Param("id") String id,@Param("time") String time);

	@Query("from Event e where ((e.type in('0','1') and e.participantId like %:teamId%) or (e.type = '2' and e.participantId like %:playerId%)) and e.status != -1 and e.addedTime>=:time")
	public List<Event> getEventListByPlayerId(@Param("playerId")String playerId,@Param("teamId")String teamId,@Param("time") String time);

	@Query("from Event e where e.type = :type and (e.participantId like %:teamId% or e.participantId like %:playerId%) and e.status != -1 and e.addedTime>=:time")
	public Page<Event> findByPlayerIdAndType(@Param("playerId")String playerId, @Param("teamId")String teamId, @Param("type")String type, Pageable pageable,@Param("time") String time);
	
	@Query("from Event e where ((e.type in('0','1') and e.participantId like %:teamId%) or (e.type = '2' and e.participantId like %:playerId%)) and e.status != -1 and e.addedTime>=:time")
	public Page<Event> findByPlayerId(@Param("playerId")String playerId, @Param("teamId")String teamId, Pageable pageable,@Param("time") String time);

	public Integer countByType(String code);	

	
}
