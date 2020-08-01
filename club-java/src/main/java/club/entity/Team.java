package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_team")
public class Team {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "team_id")
    private Long id;
	
	@Column(name = "team_clubId")
	private Long clubId;
	
	@Column(name = "team_sport")
	private String sport;
	
	@Column(name = "team_name")
	private String name;

	@Column(name = "team_profile")
	private String profile;
	
	@Column(name = "team_time")
	private String time;
	
	
	@Transient
	private Integer totalScore;
	
	@Transient
	private String sportName;
	
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

	public Long getClubId() {
		return clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	} 
	
}
