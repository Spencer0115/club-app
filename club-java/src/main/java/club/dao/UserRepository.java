package club.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import club.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>,PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query("from User u where u.username = :username")
    public User getUserTypeByUserName(@Param("username") String username);
	
	@Query("select count(1) from User u where upper(u.email) = upper(:email)")
	public Integer getUserCountByEmail(@Param("email") String email);

	@Query("from User u where u.username = :username")
	public User findByUsername(@Param("username")String username);

	@Modifying
	@Query("UPDATE User u SET u.profile = :profile where u.id = :id")
	public void updateProfile(@Param("profile")String profile, @Param("id") Long id); 

}
