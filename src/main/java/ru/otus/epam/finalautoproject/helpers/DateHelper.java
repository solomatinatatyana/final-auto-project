package ru.otus.epam.finalautoproject.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateHelper {

    public LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        return LocalDate.parse(date,formatter);
    }

    public LocalDate getDate(String date) {
        return convertToDate(date);
    }
}
