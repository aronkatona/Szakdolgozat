package hu.aronkatona.hibernateModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER_IN_LEAGUE")
public class UserInLeague {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="LEAGUE_ID",nullable = false)
	private League league;
	
	@ManyToOne
	@JoinColumn(name="USER_ID",nullable = false)
	private User user;
	
	@Column(name="ROLE")
	private boolean role;
	
	@Column(name="COMMENTS_RIGHT")
	private boolean commentsRight;
	
	@Column(name="JOIN_DATE",nullable = false)
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date joinDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public boolean isCommentsRight() {
		return commentsRight;
	}

	public void setCommentsRight(boolean commentsRight) {
		this.commentsRight = commentsRight;
	}

	@Override
	public String toString() {
		return "UserInLeague [id=" + id + ", league=" + league + ", user="
				+ user + ", role=" + role + ", commentsRight=" + commentsRight
				+ ", joinDate=" + joinDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (commentsRight ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((joinDate == null) ? 0 : joinDate.hashCode());
		result = prime * result + ((league == null) ? 0 : league.hashCode());
		result = prime * result + (role ? 1231 : 1237);
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInLeague other = (UserInLeague) obj;
		if (commentsRight != other.commentsRight)
			return false;
		if (id != other.id)
			return false;
		if (joinDate == null) {
			if (other.joinDate != null)
				return false;
		} else if (!joinDate.equals(other.joinDate))
			return false;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
		if (role != other.role)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
	
	
}
