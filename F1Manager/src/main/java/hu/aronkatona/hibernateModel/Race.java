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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="RACE")
public class Race {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	
	@NotNull
	@Column(name="DATE",nullable = false, unique = true)
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="TRACK_ID",nullable = false)
	private Track track;
	
	@ManyToOne
	@JoinColumn(name="CHAMPIONSHIP_ID",nullable = false)
	private Championship championship;
	
	@Column(name="IS_RESULT_SET")
	private boolean resultSet;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	public boolean isResultSet() {
		return resultSet;
	}

	public void setResultSet(boolean resultSet) {
		this.resultSet = resultSet;
	}

	@Override
	public String toString() {
		return "Race [id=" + id + ", date=" + date + ", track=" + track
				+ ", championship=" + championship + ", resultSet=" + resultSet
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((championship == null) ? 0 : championship.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (resultSet ? 1231 : 1237);
		result = prime * result + ((track == null) ? 0 : track.hashCode());
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
		Race other = (Race) obj;
		if (championship == null) {
			if (other.championship != null)
				return false;
		} else if (!championship.equals(other.championship))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (resultSet != other.resultSet)
			return false;
		if (track == null) {
			if (other.track != null)
				return false;
		} else if (!track.equals(other.track))
			return false;
		return true;
	}

	
	

	
	
	
}
