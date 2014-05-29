package com.angel.my.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
/**
 * 日期格式转换注解
 *
 */
public class CustomDateSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator gen,SerializerProvider arg2) throws IOException,JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(value);
		gen.writeString(formattedDate);
	}
}