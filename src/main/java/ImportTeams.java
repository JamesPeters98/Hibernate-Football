import entities.TeamsEntity;
import org.h2.tools.Csv;
import org.hibernate.Session;
import utils.SessionStore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportTeams {

    public static void main(final String[] args) throws SQLException {
        new ImportTeams();
    }

    public ImportTeams() throws SQLException {
        final Session session = SessionStore.getSession();

        ResultSet rs = new Csv().read(getClass().getResource("teams.csv").getFile(), null, null);
        session.beginTransaction();
        session.createQuery("delete from TeamsEntity").executeUpdate();
        while (rs.next()) {
                TeamsEntity teamsEntity = new TeamsEntity();
                teamsEntity.setId(rs.getInt(2));
                teamsEntity.setName(rs.getString(1));
                session.save(teamsEntity);
        }
        session.getTransaction().commit();
        rs.close();


    }
}