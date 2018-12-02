package it.unical.asde2018.sudoku.logic.util;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unical.asde18.serializer.PaginationSerializer;
import it.unical.asde2018.sudoku.logic.Room;

@JsonSerialize(using=PaginationSerializer.class)
public class Pagination
{
	private int currentPagination;
	private int totalPagination;
	private Map<Integer, Room> lobby;

	public Pagination(int currentPagination, int totalPagination, Map<Integer, Room> lobby)
	{
		this.currentPagination = currentPagination;
		this.totalPagination = totalPagination;
		this.lobby = lobby;
	}

	public int getCurrentPagination() {
		return currentPagination;
	}

	public void setCurrentPagination(int currentPagination) {
		this.currentPagination = currentPagination;
	}

	public int getTotalPagination() {
		return totalPagination;
	}

	public void setTotalPagination(int totalPagination) {
		this.totalPagination = totalPagination;
	}

	public Map<Integer, Room> getLobby() {
		return lobby;
	}

	public void setLobby(Map<Integer, Room> lobby) {
		this.lobby = lobby;
	}

}
