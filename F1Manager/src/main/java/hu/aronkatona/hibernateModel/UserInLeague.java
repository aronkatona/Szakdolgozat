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
	
	
	
	
}
