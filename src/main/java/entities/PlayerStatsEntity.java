package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PLAYER_STATS", schema = "PUBLIC", catalog = "SAVE")
public class PlayerStatsEntity {
    private int id;
    private Integer crossing;
    private Integer finishing;
    private Integer headingAccuracy;
    private Integer shortPassing;
    private Integer volleys;
    private Integer dribbling;
    private Integer curve;
    private Integer fkAccuracy;
    private Integer longPassing;
    private Integer ballControl;
    private Integer acceleration;
    private Integer sprintSpeed;
    private Integer agility;
    private Integer reactions;
    private Integer balance;
    private Integer shotPower;
    private Integer jumping;
    private Integer stamina;
    private Integer strength;
    private Integer longShots;
    private Integer aggression;
    private Integer interceptions;
    private Integer posistioning;
    private Integer vision;
    private Integer penalties;
    private Integer composure;
    private Integer marking;
    private Integer standingTackle;
    private Integer slidingTackle;
    private Integer gkDiving;
    private Integer gkHandling;
    private Integer gkKicking;
    private Integer gkPositioning;
    private Integer gkReflexes;

    @Id
    @Basic
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CROSSING")
    public Integer getCrossing() {
        return crossing;
    }

    public void setCrossing(Integer crossing) {
        this.crossing = crossing;
    }

    @Basic
    @Column(name = "FINISHING")
    public Integer getFinishing() {
        return finishing;
    }

    public void setFinishing(Integer finishing) {
        this.finishing = finishing;
    }

    @Basic
    @Column(name = "HEADING_ACCURACY")
    public Integer getHeadingAccuracy() {
        return headingAccuracy;
    }

    public void setHeadingAccuracy(Integer headingAccuracy) {
        this.headingAccuracy = headingAccuracy;
    }

    @Basic
    @Column(name = "SHORT_PASSING")
    public Integer getShortPassing() {
        return shortPassing;
    }

    public void setShortPassing(Integer shortPassing) {
        this.shortPassing = shortPassing;
    }

    @Basic
    @Column(name = "VOLLEYS")
    public Integer getVolleys() {
        return volleys;
    }

    public void setVolleys(Integer volleys) {
        this.volleys = volleys;
    }

    @Basic
    @Column(name = "DRIBBLING")
    public Integer getDribbling() {
        return dribbling;
    }

    public void setDribbling(Integer dribbling) {
        this.dribbling = dribbling;
    }

    @Basic
    @Column(name = "CURVE")
    public Integer getCurve() {
        return curve;
    }

    public void setCurve(Integer curve) {
        this.curve = curve;
    }

    @Basic
    @Column(name = "FK_ACCURACY")
    public Integer getFkAccuracy() {
        return fkAccuracy;
    }

    public void setFkAccuracy(Integer fkAccuracy) {
        this.fkAccuracy = fkAccuracy;
    }

    @Basic
    @Column(name = "LONG_PASSING")
    public Integer getLongPassing() {
        return longPassing;
    }

    public void setLongPassing(Integer longPassing) {
        this.longPassing = longPassing;
    }

    @Basic
    @Column(name = "BALL_CONTROL")
    public Integer getBallControl() {
        return ballControl;
    }

    public void setBallControl(Integer ballControl) {
        this.ballControl = ballControl;
    }

    @Basic
    @Column(name = "ACCELERATION")
    public Integer getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    @Basic
    @Column(name = "SPRINT_SPEED")
    public Integer getSprintSpeed() {
        return sprintSpeed;
    }

    public void setSprintSpeed(Integer sprintSpeed) {
        this.sprintSpeed = sprintSpeed;
    }

    @Basic
    @Column(name = "AGILITY")
    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    @Basic
    @Column(name = "REACTIONS")
    public Integer getReactions() {
        return reactions;
    }

    public void setReactions(Integer reactions) {
        this.reactions = reactions;
    }

