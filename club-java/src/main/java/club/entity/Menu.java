package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pub_menu")
public class Menu {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "menu_id")
    private Long id;
	
	@Column(name = "menu_name")
	private String name;
	
	@Column(name = "menu_url")
	private String url;
	
	@Column(name = "menu_order")
	private int order;
	
	@Column(name = "menu_userType")
	private String userType;

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
