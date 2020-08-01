package club.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="pub_user")
public class User {
	public User() {};
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) 
	private List<UserRole> roles; 
 
	public User(String username, String password, List<UserRole> roles) { 
		this.username = username; 
		this.password = password; 
		this.roles = roles; 
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id")
	
	private Long id;
	@Column(name = "user_username")
	private String username;
	@Column(name = "user_password")
	private String password;
	@Column(name = "user_status")
	private String status;
	@Column(name = "user_type")
	private String type;
	@Column(name = "user_email")
	private String email;
	@Column(name = "user_createTime")
	private String createTime;
	@Column(name = "user_profile")
	private String profile;
	@Transient
	private String club_clubName;
	@Transient
	private Long player_teamId;
	@Transient
	private Long player_clubId;
	@Transient
	private String firstName;
	@Transient
	private String lastName;
	@Transient
	private String birthday;
	@Transient
	private String gender;
	@Transient
	private Long player_id;
	
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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<UserRole> getRoles() { 
		return roles; 
	} 
 
	public void setRoles(List<UserRole> roles) { 
		this.roles = roles; 
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
	public String getClub_clubName() {
		return club_clubName;
	}
	public void setClub_clubName(String club_clubName) {
		this.club_clubName = club_clubName;
	}
	public Long getPlayer_teamId() {
		return player_teamId;
	}
	public void setPlayer_teamId(Long player_teamId) {
		this.player_teamId = player_teamId;
	}
	public Long getPlayer_clubId() {
		return player_clubId;
	}
	public void setPlayer_clubId(Long player_clubId) {
		this.player_clubId = player_clubId;
	}

	public Long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(Long player_id) {
		this.player_id = player_id;
	}

	
}
