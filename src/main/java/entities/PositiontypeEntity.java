package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "POSITIONTYPE", schema = "PUBLIC", catalog = "SAVE")
public class PositiontypeEntity {
    private int id;
    private String position;
    private Double crossing;
    private Double finishing;
    private Double headingAccuracy;
    private Double shortPassing;
    private Double volleys;
    private Double dribbling;
    private Double curve;
    private Double fkAccuracy;
    private Double longPassing;
    private Double ballControl;
    private Double acceleration;
    private Double sprintSpeed;
    private Double agility;
    private Double reactions;
    private Double balance;
    private Double shotPower;
    private Double jumping;
    private Double stamina;
    private Double strength;
    private Double longShots;
    private Double aggression;
    private Double interceptions;
    private Double posistioning;
    private Double vision;
    private Double penalties;
    private Double composure;
    private Double marking;
    private Double standingTackle;
    private Double slidingTackle;
    private Double gkDiving;
    private Double gkHandling;
    private Double gkKicking;
    private Double gkPositioning;
    private Double gkReflexes;
    private List<PositionsEntity> PositionType;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "POSITION")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "CROSSING")
    public Double getCrossing() {
        return crossing;
    }

    public void setCrossing(Double crossing) {
        this.crossing = crossing;
    }

    @Basic
    @Column(name = "FINISHING")
    public Double getFinishing() {
        return finishing;
    }

    public void setFinishing(Double finishing) {
        this.finishing = finishing;
    }

    @Basic
    @Column(name = "HEADING_ACCURACY")
    public Double getHeadingAccuracy() {
        return headingAccuracy;
    }

    public void setHeadingAccuracy(Double headingAccuracy) {
        this.headingAccuracy = headingAccuracy;
    }

    @Basic
    @Column(name = "SHORT_PASSING")
    public Double getShortPassing() {
        return shortPassing;
    }

    public void setShortPassing(Double shortPassing) {
        this.shortPassing = shortPassing;
    }

    @Basic
    @Column(name = "VOLLEYS")
    public Double getVolleys() {
        return volleys;
    }

    public void setVolleys(Double volleys) {
        this.volleys = volleys;
    }

    @Basic
    @Column(name = "DRIBBLING")
    public Double getDribbling() {
        return dribbling;
    }

    public void setDribbling(Double dribbling) {
        this.dribbling = dribbling;
    }

    @Basic
    @Column(name = "CURVE")
    public Double getCurve() {
        return curve;
    }

    public void setCurve(Double curve) {
        this.curve = curve;
    }

    @Basic
    @Column(name = "FK_ACCURACY")
    public Double getFkAccuracy() {
        return fkAccuracy;
    }

    public void setFkAccuracy(Double fkAccuracy) {
        this.fkAccuracy = fkAccuracy;
    }

    @Basic
    @Column(name = "LONG_PASSING")
    public Double getLongPassing() {
        return longPassing;
    }

    public void setLongPassing(Double longPassing) {
        this.longPassing = longPassing;
    }

    @Basic
    @Column(name = "BALL_CONTROL")
    public Double getBallControl() {
        return ballControl;
    }

    public void setBallControl(Double ballControl) {
        this.ballControl = ballControl;
    }

    @Basic
    @Column(name = "ACCELERATION")
    public Double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Double acceleration) {
        this.acceleration = acceleration;
    }

    @Basic
    @Column(name = "SPRINT_SPEED")
    public Double getSprintSpeed() {
        return sprintSpeed;
    }

    public void setSprintSpeed(Double sprintSpeed) {
        this.sprintSpeed = sprintSpeed;
    }

    @Basic
    @Column(name = "AGILITY")
    public Double getAgility() {
        return agility;
    }

    public void setAgility(Double agility) {
        this.agility = agility;
    }

    @Basic
    @Column(name = "REACTIONS")
    public Double getReactions() {
        return reactions;
    }

    public void setReactions(Double reactions) {
        this.reactions = reactions;
    }

    @Basic
    @Column(name = "BALANCE")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "SHOT_POWER")
    public Double getShotPower() {
        return shotPower;
    }

    public void setShotPower(Double shotPower) {
        this.shotPower = shotPower;
    }

    @Basic
    @Column(name = "JUMPING")
    public Double getJumping() {
        return jumping;
    }

    public void setJumping(Double jumping) {
        this.jumping = jumping;
    }

    @Basic
    @Column(name = "STAMINA")
    public Double getStamina() {
        return stamina;
    }

    public void setStamina(Double stamina) {
        this.stamina = stamina;
    }

    @Basic
    @Column(name = "STRENGTH")
    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    @Basic
    @Column(name = "LONG_SHOTS")
    public Double getLongShots() {
        return longShots;
    }

    public void setLongShots(Double longShots) {
        this.longShots = longShots;
    }

    @Basic
    @Column(name = "AGGRESSION")
    public Double getAggression() {
        return aggression;
    }

    public void setAggression(Double aggression) {
        this.aggression = aggression;
    }

    @Basic
    @Column(name = "INTERCEPTIONS")
    public Double getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(Double interceptions) {
        this.interceptions = interceptions;
    }

    @Basic
    @Column(name = "POSISTIONING")
    public Double getPosistioning() {
        return posistioning;
    }

    public void setPosistioning(Double posistioning) {
        this.posistioning = posistioning;
    }

    @Basic
    @Column(name = "VISION")
    public Double getVision() {
        return vision;
    }

    public void setVision(Double vision) {
        this.vision = vision;
    }

    @Basic
    @Column(name = "PENALTIES")
    public Double getPenalties() {
        return penalties;
    }

    public void setPenalties(Double penalties) {
        this.penalties = penalties;
    }

    @Basic
    @Column(name = "COMPOSURE")
    public Double getComposure() {
        return composure;
    }

    public void setComposure(Double composure) {
        this.composure = composure;
    }

    @Basic
    @Column(name = "MARKING")
    public Double getMarking() {
        return marking;
    }

    public void setMarking(Double marking) {
        this.marking = marking;
    }

    @Basic
    @Column(name = "STANDING_TACKLE")
    public Double getStandingTackle() {
        return standingTackle;
    }

    public void setStandingTackle(Double standingTackle) {
        this.standingTackle = standingTackle;
    }

    @Basic
    @Column(name = "SLIDING_TACKLE")
    public Double getSlidingTackle() {
        return slidingTackle;
    }

    public void setSlidingTackle(Double slidingTackle) {
        this.slidingTackle = slidingTackle;
    }

    @Basic
    @Column(name = "GK_DIVING")
    public Double getGkDiving() {
        return gkDiving;
    }

    public void setGkDiving(Double gkDiving) {
        this.gkDiving = gkDiving;
    }

    @Basic
    @Column(name = "GK_HANDLING")
    public Double getGkHandling() {
        return gkHandling;
    }

    public void setGkHandling(Double gkHandling) {
        this.gkHandling = gkHandling;
    }

    @Basic
    @Column(name = "GK_KICKING")
    public Double getGkKicking() {
        return gkKicking;
    }

    public void setGkKicking(Double gkKicking) {
        this.gkKicking = gkKicking;
    }

    @Basic
    @Column(name = "GK_POSITIONING")
    public Double getGkPositioning() {
        return gkPositioning;
    }

    public void setGkPositioning(Double gkPositioning) {
        this.gkPositioning = gkPositioning;
    }

    @Basic
    @Column(name = "GK_REFLEXES")
    public Double getGkReflexes() {
        return gkReflexes;
    }

    public void setGkReflexes(Double gkReflexes) {
        this.gkReflexes = gkReflexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiontypeEntity that = (PositiontypeEntity) o;
        return id == that.id &&
                Objects.equals(position, that.position) &&
                Objects.equals(crossing, that.crossing) &&
                Objects.equals(finishing, that.finishing) &&
                Objects.equals(headingAccuracy, that.headingAccuracy) &&
                Objects.equals(shortPassing, that.shortPassing) &&
                Objects.equals(volleys, that.volleys) &&
                Objects.equals(dribbling, that.dribbling) &&
                Objects.equals(curve, that.curve) &&
                Objects.equals(fkAccuracy, that.fkAccuracy) &&
                Objects.equals(longPassing, that.longPassing) &&
                Objects.equals(ballControl, that.ballControl) &&
                Objects.equals(acceleration, that.acceleration) &&
                Objects.equals(sprintSpeed, that.sprintSpeed) &&
                Objects.equals(agility, that.agility) &&
                Objects.equals(reactions, that.reactions) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(shotPower, that.shotPower) &&
                Objects.equals(jumping, that.jumping) &&
                Objects.equals(stamina, that.stamina) &&
                Objects.equals(strength, that.strength) &&
                Objects.equals(longShots, that.longShots) &&
                Objects.equals(aggression, that.aggression) &&
                Objects.equals(interceptions, that.interceptions) &&
                Objects.equals(posistioning, that.posistioning) &&
                Objects.equals(vision, that.vision) &&
                Objects.equals(penalties, that.penalties) &&
                Objects.equals(composure, that.composure) &&
                Objects.equals(marking, that.marking) &&
                Objects.equals(standingTackle, that.standingTackle) &&
                Objects.equals(slidingTackle, that.slidingTackle) &&
                Objects.equals(gkDiving, that.gkDiving) &&
                Objects.equals(gkHandling, that.gkHandling) &&
                Objects.equals(gkKicking, that.gkKicking) &&
                Objects.equals(gkPositioning, that.gkPositioning) &&
                Objects.equals(gkReflexes, that.gkReflexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, crossing, finishing, headingAccuracy, shortPassing, volleys, dribbling, curve, fkAccuracy, longPassing, ballControl, acceleration, sprintSpeed, agility, reactions, balance, shotPower, jumping, stamina, strength, longShots, aggression, interceptions, posistioning, vision, penalties, composure, marking, standingTackle, slidingTackle, gkDiving, gkHandling, gkKicking, gkPositioning, gkReflexes);
    }

    @OneToMany(mappedBy = "positiontype")
    public List<PositionsEntity> getPositionType() {
        return PositionType;
    }

    public void setPositionType(List<PositionsEntity> positionType) {
        PositionType = positionType;
    }
}
