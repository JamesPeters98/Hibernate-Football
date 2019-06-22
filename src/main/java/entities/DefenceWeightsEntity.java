package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DEFENCE_WEIGHTS", schema = "PUBLIC", catalog = "SAVE")
public class DefenceWeightsEntity {
    private int id;
    private int positiontype;
    private Double weight;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "POSITIONTYPE")
    public int getPositiontype() {
        return positiontype;
    }

    public void setPositiontype(int positiontype) {
        this.positiontype = positiontype;
    }

    @Basic
    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefenceWeightsEntity that = (DefenceWeightsEntity) o;
        return id == that.id &&
                positiontype == that.positiontype &&
                Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positiontype, weight);
    }
}
