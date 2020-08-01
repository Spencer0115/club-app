package club.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{
	
	/**
	 * get menus by userType
	 * @param userType
	 * @return
	 */
	@Query("from Menu m where m.userType = :userType")
	List<Menu> getMenuByUserType(@Param("userType") String userType);
}
