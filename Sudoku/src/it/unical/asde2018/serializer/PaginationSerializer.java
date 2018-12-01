package it.unical.asde2018.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import it.unical.asde2018.sudoku.logic.Room;

public class PaginationSerializer extends JsonSerializer<Pagination>
{

	@Override
	public void serialize(Pagination value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			
		gen.writeStartObject();
		gen.writeObjectField("curr_pag", value.getCurrentPagination());
		gen.writeObjectField("tot_pag",value.getTotalPagination());
		gen.writeFieldName("matches");
		gen.writeStartArray();
		for(Map.Entry<Integer, Room> r : value.getLobby().entrySet())
		{
			gen.writeStartObject();
			gen.writeNumberField("id", r.getKey());
			gen.writeStringField("creator", r.getValue().getCreator().getUsername());
			gen.writeStringField("difficulty",r.getValue().getMatch().getDifficulty().toString());
			gen.writeStringField("name", r.getValue().getMatch().getName());
			gen.writeEndObject();
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}

}
