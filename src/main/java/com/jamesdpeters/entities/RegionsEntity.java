package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "REGIONS", schema = "PUBLIC")
public class RegionsEntity {
    private int id;
    private String name;
    private List<NationalityEntity> nationalities;

    @Id
    @Basic
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionsEntity that = (RegionsEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "region")
    public List<NationalityEntity> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<NationalityEntity> nationalities) {
        this.nationalities = nationalities;
    }
}
