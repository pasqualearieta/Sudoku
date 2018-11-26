package it.unical.asde18.serializer;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;

import it.unical.asde2018.sudoku.logic.Room;

public class LobbySerializer
{
	@JsonSerialize(keyUsing = MapSerializer.class)
	private Map<Integer, Room> serializedObject = new HashMap<>();

	private ObjectMapper mapper = new ObjectMapper();

	public LobbySerializer(Map<Integer, Room> serializedObject)
	{
		this.serializedObject = serializedObject;
	}

	public String getJSon() {
		String json = "";
		try
		{
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serializedObject);
		} catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return json;
	}
}
