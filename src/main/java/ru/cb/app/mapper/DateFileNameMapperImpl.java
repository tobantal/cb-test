package ru.cb.app.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateFileNameMapperImpl implements DateFileNameMapper {

	private final SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy_hhmm");

	public String dateToFileName(Date date, String prefix, String suffix) {
		return String.join("", prefix, formatter.format(date), suffix);
	}

}
