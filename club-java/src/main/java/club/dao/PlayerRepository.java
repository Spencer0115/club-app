package club.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>,PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<Player> {

	public Player findByUserId(@Param("id") Long id);
	
	public Optional<Player> findById(Long id);

	public List<Player> findByClubId(@Param("id") Long id);

	public List<Player> findByTeamId(Long teamId);


}
