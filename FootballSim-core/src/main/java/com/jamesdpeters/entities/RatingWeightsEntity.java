package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RATING_WEIGHTS", schema = "PUBLIC")
public class RatingWeightsEntity {

    private int id;
    private Double attackAttacker;
    private Double attackFullback;
    private Double attackMidfielder;
    private Double attackCentreback;
    private Double attackGoalkeeper;
    private Double defenceAttacker;
    private Double defenceFullback;
    private Double defenceMidfielder;
    private Double defenceCentreback;
    private Double defenceGoalkeeper;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ATTACK_ATTACKER")
    public Double getAttackAttacker() {
        return attackAttacker;
    }

    public void setAttackAttacker(Double attackAttacker) {
        this.attackAttacker = attackAttacker;
    }

    @Basic
    @Column(name = "ATTACK_FULLBACK")
    public Double getAttackFullback() {
        return attackFullback;
    }

    public void setAttackFullback(Double attackFullback) {
        this.attackFullback = attackFullback;
    }

    @Basic
    @Column(name = "ATTACK_MIDFIELDER")
    public Double getAttackMidfielder() {
        return attackMidfielder;
    }

    public void setAttackMidfielder(Double attackMidfielder) {
        this.attackMidfielder = attackMidfielder;
    }

    @Basic
    @Column(name = "ATTACK_CENTREBACK")
    public Double getAttackCentreback() {
        return attackCentreback;
    }

    public void setAttackCentreback(Double attackCentreback) {
        this.attackCentreback = attackCentreback;
    }

    @Basic
    @Column(name = "ATTACK_GOALKEEPER")
    public Double getAttackGoalkeeper() {
        return attackGoalkeeper;
    }

    public void setAttackGoalkeeper(Double attackGoalkeeper) {
        this.attackGoalkeeper = attackGoalkeeper;
    }

    @Basic
    @Column(name = "DEFENCE_ATTACKER")
    public Double getDefenceAttacker() {
        return defenceAttacker;
    }

    public void setDefenceAttacker(Double defenceAttacker) {
        this.defenceAttacker = defenceAttacker;
    }

    @Basic
    @Column(name = "DEFENCE_FULLBACK")
    public Double getDefenceFullback() {
        return defenceFullback;
    }

    public void setDefenceFullback(Double defenceFullback) {
        this.defenceFullback = defenceFullback;
    }

    @Basic
    @Column(name = "DEFENCE_MIDFIELDER")
    public Double getDefenceMidfielder() {
        return defenceMidfielder;
    }

    public void setDefenceMidfielder(Double defenceMidfielder) {
        this.defenceMidfielder = defenceMidfielder;
    }

    @Basic
    @Column(name = "DEFENCE_CENTREBACK")
    public Double getDefenceCentreback() {
        return defenceCentreback;
    }

    public void setDefenceCentreback(Double defenceCentreback) {
        this.defenceCentreback = defenceCentreback;
    }

    @Basic
    @Column(name = "DEFENCE_GOALKEEPER")
    public Double getDefenceGoalkeeper() {
        return defenceGoalkeeper;
    }

    public void setDefenceGoalkeeper(Double defenceGoalkeeper) {
        this.defenceGoalkeeper = defenceGoalkeeper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingWeightsEntity that = (RatingWeightsEntity) o;
        return id == that.id &&
                Objects.equals(attackAttacker, that.attackAttacker) &&
                Objects.equals(attackFullback, that.attackFullback) &&
                Objects.equals(attackMidfielder, that.attackMidfielder) &&
                Objects.equals(attackCentreback, that.attackCentreback) &&
                Objects.equals(attackGoalkeeper, that.attackGoalkeeper) &&
                Objects.equals(defenceAttacker, that.defenceAttacker) &&
                Objects.equals(defenceFullback, that.defenceFullback) &&
                Objects.equals(defenceMidfielder, that.defenceMidfielder) &&
                Objects.equals(defenceCentreback, that.defenceCentreback) &&
                Objects.equals(defenceGoalkeeper, that.defenceGoalkeeper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attackAttacker, attackFullback, attackMidfielder, attackCentreback, attackGoalkeeper, defenceAttacker, defenceFullback, defenceMidfielder, defenceCentreback, defenceGoalkeeper);
    }
}
