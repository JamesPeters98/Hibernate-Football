package com.jamesdpeters.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "FORMATIONS", schema = "PUBLIC")
public class FormationsEntity {
    private Integer id;
    private String formation;
    private Integer pos1;
    private Integer pos2;
    private Integer pos3;
    private Integer pos4;
    private Integer pos5;
    private Integer pos6;
    private Integer pos7;
    private Integer pos8;
    private Integer pos9;
    private Integer pos10;

    @Transient
    public List<Integer> getPositions(){
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(pos10);
        pos.add(pos9);
        pos.add(pos8);
        pos.add(pos7);
        pos.add(pos6);
        pos.add(pos5);
        pos.add(pos4);
        pos.add(pos3);
        pos.add(pos2);
        pos.add(pos1);
        return pos;
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
    @Column(name = "FORMATION")
    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    @Basic
    @Column(name = "POS1")
    public Integer getPos1() {
        return pos1;
    }

    public void setPos1(Integer pos1) {
        this.pos1 = pos1;
    }

    @Basic
    @Column(name = "POS2")
    public Integer getPos2() {
        return pos2;
    }

    public void setPos2(Integer pos2) {
        this.pos2 = pos2;
    }

    @Basic
    @Column(name = "POS3")
    public Integer getPos3() {
        return pos3;
    }

    public void setPos3(Integer pos3) {
        this.pos3 = pos3;
    }

    @Basic
    @Column(name = "POS4")
    public Integer getPos4() {
        return pos4;
    }

    public void setPos4(Integer pos4) {
        this.pos4 = pos4;
    }

    @Basic
    @Column(name = "POS5")
    public Integer getPos5() {
        return pos5;
    }

    public void setPos5(Integer pos5) {
        this.pos5 = pos5;
    }

    @Basic
    @Column(name = "POS6")
    public Integer getPos6() {
        return pos6;
    }

    public void setPos6(Integer pos6) {
        this.pos6 = pos6;
    }

    @Basic
    @Column(name = "POS7")
    public Integer getPos7() {
        return pos7;
    }

    public void setPos7(Integer pos7) {
        this.pos7 = pos7;
    }

    @Basic
    @Column(name = "POS8")
    public Integer getPos8() {
        return pos8;
    }

    public void setPos8(Integer pos8) {
        this.pos8 = pos8;
    }

    @Basic
    @Column(name = "POS9")
    public Integer getPos9() {
        return pos9;
    }

    public void setPos9(Integer pos9) {
        this.pos9 = pos9;
    }

    @Basic
    @Column(name = "POS10")
    public Integer getPos10() {
        return pos10;
    }

    public void setPos10(Integer pos10) {
        this.pos10 = pos10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormationsEntity that = (FormationsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(formation, that.formation) &&
                Objects.equals(pos1, that.pos1) &&
                Objects.equals(pos2, that.pos2) &&
                Objects.equals(pos3, that.pos3) &&
                Objects.equals(pos4, that.pos4) &&
                Objects.equals(pos5, that.pos5) &&
                Objects.equals(pos6, that.pos6) &&
                Objects.equals(pos7, that.pos7) &&
                Objects.equals(pos8, that.pos8) &&
                Objects.equals(pos9, that.pos9) &&
                Objects.equals(pos10, that.pos10);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, formation, pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9, pos10);
    }
}
