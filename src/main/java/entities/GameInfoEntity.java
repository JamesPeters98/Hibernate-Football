package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GAME_INFO", schema = "PUBLIC")
public class GameInfoEntity {

    private int gameId;
    private Integer currentSeason;
    private Integer currentGameWeek;
    private TeamsEntity team;
    private SeasonsEntity currentSeasonEntity;
    private List<Integer> leagues;
    @Column(columnDefinition = "default boolean false")
    private Boolean gameStarted;
    private Integer teamId;

    @OneToOne
    @JoinColumn(name = "TEAM", referencedColumnName = "ID", insertable = false, updatable = false)
    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }

    @Basic
    @Column(name = "GAME_STARTED")
    public Boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(Boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    @Basic
    @Column(name = "CURRENT_SEASON")
    public Integer getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Integer currentSeason) {
        this.currentSeason = currentSeason;
    }

    @Basic
    @Column(name = "TEAM")
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Basic
    @Column(name = "CURRENT_GAME_WEEK")
    public Integer getCurrentGameWeek() {
        return currentGameWeek;
    }

    public void setCurrentGameWeek(Integer currentGameWeek) {
        this.currentGameWeek = currentGameWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfoEntity that = (GameInfoEntity) o;
        return Objects.equals(gameStarted, that.gameStarted) &&
                Objects.equals(currentSeason, that.currentSeason) &&
                Objects.equals(currentGameWeek, that.currentGameWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameStarted, currentSeason, currentGameWeek);
    }

    @OneToOne
    @JoinColumn(name = "CURRENT_SEASON", referencedColumnName = "ID", insertable = false, updatable = false)
    public SeasonsEntity getCurrentSeasonEntity() {
        return currentSeasonEntity;
    }

    public void setCurrentSeasonEntity(SeasonsEntity currentSeasonEntity) {
        this.currentSeasonEntity = currentSeasonEntity;
    }

    @Id
    @Basic
    @Column(name = "GAME_ID")
    public int getId() {
        return gameId;
    }

    public void setId(int id) {
        this.gameId = id;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "LEAGUES")
    public List<Integer> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<Integer> leagues) {
        this.leagues = leagues;
    }

    @Override
    public String toString() {
        return "GameInfoEntity{" +
                "gameId=" + gameId +
                ", currentSeason=" + currentSeason +
                ", currentGameWeek=" + currentGameWeek +
                ", team=" + team +
                ", currentSeasonEntity=" + currentSeasonEntity +
                ", leagues=" + leagues +
                ", gameStarted=" + gameStarted +
                ", teamId=" + teamId +
                '}';
    }
}
