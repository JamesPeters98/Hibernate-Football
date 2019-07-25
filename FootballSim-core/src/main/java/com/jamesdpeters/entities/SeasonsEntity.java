package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "SEASONS", schema = "PUBLIC")
public class SeasonsEntity {
    private int id;

    @Column(columnDefinition = "default boolean false")
    private boolean generated;

    @Column(columnDefinition = "default boolean false")
    private boolean ongoing;

    private List<FixturesEntity> fixtures;
    private Map<Object, FixturesEntity> fixturesByLeague;

    public SeasonsEntity() {
    }

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "GENERATED")
    public Boolean getGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    @Basic
    @Column(name = "ONGOING")
    public boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonsEntity that = (SeasonsEntity) o;
        return id == that.id &&
                Objects.equals(generated, that.generated) &&
                Objects.equals(ongoing, that.ongoing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, generated, ongoing);
    }

    @OneToMany(mappedBy = "season")
    public List<FixturesEntity> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<FixturesEntity> fixtures) {
        this.fixtures = fixtures;
    }

    @MapKey(name = "leagueid")
    @OneToMany(mappedBy = "season")
    public Map<Object, FixturesEntity> getFixturesByLeague() {
        return fixturesByLeague;
    }

    public void setFixturesByLeague(Map<Object, FixturesEntity> fixturesByLeague) {
        this.fixturesByLeague = fixturesByLeague;
    }



}
