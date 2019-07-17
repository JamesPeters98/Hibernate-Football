package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LEAGUE_TABLE", schema = "PUBLIC")
public class LeagueTableEntity {
    private int season;
    private Integer teamid;
    private Integer wins = 0;
    private Integer draws = 0;
    private Integer losses = 0;
    private Integer goalsScored = 0;
    private Integer goalsConceeded = 0;
    private Integer leagueId;
    private LeaguesEntity league;
    private int id;
    private TeamsEntity team;

    @Basic
    @Column(name = "SEASON")
    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Basic
    @Column(name = "TEAMID")
    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    @Basic
    @Column(name = "WINS", nullable = false)
    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Basic
    @Column(name = "DRAWS", nullable = false)
    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    @Basic
    @Column(name = "LOSSES", nullable = false)
    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    @Basic
    @Column(name = "GOALS_SCORED", nullable = false)
    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    @Basic
    @Column(name = "GOALS_CONCEEDED", nullable = false)
    public Integer getGoalsConceeded() {
        return goalsConceeded;
    }

    public void setGoalsConceeded(Integer goalsConceeded) {
        this.goalsConceeded = goalsConceeded;
    }

    @Basic
    @Column(name = "LEAGUE_ID")
    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueTableEntity that = (LeagueTableEntity) o;
        return season == that.season &&
                Objects.equals(teamid, that.teamid) &&
                Objects.equals(wins, that.wins) &&
                Objects.equals(draws, that.draws) &&
                Objects.equals(losses, that.losses) &&
                Objects.equals(goalsScored, that.goalsScored) &&
                Objects.equals(goalsConceeded, that.goalsConceeded) &&
                Objects.equals(leagueId, that.leagueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, teamid, wins, draws, losses, goalsScored, goalsConceeded, leagueId);
    }

    @OneToOne
    @JoinColumn(name = "LEAGUE_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    public LeaguesEntity getLeague() {
        return league;
    }

    public void setLeague(LeaguesEntity league) {
        this.league = league;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @OneToOne
    @JoinColumn(name = "TEAMID", referencedColumnName = "ID", updatable = false, insertable = false)
    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Transient
    public void addWin(){
        setWins(getWins()+1);
    }

    @Transient
    public void addDraw(){
        setDraws(getDraws()+1);
    }

    @Transient
    public void addLoss(){
        setLosses(getLosses()+1);
    }

    @Transient
    public void addGoalsScored(int goalsScored){
        setGoalsScored(getGoalsScored()+goalsScored);
    }

    @Transient
    public void addGoalsConceeded(int goalsConceeded){
        setGoalsConceeded(getGoalsConceeded()+goalsConceeded);
    }

    @Transient
    public int getPoints(){
        return 3*wins+draws;
    }

}
