package entities;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "PLAYERS", schema = "PUBLIC", catalog = "SAVE")
public class PlayersEntity {
    private int id;
    private Integer age;
    private String name;
    private NationalityEntity nationality;
    private Integer nationalityId;
    private Integer teamId;
    private Integer overall;
    private String photo;
    private Integer growth;
    private TeamsEntity team;
    private PlayerStatsEntity stats;
    private Integer positionId;
    private PositionsEntity position;
    private Map<Object, PlayerRatingsEntity> ratings;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "AGE")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "OVERALL")
    public Integer getOverall() {
        return overall;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    @Basic
    @Column(name = "PHOTO")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "GROWTH")
    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    @Basic
    @Column(name = "NATIONALITY")
    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    @Basic
    @Column(name = "TEAMID")
    public Integer getTeamId(){
        return teamId;
    }

    public void setTeamId(Integer teamId){
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayersEntity that = (PlayersEntity) o;

        if (id != that.id) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null) return false;
        if (overall != null ? !overall.equals(that.overall) : that.overall != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (growth != null ? !growth.equals(that.growth) : that.growth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (overall != null ? overall.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (growth != null ? growth.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "TEAMID", nullable = false, insertable = false, updatable = false)
    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }

    @ManyToOne
    @JoinColumn(name = "NATIONALITY", nullable = false, insertable = false, updatable = false)
    public NationalityEntity getNationality() {
        return nationality;
    }

    public void setNationality(NationalityEntity nationality) {
        this.nationality = nationality;
    }

    @ManyToOne
    @JoinColumn(name = "ID", nullable = false, insertable = false, updatable = false)
    public PlayerStatsEntity getStats() {
        return stats;
    }

    public void setStats(PlayerStatsEntity stats) {
        this.stats = stats;
    }

    @Basic
    @Column(name = "POSITION_ID")
    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    @ManyToOne
    @JoinColumn(name = "POSITION_ID", nullable = false, insertable = false, updatable = false)
    public PositionsEntity getPosition() {
        return position;
    }

    public void setPosition(PositionsEntity position) {
        this.position = position;
    }

    @MapKey(name = "positionId")
    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER)
    public Map<Object, PlayerRatingsEntity> getRatings() {
        return ratings;
    }

    public void setRatings(Map<Object, PlayerRatingsEntity> ratings) {
        this.ratings = ratings;
    }
}
