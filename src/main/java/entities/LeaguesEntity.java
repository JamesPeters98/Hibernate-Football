package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LEAGUES", schema = "PUBLIC")
public class LeaguesEntity {
    private int id;
    private String leaguename;
    private Integer division;
    private NationalityEntity country;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LEAGUENAME")
    public String getLeaguename() {
        return leaguename;
    }

    public void setLeaguename(String leaguename) {
        this.leaguename = leaguename;
    }

    @Basic
    @Column(name = "DIVISION")
    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaguesEntity that = (LeaguesEntity) o;
        return id == that.id &&
                Objects.equals(leaguename, that.leaguename) &&
                Objects.equals(division, that.division);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leaguename, division);
    }

    @ManyToOne
    @JoinColumn(name = "COUNTRY", insertable = false, updatable = false)
    public NationalityEntity getCountry() {
        return country;
    }

    public void setCountry(NationalityEntity country) {
        this.country = country;
    }
}
