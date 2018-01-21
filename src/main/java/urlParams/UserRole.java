package urlParams;

import com.boly.entity.Role;

public class UserRole {
	private Long idUser;
	private Role role;
	public UserRole(Long id, Role role) {
		this.idUser = id;
		this.role = role;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
