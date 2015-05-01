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
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER")
public class User implements Comparable<User>{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="NAME",					length = 100,   nullable = false,  unique=true)
	@NotEmpty
	private String name;
	
	@Column(name="EMAIL",					length = 100,  nullable = false,  unique=true)
	@Pattern(regexp=".+@.+\\..+", message="Az e-mail nem megfelel\u0151")
	private String email;
	
	@Column(name="PASSWORD",				length = 255,  nullable = false)
	@NotEmpty
	@Size(min=6,max=20)
	private String password;
	
	@Transient
	@NotEmpty
	@Size(min=6,max=20)
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
	
	@Column(name="ACTUAL_POSITION")
	private long actualPosition;
	
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

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}
	
	
	
	public long getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(long actualPosition) {
		this.actualPosition = actualPosition;
	}

	@AssertTrue(message = "A jelszavak nem egyeznek")
	public boolean isPasswordSame(){
		if(password != null  && passwordAgain != null) return password.equals(passwordAgain);
		else return true;
	}
	
	@Override
	public int compareTo(User other) {
		return (int) other.getActualPoint() - (int) this.actualPoint;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", passwordAgain=" + passwordAgain
				+ ", registrationDate=" + registrationDate
				+ ", activationCode=" + activationCode
				+ ", changePasswordToken=" + changePasswordToken
				+ ", actualMoney=" + actualMoney + ", actualPoint="
				+ actualPoint + ", actualDriver1=" + actualDriver1
				+ ", actualDriver2=" + actualDriver2 + ", actualTeam1="
				+ actualTeam1 + ", actualTeam2=" + actualTeam2
				+ ", actualTeam3=" + actualTeam3 + ", actualPosition="
				+ actualPosition + ", activated=" + activated + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result
				+ ((activationCode == null) ? 0 : activationCode.hashCode());
		result = prime * result
				+ ((actualDriver1 == null) ? 0 : actualDriver1.hashCode());
		result = prime * result
				+ ((actualDriver2 == null) ? 0 : actualDriver2.hashCode());
		result = prime * result + (int) (actualMoney ^ (actualMoney >>> 32));
		result = prime * result + (int) (actualPoint ^ (actualPoint >>> 32));
		result = prime * result
				+ (int) (actualPosition ^ (actualPosition >>> 32));
		result = prime * result
				+ ((actualTeam1 == null) ? 0 : actualTeam1.hashCode());
		result = prime * result
				+ ((actualTeam2 == null) ? 0 : actualTeam2.hashCode());
		result = prime * result
				+ ((actualTeam3 == null) ? 0 : actualTeam3.hashCode());
		result = prime
				* result
				+ ((changePasswordToken == null) ? 0 : changePasswordToken
						.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((passwordAgain == null) ? 0 : passwordAgain.hashCode());
		result = prime
				* result
				+ ((registrationDate == null) ? 0 : registrationDate.hashCode());
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
		User other = (User) obj;
		if (activated != other.activated)
			return false;
		if (activationCode == null) {
			if (other.activationCode != null)
				return false;
		} else if (!activationCode.equals(other.activationCode))
			return false;
		if (actualDriver1 == null) {
			if (other.actualDriver1 != null)
				return false;
		} else if (!actualDriver1.equals(other.actualDriver1))
			return false;
		if (actualDriver2 == null) {
			if (other.actualDriver2 != null)
				return false;
		} else if (!actualDriver2.equals(other.actualDriver2))
			return false;
		if (actualMoney != other.actualMoney)
			return false;
		if (actualPoint != other.actualPoint)
			return false;
		if (actualPosition != other.actualPosition)
			return false;
		if (actualTeam1 == null) {
			if (other.actualTeam1 != null)
				return false;
		} else if (!actualTeam1.equals(other.actualTeam1))
			return false;
		if (actualTeam2 == null) {
			if (other.actualTeam2 != null)
				return false;
		} else if (!actualTeam2.equals(other.actualTeam2))
			return false;
		if (actualTeam3 == null) {
			if (other.actualTeam3 != null)
				return false;
		} else if (!actualTeam3.equals(other.actualTeam3))
			return false;
		if (changePasswordToken == null) {
			if (other.changePasswordToken != null)
				return false;
		} else if (!changePasswordToken.equals(other.changePasswordToken))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordAgain == null) {
			if (other.passwordAgain != null)
				return false;
		} else if (!passwordAgain.equals(other.passwordAgain))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		return true;
	}
	
	
	
}
