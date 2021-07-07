package cogent.recruitment.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="APPLICATION_STATUS")
public class ApplicationStatus implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private Status stat;

	public ApplicationStatus(int id, Status stat) {
		this.id = id;
		this.stat = stat;
	}

	public ApplicationStatus() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStat() {
		return stat;
	}

	public void setStat(Status stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return "ApplicationStatus [id=" + id + ", stat=" + stat + "]";
	}
	
	
}
