package cogent.recruitment.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "document")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name ="data",columnDefinition="LONGBLOB")
    private byte[] data;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "application_id")
    private  Application application;

    public Document() {
    }

    public Document(String name, byte[] data, Application application) {
        this.name = name;
        this.data = data;
        this.application = application;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Id == document.Id &&
                Objects.equals(name, document.name) &&
                Arrays.equals(data, document.data) &&
                Objects.equals(application, document.application);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(Id, name, application);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}

