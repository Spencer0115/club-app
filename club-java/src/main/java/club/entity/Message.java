package club.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user_message")
public class Message {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "message_id")
    private Long id;

	@Column(name = "message_type")
	private String type;

	@Column(name = "message_sender")
	private Long sender;

	@Transient
	private String senderName;
	
	@Column(name = "message_receiver")
	private Long receiver;
	
	@Column(name = "message_time")
	private String time;
	
	@Column(name = "message_content")
	private String content;
	
	@Column(name = "message_status")
	private String status;
	
	@Column(name = "message_title")
	private String title;
	
	@Column(name = "message_readBy")
	private String readBy;
	
	@Column(name = "message_src")
	private String src;
	
	@Transient
	private List<MessageDetail> messageDetail;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReadBy() {
		return readBy;
	}

	public void setReadBy(String readBy) {
		this.readBy = readBy;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<MessageDetail> getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(List<MessageDetail> messageDetail) {
		this.messageDetail = messageDetail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
}