    @Basic
    @Column(name = "BALANCE")
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "SHOT_POWER")
    public Integer getShotPower() {
        return shotPower;
    }

    public void setShotPower(Integer shotPower) {
        this.shotPower = shotPower;
    }

    @Basic
    @Column(name = "JUMPING")
    public Integer getJumping() {
        return jumping;
    }

    public void setJumping(Integer jumping) {
        this.jumping = jumping;
    }

    @Basic
    @Column(name = "STAMINA")
    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    @Basic
    @Column(name = "STRENGTH")
    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    @Basic
    @Column(name = "LONG_SHOTS")
    public Integer getLongShots() {
        return longShots;
    }

    public void setLongShots(Integer longShots) {
        this.longShots = longShots;
    }

    @Basic
    @Column(name = "AGGRESSION")
    public Integer getAggression() {
        return aggression;
    }

    public void setAggression(Integer aggression) {
        this.aggression = aggression;
    }

    @Basic
    @Column(name = "INTERCEPTIONS")
    public Integer getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(Integer interceptions) {
        this.interceptions = interceptions;
    }

    @Basic
    @Column(name = "POSISTIONING")
    public Integer getPosistioning() {
        return posistioning;
    }

    public void setPosistioning(Integer posistioning) {
        this.posistioning = posistioning;
    }

    @Basic
    @Column(name = "VISION")
    public Integer getVision() {
        return vision;
    }

    public void setVision(Integer vision) {
        this.vision = vision;
    }

    @Basic
    @Column(name = "PENALTIES")
    public Integer getPenalties() {
        return penalties;
    }

    public void setPenalties(Integer penalties) {
        this.penalties = penalties;
    }

    @Basic
    @Column(name = "COMPOSURE")
    public Integer getComposure() {
        return composure;
    }

    public void setComposure(Integer composure) {
        this.composure = composure;
    }

    @Basic
    @Column(name = "MARKING")
    public Integer getMarking() {
        return marking;
    }

    public void setMarking(Integer marking) {
        this.marking = marking;
    }

    @Basic
    @Column(name = "STANDING_TACKLE")
    public Integer getStandingTackle() {
        return standingTackle;
    }

    public void setStandingTackle(Integer standingTackle) {
        this.standingTackle = standingTackle;
    }

    @Basic
    @Column(name = "SLIDING_TACKLE")
    public Integer getSlidingTackle() {
        return slidingTackle;
    }

    public void setSlidingTackle(Integer slidingTackle) {
        this.slidingTackle = slidingTackle;
    }

    @Basic
    @Column(name = "GK_DIVING")
    public Integer getGkDiving() {
        return gkDiving;
    }

    public void setGkDiving(Integer gkDiving) {
        this.gkDiving = gkDiving;
    }

    @Basic
    @Column(name = "GK_HANDLING")
    public Integer getGkHandling() {
        return gkHandling;
    }

    public void setGkHandling(Integer gkHandling) {
        this.gkHandling = gkHandling;
    }

    @Basic
    @Column(name = "GK_KICKING")
    public Integer getGkKicking() {
        return gkKicking;
    }

    public void setGkKicking(Integer gkKicking) {
        this.gkKicking = gkKicking;
    }

    @Basic
    @Column(name = "GK_POSITIONING")
    public Integer getGkPositioning() {
        return gkPositioning;
    }

    public void setGkPositioning(Integer gkPositioning) {
        this.gkPositioning = gkPositioning;
    }

    @Basic
    @Column(name = "GK_REFLEXES")
    public Integer getGkReflexes() {
        return gkReflexes;
    }

    public void setGkReflexes(Integer gkReflexes) {
        this.gkReflexes = gkReflexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatsEntity that = (PlayerStatsEntity) o;
        return id == that.id &&
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
        return Objects.hash(id, crossing, finishing, headingAccuracy, shortPassing, volleys, dribbling, curve, fkAccuracy, longPassing, ballControl, acceleration, sprintSpeed, agility, reactions, balance, shotPower, jumping, stamina, strength, longShots, aggression, interceptions, posistioning, vision, penalties, composure, marking, standingTackle, slidingTackle, gkDiving, gkHandling, gkKicking, gkPositioning, gkReflexes);
    }
}
