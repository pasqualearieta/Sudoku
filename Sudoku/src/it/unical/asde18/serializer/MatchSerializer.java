package it.unical.asde18.serializer;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import it.unical.asde2018.sudoku.model.Match;

public class MatchSerializer extends JsonSerializer<Match> {

	@Override
	public void serialize(Match value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, value);
		gen.writeFieldName(writer.toString());

	}

}
