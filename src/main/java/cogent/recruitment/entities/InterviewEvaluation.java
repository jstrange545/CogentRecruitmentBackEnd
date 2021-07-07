package cogent.recruitment.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

//import lombok.Data;

//@Data
@Entity
@Table(name = "interview_evaluation")
public class InterviewEvaluation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank
	@Column(name = "notes")
	private String notes;
	
	@NotBlank
	@Column(name = "pass")
	private boolean pass;
	
	@ManyToOne
	@JoinColumn(name="interview_id", referencedColumnName="id")
	private Interview interview;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	public InterviewEvaluation() {
		
	}
	public InterviewEvaluation(int id, String notes, boolean pass, Interview interview, User user) {
		this.id = id;
		this.notes = notes;
		this.pass = pass;
		this.interview = interview;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public void setIntEval_id(int id) {
		this.id = id;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public Interview getInterview() {
		return interview;
	}
	public void setInterview(Interview interview) {
		this.interview = interview;
	}
	public User getAdmin() {
		return user;
	}
	public void setAdmin(User user) {
		this.user = user;
	}

}
