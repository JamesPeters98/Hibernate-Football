package entities;

import javax.persistence.*;

@Entity
@Table(name = "FIXTURES", schema = "PUBLIC")
public class FixturesEntity {
    private int id;
    private int hometeamid;
    private int awayteamid;
    private int gameweek;
    private int leagueid;
    private LeaguesEntity league;
    private TeamsEntity awayteam;
    private TeamsEntity hometeam;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "HOMETEAMID")
    public int getHometeamid() {
        return hometeamid;
    }

    public void setHometeamid(int hometeamid) {
        this.hometeamid = hometeamid;
    }

    @Basic
    @Column(name = "AWAYTEAMID")
    public int getAwayteamid() {
        return awayteamid;
    }

    public void setAwayteamid(int awayteamid) {
        this.awayteamid = awayteamid;
    }

    @Basic
    @Column(name = "GAMEWEEK")
    public int getGameweek() {
        return gameweek;
    }

    public void setGameweek(int gameweek) {
        this.gameweek = gameweek;
    }

    @Basic
    @Column(name = "LEAGUEID")
    public int getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(int leagueid) {
        this.leagueid = leagueid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixturesEntity that = (FixturesEntity) o;

        if (id != that.id) return false;
        if (hometeamid != that.hometeamid) return false;
        if (awayteamid != that.awayteamid) return false;
        if (gameweek != that.gameweek) return false;
        if (leagueid != that.leagueid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + hometeamid;
        result = 31 * result + awayteamid;
        result = 31 * result + gameweek;
        result = 31 * result + leagueid;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "LEAGUEID", referencedColumnName = "ID", nullable = false)
    public LeaguesEntity getLeague() {
        return league;
    }

    public void setLeague(LeaguesEntity league) {
        this.league = league;
    }

    @ManyToOne
    @JoinColumn(name = "AWAYTEAMID", referencedColumnName = "ID", nullable = false)
    public TeamsEntity getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(TeamsEntity awayteam) {
        this.awayteam = awayteam;
    }

    @ManyToOne
    @JoinColumn(name = "HOMETEAMID", referencedColumnName = "ID", nullable = false)
    public TeamsEntity getHometeam() {
        return hometeam;
    }

    public void setHometeam(TeamsEntity hometeam) {
        this.hometeam = hometeam;
    }
}
