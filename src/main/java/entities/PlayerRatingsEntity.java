package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PLAYER_RATINGS", schema = "PUBLIC")
@IdClass(PlayerRatingsEntityPK.class)
public class PlayerRatingsEntity {
    private int id;
    private int positionId;
    private Double rating;
    private Double attackrating;
    private Double defencerating;
    private PlayersEntity player;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "POSITION_ID")
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Basic
    @Column(name = "RATING")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRatingsEntity that = (PlayerRatingsEntity) o;
        return id == that.id &&
                positionId == that.positionId &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionId, rating);
    }

    @Basic
    @Column(name = "ATTACKRATING")
    public Double getAttackrating() {
        return attackrating;
    }

    public void setAttackrating(Double attackrating) {
        this.attackrating = attackrating;
    }

    @Basic
    @Column(name = "DEFENCERATING")
    public Double getDefencerating() {
        return defencerating;
    }

    public void setDefencerating(Double defencerating) {
        this.defencerating = defencerating;
    }

    @ManyToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public PlayersEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayersEntity player) {
        this.player = player;
    }
}
