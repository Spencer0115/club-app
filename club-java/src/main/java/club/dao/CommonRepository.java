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

import club.entity.Common;


@Repository
public interface CommonRepository extends CrudRepository<Common, Long>,PagingAndSortingRepository<Common, Long>, JpaSpecificationExecutor<Common> {

	public Common findByTypeAndCode(String type,String code);

	public List<Common> findByType(String type);
	
	public Page<Common> findByType(String type,Pageable page);
	
	@Query("select MAX(c.code) from Common c where c.type=:type ")
	public Integer findMaxCode(@Param("type") String type);

	public Common findByNameAndType(String name, String type);
}
