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

import club.entity.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>,PagingAndSortingRepository<Team, Long>, JpaSpecificationExecutor<Team> {
	
    public List<Team> findByClubId(Long clubId);
	
    public Page<Team> findByClubId(Long clubId,Pageable pageable);
    
    @Query("select count(1) from Team t where upper(t.name) = upper( :name) and t.clubId= :id")
	public Integer countByNameAndClubId(@Param("name") String name, @Param("id")Long id);

    @Query("from Team t where t.name like %:name% ")
	public List<Team> findByName(@Param("name")String name);
	
	@Query("select sum(p.score) from Player p where p.teamId=:id")
	public Integer getTotalScore(@Param("id") Long id);

	public Integer countBySport(String code);

}
