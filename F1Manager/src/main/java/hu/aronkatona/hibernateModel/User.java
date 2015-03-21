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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="NAME",					length = 100,   nullable = false,  unique=true)
	@NotEmpty
	private String name;
	
	@Column(name="EMAIL",					length = 100,  nullable = false,  unique=true)
	@Pattern(regexp=".+@.+\\..+", message="Az e-mail nem megfelelő")
	private String email;
	
	@Column(name="PASSWORD",				length = 255,  nullable = false)
	@NotEmpty
	@Size(min=6)
	private String password;
	
	@Transient
	@NotEmpty
	@Size(min=6)
	private String passwordAgain;
	
	@Column(name="REGISTRATION_DATE")
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date registrationDate;
	
	@Column(name="ACTIVATION_CODE",length = 255)
	private String activationCode;
	
	@Column(name="CHANGE_PASSWORD_TOKEN",length = 255)
	private String changePasswordToken;
	
	@Column(name="ACTUAL_MONEY")
	private long actualMoney;
	
	@Column(name="ACTUAL_POINT")
	private long actualPoint;
	
	@ManyToOne
	@JoinColumn(name="ACTUAL_DRIVER1_ID")
	private Driver actualDriver1;
	
	@ManyToOne
	@JoinColumn(name="ACTUAL_DRIVER2_ID")
	private Driver actualDriver2;
	
	@ManyToOne
	@JoinColumn(name="ACTUAL_TEAM1_ID")
	private Team actualTeam1;
	
	@ManyToOne
	@JoinColumn(name="ACTUAL_TEAM2_ID")
	private Team actualTeam2;
	
	@ManyToOne
	@JoinColumn(name="ACTUAL_TEAM3_ID")
	private Team actualTeam3;
	
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> resultHistorys = new HashSet<>();
	
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<ChampionshipResult> championShipResults = new HashSet<>();
	
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserInLeague> userInLeague = new HashSet<>();
	
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<LeagueComment> leagueComments = new HashSet<>();
	
	@OneToMany(mappedBy="creator",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<League> leagues = new HashSet<>();

	private boolean activated;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getChangePasswordToken() {
		return changePasswordToken;
	}

	public void setChangePasswordToken(String changePasswordToken) {
		this.changePasswordToken = changePasswordToken;
	}

	public long getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(long actualMoney) {
		this.actualMoney = actualMoney;
	}

	public long getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(long actualPoint) {
		this.actualPoint = actualPoint;
	}

	public Driver getActualDriver1() {
		return actualDriver1;
	}

	public void setActualDriver1(Driver actualDriver1) {
		this.actualDriver1 = actualDriver1;
	}

	public Driver getActualDriver2() {
		return actualDriver2;
	}

	public void setActualDriver2(Driver actualDriver2) {
		this.actualDriver2 = actualDriver2;
	}

	public Team getActualTeam1() {
		return actualTeam1;
	}

	public void setActualTeam1(Team actualTeam1) {
		this.actualTeam1 = actualTeam1;
	}

	public Team getActualTeam2() {
		return actualTeam2;
	}

	public void setActualTeam2(Team actualTeam2) {
		this.actualTeam2 = actualTeam2;
	}

	public Team getActualTeam3() {
		return actualTeam3;
	}

	public void setActualTeam3(Team actualTeam3) {
		this.actualTeam3 = actualTeam3;
	}

	public Set<UserResultHistory> getResultHistorys() {
		return resultHistorys;
	}

	public void setResultHistorys(Set<UserResultHistory> resultHistorys) {
		this.resultHistorys = resultHistorys;
	}

	public Set<ChampionshipResult> getChampionShipResults() {
		return championShipResults;
	}

	public void setChampionShipResults(Set<ChampionshipResult> championShipResults) {
		this.championShipResults = championShipResults;
	}

	public Set<UserInLeague> getUserInLeague() {
		return userInLeague;
	}

	public void setUserInLeague(Set<UserInLeague> userInLeague) {
		this.userInLeague = userInLeague;
	}

	public Set<LeagueComment> getLeagueComments() {
		return leagueComments;
	}

	public void setLeagueComments(Set<LeagueComment> leagueComments) {
		this.leagueComments = leagueComments;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public void increaseActualMoney(long money){
		this.actualMoney+=money;
	}
	
	public void decreaseActualMoney(long money){
		this.actualMoney-=money;
	}
	
	public void increaseActualPoint(long point){
		this.actualPoint+=point;
	}

	public Set<League> getLeagues() {
		return leagues;
	}

	public void setLeagues(Set<League> leagues) {
		this.leagues = leagues;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}
	
	@AssertTrue(message = "A jelszavak nem egyeznek")
	public boolean isPasswordSame(){
		if(password != null  && passwordAgain != null) return password.equals(passwordAgain);
		else return true;
	}
	
}
