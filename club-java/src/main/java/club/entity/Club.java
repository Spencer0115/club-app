package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "user_club")
public class Club {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "club_id")
    private Long id;
	
	@Column(name = "club_name")
	private String name;
	
	@Column(name = "club_userId")
	private Long userId;
	
	@Column(name = "club_description")
	private String description;
	
	@Transient
	private String profile;
	
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
