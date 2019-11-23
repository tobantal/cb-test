package ru.cb.app.mapper;

import java.util.Date;

public interface DateFileNameMapper {

    String dateToFileName(Date date, String prefix, String suffix);

}
