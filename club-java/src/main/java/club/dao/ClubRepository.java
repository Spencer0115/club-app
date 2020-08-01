package club.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import club.entity.Club;


@Repository
public interface ClubRepository extends CrudRepository<Club, Long>,PagingAndSortingRepository<Club, Long>, JpaSpecificationExecutor<Club>{
	
    public Club findByName(String clubName);
	
    @Query("from Club c order by c.name")
	public List<Club> findAllOrderByName();

	public Club findByUserId(Long id);
	


}
