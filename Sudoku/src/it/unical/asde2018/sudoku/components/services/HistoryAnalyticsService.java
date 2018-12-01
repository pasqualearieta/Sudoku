package it.unical.asde2018.sudoku.components.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Service
public class HistoryAnalyticsService {

	private final double WIN_COEF = 0.75;
	private final double DRAW_COEF = 0.6;
	private final double LOSE_COEF = 0.2;

	public Map<String, Double> getMetrics(String username, List<Match> previousMatches) {
		/*
		 * Produce a bunch of performance metrics, given an username and a collection of
		 * its previous matches.
		 * 
		 * @param username Username of the user whose metrics should be computed
		 * 
		 * @param previousMatches List of the matches this user has played
		 * 
		 * Returns a Map linking a numerical performance to each nominal metric.
		 * 
		 */

		HashMap<String, Double> metrics = new HashMap<>();

		Difficulty[] difficulties = new Difficulty[] { Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD };

		for (Difficulty dif : difficulties) {
			Map<String, Double> dif_metrics = getMetricsByDiff(username, previousMatches, dif);
			String prefix = null;
			if (dif == Difficulty.EASY) {
				prefix = "easy";
			} else if (dif == Difficulty.MEDIUM) {
				prefix = "medium";
			} else if (dif == Difficulty.HARD) {
				prefix = "hard";
			}
			for (String s : dif_metrics.keySet()) {
				metrics.put(prefix + s, dif_metrics.get(s));
			}

		}
		return metrics;
	}

	private Map<String, Double> getMetricsByDiff(String username, List<Match> previousMatches, Difficulty diff) {
		HashMap<String, Double> metrics = new HashMap<>();

		double total_matches = 0; // Total participations
		double won = 0; // Won ratio
		double drawn = 0; // Draw ratio
		double lost = 0;
		double duration = 0;
		// double score = 0; TODO: Consider score and standings

		for (Match m : previousMatches) {
			
			System.out.println(m.getDurations().toString());
			Difficulty m_diff = m.getDifficulty();
			if (m_diff != diff)
				continue;
			int removedRows = m_diff.getNumberToRemove();
			total_matches++;

			String winnerUsername = null;
			long minDuration = 0;
			boolean match_drawn = false;

			// Compute match results
			// TODO: Consider moving to Match.java as Enum.OUTCOME playerStatus(username)

			System.out.println(m.getDurations().toString() +"    COSE DEL MATCH");
			
			Map<User, Long> durations = m.getDurations();
			for (User participant : durations.keySet()) {
				long part_duration = durations.get(participant);
				String part_user = participant.getUsername();

				if (part_user.equals(username)) {
					duration += part_duration;
				}

				if (winnerUsername == null || part_duration < minDuration) {	
					winnerUsername = part_user;
					minDuration = part_duration;
					match_drawn = false;
				} else if (part_duration == minDuration) { // It's a partial draw
					if (part_user.equals(username) || winnerUsername.equals(username)) {
						match_drawn = true;
						winnerUsername = username;
					}
				}
			} // Compute outcomes for given user

			double outcome_coef = 0;

			if (winnerUsername.equals(username)) {
				if (match_drawn) {
					outcome_coef = DRAW_COEF;
					drawn++;
				} else {
					outcome_coef = WIN_COEF;
					won++;
				}
			} else {
				outcome_coef = LOSE_COEF;
				lost++;
			}
			// score += outcome_coef * removedRows;
		}
		if (total_matches == 0) { // Never played a match
			/*
			 * TODO: Using this technique avoid NaN to be displayed, eventually consider a
			 * different visualization for new user
			 */
			total_matches = 1;
		}
		metrics.put("HistorySize", total_matches);
		metrics.put("WinRatio", won / total_matches * 100);
		metrics.put("DrawRatio", drawn / total_matches * 100);
		metrics.put("LoseRatio", lost / total_matches * 100);
		metrics.put("AverageDuration", duration / total_matches);
		// metrics.put("Score", score);
		return metrics;
	}

	public List<Match> getPreviousMatches(User user) {
		List<Match> to_retunr = new ArrayList<Match>();

		System.out.println(user.toString() + "TU SEI");
		
		for (Match m : user.getMatches())
		{	to_retunr.add(m);
		System.out.println("ma dio bau");
		}
		
		
		return to_retunr;

	}
}