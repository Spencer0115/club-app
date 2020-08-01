package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_event")
public class Event {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "event_id")
    private Long id;
	
	@Column(name = "event_clubId")
    private Long clubId;

	@Column(name = "event_addedTime")
	private String addedTime;
	
	@Column(name = "event_name")
	private String name;
	
	@Column(name = "event_place")
	private String place;
	
	@Column(name = "event_type")
	private String type;
	
	@Column(name = "event_description")
	private String description;
	
	@Column(name = "event_participant")
	private String participant;
	
	@Column(name = "event_participantId")
	private String participantId;
	
	@Column(name = "event_startTime")
	private String startTime;
	
	@Column(name = "event_endTime")
	private String endTime;

	@Column(name = "event_status")
	private String status;
	
	@Column(name = "event_isFlag")
	private String isFlag;
	
	@Transient
	private Integer score;
	
	@Transient
	private String player_status;
	
	
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getClubId() {
		return clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	public String getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPlayer_status() {
		return player_status;
	}

	public void setPlayer_status(String player_status) {
		this.player_status = player_status;
	}
	
	
	
	
	
}
