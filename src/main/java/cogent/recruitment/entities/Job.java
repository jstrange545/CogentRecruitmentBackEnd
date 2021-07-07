package cogent.recruitment.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
@Table(name="JOB")
public class Job implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@NotBlank
	@Size(max = 10)
	@Column(name="BATCH_NO")
	private String batch;
	
	@NotBlank
	@Size(max = 100)
	@Column(name="NAME")
	private String name;
	
	@Size(max = 1000)
	@Column(name="DESCRIPTION")
	private String description;
	
	@CreatedDate
	@Column(name="DATE_PUBLISHED")
	private LocalDate datepub;
	
	@NotBlank
	@Column(name="JOB_START_DATE")
	private LocalDate datestart;
	
	@NotBlank
	@Column(name="NO_OF_VACANCIES")
	private int vacancies;
	
	@JsonBackReference
	@OneToMany(mappedBy = "job")
	private Set<Application> applications;
	
	public Job() {}
	
	public Job(String batch, String name, String description, LocalDate datepub, LocalDate datestart, int vacancies, Set<Application> applications) {
		this.batch = batch;
		this.name = name;
		this.description = description;
		this.datepub = datepub;
		this.datestart = datestart;
		this.vacancies = vacancies;
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", batch=" + batch + ", name=" + name + ", description=" + description + ", datepub="
				+ datepub + ", datestart=" + datestart + ", vacancies=" + vacancies + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Job other = (Job) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDatepub() {
		return datepub;
	}

	public void setDatepub(LocalDate datepub) {
		this.datepub = datepub;
	}

	public LocalDate getDatestart() {
		return datestart;
	}

	public void setDatestart(LocalDate datestart) {
		this.datestart = datestart;
	}

	public int getVacancies() {
		return vacancies;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

}
