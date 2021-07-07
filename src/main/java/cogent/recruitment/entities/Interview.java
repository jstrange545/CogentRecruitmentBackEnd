package cogent.recruitment.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "interview")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Interview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NotBlank
    @Column(name = "end_time")
    private  LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;



    public Interview() {
    }

    public Interview(int id, String name,LocalDateTime startTime, LocalDateTime endTime, Application application) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.application = application;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", application=" + application +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview interview = (Interview) o;
        return id == interview.id &&
                Objects.equals(name, interview.name) &&
                Objects.equals(startTime, interview.startTime) &&
                Objects.equals(endTime, interview.endTime) &&
                Objects.equals(application, interview.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, endTime, application);
    }
}
