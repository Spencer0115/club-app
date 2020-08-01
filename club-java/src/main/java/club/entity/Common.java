package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="pub_common")
public class Common {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "common_id")
	private Long id;
	
	@Column(name = "common_type")
	private String type;
	
	@Column(name = "common_description")
	private String description;
	
	@Column(name = "common_code")
	private String code;
	
	@Column(name = "common_name")
	private String name;

	@Transient
	private boolean inUse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	
}
