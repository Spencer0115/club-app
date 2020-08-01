package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_eventDetail")
public class EventDetail {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "eventDetail_id")
	private Long id;
	
	@Column(name="eventDetail_eventId")
	private Long eventId;
	
	@Column(name="eventDetail_playerId")
	private Long playerId;

	@Column(name="eventDetail_clubId")
	private Long clubId;
	
	public Long getClubId() {
		return clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	@Column(name="eventDetail_teamId")
	private Long teamId;
	
	@Column(name="eventDetail_status")
	private String status;//0:sent 1: accepted 2:declined 3:expired 
	
	@Column(name="eventDetail_score")
	private Integer score;
	
	@Column(name="eventDetail_isFlag")
	private String isFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}
	
	
	
}
