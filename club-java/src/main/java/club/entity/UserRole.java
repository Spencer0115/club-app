package club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="user_roles")
public class UserRole {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roles_id")
	private Long id;
	
	@Column(name = "roles_name")
	private String name;	
 
	UserRole() { 
	} 
 
	public UserRole(String name) { 
		this.name = name; 
	} 
 
	public String getName() { 
		return name; 
	} 
 
	public void setName(String name) { 
		this.name = name; 
	} 
}