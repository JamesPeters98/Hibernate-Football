import Exceptions.NoDatabaseSelectedException;
import entities.PlayerStatsEntity;
import entities.PlayersEntity;
import org.h2.tools.Csv;
import org.hibernate.Session;
import utils.SessionStore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportPlayers {

    public static void main(final String[] args) throws SQLException, NoDatabaseSelectedException {
        new ImportPlayers();
    }

    public ImportPlayers() throws SQLException, NoDatabaseSelectedException {
        final Session session = SessionStore.getSession();

        ResultSet rs = new Csv().read(getClass().getResource("players.csv").getFile(), null, null);
        session.beginTransaction();
        session.createQuery("delete from PlayersEntity").executeUpdate();
        session.createQuery("delete from PlayerStatsEntity ").executeUpdate();
        session.getTransaction().commit();

        session.beginTransaction();
        int id = 1;
        while (rs.next()) {
                PlayersEntity player = new PlayersEntity();
                try {
                    player.setId(id);
                    player.setName(rs.getString(1));
                    player.setAge(rs.getInt(2));
                    player.setPhoto(rs.getString(3));
                    player.setNationalityId(rs.getInt(4));
                    player.setOverall(rs.getInt(5));
                    player.setGrowth(rs.getInt(6));
                    player.setTeamid(rs.getInt(7));
                    player.setPositionId(rs.getInt(8));
                    session.save(player);



                    PlayerStatsEntity stats = new PlayerStatsEntity();
                    stats.setId(player.getId());
                    stats.setCrossing(rs.getInt(9));
                    stats.setFinishing(rs.getInt(10));
                    stats.setHeadingAccuracy(rs.getInt(11));
                    stats.setShortPassing(rs.getInt(12));
                    stats.setVolleys(rs.getInt(13));
                    stats.setDribbling(rs.getInt(14));
                    stats.setCurve(rs.getInt(15));
                    stats.setFkAccuracy(rs.getInt(16));
                    stats.setLongPassing(rs.getInt(17));
                    stats.setBallControl(rs.getInt(18));
                    stats.setAcceleration(rs.getInt(19 ));
                    stats.setSprintSpeed(rs.getInt(20));
                    stats.setAgility(rs.getInt(21));
                    stats.setReactions(rs.getInt(22));
                    stats.setBalance(rs.getInt(23));
                    stats.setShotPower(rs.getInt(24));
                    stats.setJumping(rs.getInt(25));
                    stats.setStamina(rs.getInt(26));
                    stats.setStrength(rs.getInt(27));
                    stats.setLongShots(rs.getInt(28));
                    stats.setAggression(rs.getInt(29));
                    stats.setInterceptions(rs.getInt(30));
                    stats.setPosistioning(rs.getInt(31));
                    stats.setVision(rs.getInt(32));
                    stats.setPenalties(rs.getInt(33));
                    stats.setComposure(rs.getInt(34));
                    stats.setMarking(rs.getInt(35));
                    stats.setStandingTackle(rs.getInt(36));
                    stats.setSlidingTackle(rs.getInt(37));
                    stats.setGkDiving(rs.getInt(38));
                    stats.setGkHandling(rs.getInt(39));
                    stats.setGkKicking(rs.getInt(40));
                    stats.setGkPositioning(rs.getInt(41));
                    stats.setGkReflexes(rs.getInt(42));

                    session.save(stats);

                    id++;
                } catch (Exception e){
                    e.printStackTrace();
                }

        }
        session.getTransaction().commit();
        rs.close();


    }
}