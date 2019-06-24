package frameworks;

import entities.NationalityEntity;
import entities.PlayersEntity;
import org.hibernate.Session;

import java.util.List;

public class NationalTeam extends Team {

    protected NationalityEntity nationality;

    public NationalTeam(Session session, NationalityEntity nationalityEntity) {
        super(session);
        this.nationality = nationalityEntity;
    }

    @Override
    protected List<PlayersEntity> getPlayers() {
        return session.
                createQuery("from PlayersEntity where nationalityId = "+
                        nationality.getId()+" order by overall desc ",
                        PlayersEntity.class).setMaxResults(50)
                .list();
    }

    @Override
    public String getTeamName() {
        return nationality.getName();
    }
}
