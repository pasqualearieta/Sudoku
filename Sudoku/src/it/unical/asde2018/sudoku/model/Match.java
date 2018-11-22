package it.unical.asde2018.sudoku.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import it.unical.asde2018.sudoku.logic.util.Difficulty;

@Entity
@Table
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToMany(mappedBy = "matches")
	private Set<User> players;

	@ElementCollection
	@CollectionTable(name = "match_durations", joinColumns = @JoinColumn(name = "match_id"))
	@MapKeyJoinColumn(name = "user_id")
	private Map<User, Long> durations;

	@Column(nullable = false)
	private Date creating_date;

	@Column
	private Date starting_date;

	@Column(nullable = false)
	private Difficulty difficulty;

	@Column
	private String name;

	public Match() {
		super();
	}

	public Match(User creator, Difficulty difficulty, String name) {
		super();
		this.players = new HashSet<>();
		this.durations = new HashMap<>();
		this.creating_date = new Date();
		this.starting_date = null;
		this.difficulty = difficulty;
		this.name = name;

		this.players.add(creator);
	}

	public Set<User> getPlayers() {
		return players;
	}

	public void setPlayers(Set<User> players) {
		this.players = players;
	}

	public Map<User, Long> getDurations() {
		return durations;
	}

	public void setDurations(Map<User, Long> durations) {
		this.durations = durations;
	}

	public Date getCreating_date() {
		return creating_date;
	}

	public void setCreating_date(Date creating_date) {
		this.creating_date = creating_date;
	}

	public Date getStarting_date() {
		return starting_date;
	}

	public void setStarting_date(Date starting_date) {
		this.starting_date = starting_date;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creating_date == null) ? 0 : creating_date.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((durations == null) ? 0 : durations.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((starting_date == null) ? 0 : starting_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (creating_date == null) {
			if (other.creating_date != null)
				return false;
		} else if (!creating_date.equals(other.creating_date))
			return false;
		if (difficulty != other.difficulty)
			return false;
		if (durations == null) {
			if (other.durations != null)
				return false;
		} else if (!durations.equals(other.durations))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (starting_date == null) {
			if (other.starting_date != null)
				return false;
		} else if (!starting_date.equals(other.starting_date))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}