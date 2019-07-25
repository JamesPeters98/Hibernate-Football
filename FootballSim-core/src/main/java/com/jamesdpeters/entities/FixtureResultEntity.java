package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FIXTURE_RESULT", schema = "PUBLIC")
public class FixtureResultEntity {
    private int fixtureId;
    private Integer homeGoals;
    private Integer awayGoals;
    private FixturesEntity fixture;

    @Id
    @Column(name = "FIXTURE_ID")
    public int getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(int fixtureId) {
        this.fixtureId = fixtureId;
    }

    @Basic
    @Column(name = "HOME_GOALS")
    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    @Basic
    @Column(name = "AWAY_GOALS")
    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixtureResultEntity that = (FixtureResultEntity) o;
        return fixtureId == that.fixtureId &&
                Objects.equals(homeGoals, that.homeGoals) &&
                Objects.equals(awayGoals, that.awayGoals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fixtureId, homeGoals, awayGoals);
    }

    @OneToOne
    @JoinColumn(name = "FIXTURE_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    public FixturesEntity getFixture() {
        return fixture;
    }

    public void setFixture(FixturesEntity fixture) {
        this.fixture = fixture;
    }
}
