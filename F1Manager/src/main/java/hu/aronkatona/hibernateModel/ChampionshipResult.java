package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CHAMPIONSHIP_RESULT")
public class ChampionshipResult {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="CHAMPIONSHIP_ID")
	private Championship championship;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="POINT")
	private long point;
	
	@Column(name="POSITION")
	private int position;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
