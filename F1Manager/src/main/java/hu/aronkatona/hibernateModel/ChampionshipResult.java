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
	@JoinColumn(name="CHAMPIONSHIP_ID",nullable = false)
	private Championship championship;
	
	@ManyToOne
	@JoinColumn(name="USER_ID",nullable = false)
	private User user;
	
	@Column(name="POINT",nullable = false)
	private long point;
	
	@Column(name="POSITION",nullable = false)
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

	@Override
	public String toString() {
		return "ChampionshipResult [id=" + id + ", championship="
				+ championship + ", user=" + user + ", point=" + point
				+ ", position=" + position + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((championship == null) ? 0 : championship.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (point ^ (point >>> 32));
		result = prime * result + position;
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
		ChampionshipResult other = (ChampionshipResult) obj;
		if (championship == null) {
			if (other.championship != null)
				return false;
		} else if (!championship.equals(other.championship))
			return false;
		if (id != other.id)
			return false;
		if (point != other.point)
			return false;
		if (position != other.position)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
	
	
	
	
	
	
}
