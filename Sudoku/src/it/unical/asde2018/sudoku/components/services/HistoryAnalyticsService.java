package it.unical.asde2018.sudoku.components.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	/**
	 * Produce a bunch of performance metrics, given an username and a collection of
	 * its previous matches.
	 * 
	 * @param username        Username of the user whose metrics should be computed
	 * 
	 * @param previousMatches List of the matches this user has played
	 * 
	 *                        Returns a Map linking a numerical performance to each
	 *                        nominal metric.
	 * 
	 */

	public Map<String, Double> getMetrics(String username, List<Match> previousMatches) {
		HashMap<String, Double> metrics = new HashMap<>();

		for (Difficulty dif : Difficulty.values()) {
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

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	private Map<String, Double> getMetricsByDiff(String username, List<Match> previousMatches, Difficulty diff) {
		HashMap<String, Double> metrics = new HashMap<>();

		double total_matches = 0; // Total participations
		double won = 0; // Won ratio
		double drawn = 0; // Draw ratio
		double lost = 0;
		double duration = 0;

		for (Match m : previousMatches) {
			Difficulty m_diff = m.getDifficulty();
			if (m_diff != diff)
				continue;

			total_matches++;

			String winnerUsername = null;
			long minDuration = 0;
			boolean match_drawn = false;

			Map<User, Long> durations = m.getDurations();
			for (User participant : durations.keySet()) {
				long part_duration = durations.get(participant);
				if (part_duration != 0) {
					String part_user = participant.getUsername();

					if (part_user.equals(username)) {
						duration += part_duration;
					}

					if (part_duration > 0 && (winnerUsername == null || part_duration < minDuration)) {
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
			}

			if (winnerUsername.equals(username)) {
				if (match_drawn) {
					drawn++;
				} else {
					won++;
				}
			} else {
				lost++;
			}
		}
		if (total_matches == 0) { // Never played a match
			total_matches = 1;
		}
		metrics.put("HistorySize", total_matches);
		metrics.put("WinRatio", round(won / total_matches * 100, 2));
		metrics.put("DrawRatio", round(drawn / total_matches * 100, 2));
		metrics.put("LoseRatio", round(lost / total_matches * 100, 2));
		metrics.put("AverageDuration", duration / total_matches);
		return metrics;
	}

	public List<Match> getPreviousMatches(User user) {
		List<Match> to_return = new ArrayList<>();

		for (Match m : user.getMatches())
			to_return.add(m);
		return to_return;

	}
}