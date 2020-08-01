package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_player")
public class Player {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "player_id")
    private Long id;
	
	@Column(name = "player_userId")
	private Long userId;
	
	@Column(name = "player_clubId")
	private Long clubId;
	
	@Column(name = "player_teamId")
	private Long teamId;
	
	@Column(name = "player_firstName")
	private String firstName;
	
	@Column(name = "player_LastName")
	private String lastName;
	
	@Column(name = "player_birthday")
	private String birthday;
	
	@Column(name = "player_gender")
	private String gender;
	
	@Column(name = "player_score")
	private int score;
	
	@Column(name = "player_profile")
	private String profile;
	
	@Transient
	private String clubName;
	
	@Transient
	private String teamName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getClubId() {
		return clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
