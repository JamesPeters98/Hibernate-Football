package com.jamesdpeters.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PlayerRatingsEntityPK implements Serializable {
    private int id;
    private int positionId;

    @Column(name = "ID")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "POSITION_ID")
    @Id
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRatingsEntityPK that = (PlayerRatingsEntityPK) o;
        return id == that.id &&
                positionId == that.positionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionId);
    }
}
