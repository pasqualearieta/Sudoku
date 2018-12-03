package it.unical.asde2018.sudoku.serializer;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

public class MatchSerializer extends JsonSerializer<Match> {

	@Override
	public void serialize(Match value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		gen.writeStartObject();
		gen.writeStringField("name", value.getName());
		gen.writeObjectField("creating_date", value.getCreating_date());
		gen.writeObjectField("difficulty", value.getDifficulty());
		gen.writeObjectField("numPlayers", value.getPlayersSize());
		gen.writeObjectField("players", value.getPlayers());

		gen.writeFieldName("durations");
		gen.writeStartArray();
		for (Map.Entry<User, Long> entry : value.getDurations().entrySet()) {
			gen.writeStartObject();
			gen.writeStringField("username", entry.getKey().getUsername());
			gen.writeNumberField("value", entry.getValue());
			gen.writeEndObject();

		}
		gen.writeEndArray();

		gen.writeEndObject();

	}

}
