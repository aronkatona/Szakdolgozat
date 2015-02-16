package hu.aronkatona.hibernateModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CHAMPIONSHIP")
public class Championship {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="START_DATE")
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date startDate;


	@Column(name="END_DATE")
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date endDate;
	
	@OneToMany(mappedBy="championship",fetch = FetchType.EAGER)
	private Set<Race> races = new HashSet<>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}


	

	
	
	
}
