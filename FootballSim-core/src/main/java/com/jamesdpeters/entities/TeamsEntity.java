package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TEAMS", schema = "PUBLIC")
public class TeamsEntity {
    private Integer id;
    private String name;
    private List<PlayersEntity> players;
    private Integer leagueid;
    private LeaguesEntity league;
    private List<FixturesEntity> AwayFixtures;
    private List<FixturesEntity> HomeFixtures;

    @OneToMany(mappedBy = "team")
    public List<PlayersEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersEntity> players) {
        this.players = players;
    }

    @Id
    @Basic
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

//    @OneToMany(mappedBy = "team")
//    public List<PlayersEntity> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(List<PlayersEntity> players) {
//        this.players = players;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamsEntity that = (TeamsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "LEAGUEID")
    public Integer getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(Integer leagueid) {
        this.leagueid = leagueid;
    }

    @ManyToOne
    @JoinColumn(name = "LEAGUEID", nullable = false, insertable = false, updatable = false)
    public LeaguesEntity getLeague() {
        return league;
    }

    public void setLeague(LeaguesEntity league) {
        this.league = league;
    }

    @OneToMany(mappedBy = "awayteam")
    public List<FixturesEntity> getAwayFixtures() {
        return AwayFixtures;
    }

    public void setAwayFixtures(List<FixturesEntity> awayFixtures) {
        AwayFixtures = awayFixtures;
    }

    @OneToMany(mappedBy = "hometeam")
    public List<FixturesEntity> getHomeFixtures() {
        return HomeFixtures;
    }

    public void setHomeFixtures(List<FixturesEntity> homeFixtures) {
        HomeFixtures = homeFixtures;
    }
}
