package club.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.EventDetail;

@Repository
public interface EventDetailRepository  extends CrudRepository<EventDetail, Long>,PagingAndSortingRepository<EventDetail, Long>, JpaSpecificationExecutor<EventDetail>{
	
	public EventDetail findByEventIdAndPlayerId(Long eventId, Long playerID);

	@Query("select sum(ed.score) from EventDetail ed where ed.eventId= :eventId and ed.teamId=:id")
	public Integer calculateScoreByTeam(@Param("eventId")Long eventId, @Param("id") Long id);

	@Query("select ed.score from EventDetail ed where ed.eventId= :eventId and ed.playerId=:id")
	public Integer calculateScoreByPlayer(@Param("eventId")Long eventId, @Param("id") Long id);	

	@Query("select sum(ed.score) from EventDetail ed where ed.playerId=:id")
	public Integer getScoreByPlayerId(@Param("id")Long id);
	
	@Query("select sum(ed.score) from EventDetail ed where ed.teamId=:id")
	public Integer getTotalScoreByTeam(@Param("id")Long id);
}
