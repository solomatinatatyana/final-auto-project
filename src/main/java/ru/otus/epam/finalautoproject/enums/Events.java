package ru.otus.epam.finalautoproject.enums;

public enum Events {
    UPCOMING_EVENTS("Upcoming events"),
    PAST_EVENTS("Past events");
    private String type;
    Events(String type) { this.type = type; }
    public String getEvent() { return type; }
}
