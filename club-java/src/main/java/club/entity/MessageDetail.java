package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_messageDetail")
public class MessageDetail {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "messageDetail_id")
	private Long id;
	
	@Column(name = "messageDetail_messageId")
	private Long messageId;
	
	@Column(name = "messageDetail_senderId")
	private Long senderId;
	
	@Column(name = "messageDetail_receiverId")
	private Long receiverId;
	
	@Column(name = "messageDetail_content")
	private String content;
	
	@Column(name = "messageDetail_time")
	private String time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
		
	
}
