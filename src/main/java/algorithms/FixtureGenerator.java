package algorithms;

import frameworks.Fixture;
import frameworks.Team;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FixtureGenerator {

    public static List<List<Fixture>> getFixtures(List<Team> teams, boolean includeReverseFixtures, boolean randomise) {

        if(randomise) Collections.shuffle(teams);

        int numberOfTeams = teams.size();

        // If odd number of teams add a "ghost".
        boolean ghost = false;
        if (numberOfTeams % 2 != 0) {
            numberOfTeams++;
            ghost = true;
        }

        // Generate the fixtures using the cyclic algorithm.
        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;
        List<List<Fixture>> rounds = new LinkedList<>();

        for (int round = 0; round < totalRounds; round++) {
            List<Fixture> fixtures = new LinkedList<>();
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);
                // Last team stays in the same place while the others
                // rotate around it.
                if (match == 0) {
                    away = numberOfTeams - 1;
                }
                fixtures.add(new Fixture(teams.get(home), teams.get(away)));
            }
            rounds.add(fixtures);
        }

        // Interleave so that home and away games are fairly evenly dispersed.
        List<List<Fixture>> interleaved = new LinkedList<>();

        int evn = 0;
        int odd = (numberOfTeams / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(odd++));
            }
        }

        rounds = interleaved;

        // Last team can't be away for every game so flip them
        // to home on odd rounds.
        for (int roundNumber = 0; roundNumber < rounds.size(); roundNumber++) {
            if (roundNumber % 2 == 1) {
                Fixture fixture = rounds.get(roundNumber).get(0);
                rounds.get(roundNumber).set(0, new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
            }
        }

        if(includeReverseFixtures){
            List<List<Fixture>> reverseFixtures = new LinkedList<>();
            for(List<Fixture> round: rounds){
                List<Fixture> reverseRound = new LinkedList<>();
                for(Fixture fixture: round){
                    reverseRound.add(new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
                }
                reverseFixtures.add(reverseRound);
            }
            rounds.addAll(reverseFixtures);
        }

        return rounds;
    }
}
