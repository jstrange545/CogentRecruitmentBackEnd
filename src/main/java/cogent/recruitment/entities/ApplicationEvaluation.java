package cogent.recruitment.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="APPLICATION_EVALUATION")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer"})
public class ApplicationEvaluation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="NOTES")
	private String notes;

	//need object of type adminin for join column ADMIN_ID
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private User adm;


	//need object of type application for join column APPLICATION_ID

	@ManyToOne
	@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID")
	private Application app;

	@ManyToOne
	@JoinColumn(name="APPLICATION_STATUS_ID", referencedColumnName="ID")
	private ApplicationStatus appStatus;

	public ApplicationEvaluation() {
		// TODO Auto-generated constructor stub
	}

	public ApplicationEvaluation(int id, String notes, ApplicationStatus appStatus) {
		super();
		this.id = id;
		this.notes = notes;
		this.appStatus = appStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ApplicationStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(ApplicationStatus appStatus) {
		this.appStatus = appStatus;
	}

	public User getAdm() {
		return adm;
	}

	public void setAdm(User adm) {
		this.adm = adm;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return "ApplicationEvaluation [id=" + id + ", notes=" + notes + ", admin=" + adm + ", app=" + app
				+ ", appStatus=" + appStatus + "]";
	}

}