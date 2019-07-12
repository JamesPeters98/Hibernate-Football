package frameworks;

public class Fixture {

    private Team homeTeam, awayTeam;

    public Fixture(Team homeTeam, Team awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }
}
