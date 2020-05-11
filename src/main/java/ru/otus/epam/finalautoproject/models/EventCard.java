package ru.otus.epam.finalautoproject.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventCard {
    private String location;
    private String name;
    private String registrationInfo;
    private LocalDate date;
    private String language;
    private List<String> speakerList = new ArrayList<>();

    public EventCard() { }

    public EventCard(EventCard eventCard){
        this.location = eventCard.location;
        this.name = eventCard.name;
        this.registrationInfo = eventCard.registrationInfo;
        this.date = eventCard.date;
        this.language = eventCard.language;
        this.speakerList = eventCard.speakerList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationInfo() {
        return registrationInfo;
    }

    public void setRegistrationInfo(String registrationInfo) {
        this.registrationInfo = registrationInfo;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public List<String> getSpeakerList() {
        return speakerList;
    }

    public void setSpeakerList(List<String> speakerList) {
        this.speakerList = speakerList;
    }

    public static EventCardBuilder anCard() {return new EventCardBuilder();}

    public static final class EventCardBuilder{
        private String location;
        private String name;
        private String registrationInfo;
        private LocalDate date;
        private String language;
        private List<String> speakerList = new ArrayList<>();

        public EventCardBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public EventCardBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventCardBuilder withRegistrationInfo(String registrationInfo) {
            this.registrationInfo = registrationInfo;
            return this;
        }

        public EventCardBuilder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public EventCardBuilder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public EventCardBuilder withSpeakerList(List<String> speakerList){
            this.speakerList = speakerList;
            return this;
        }

        public EventCard build(){
            EventCard card = new EventCard();
            card.setLocation(location);
            card.setLanguage(language);
            card.setName(name);
            card.setRegistrationInfo(registrationInfo);
            card.setDate(date);
            card.setSpeakerList(speakerList);
            return card;
        }
    }

    @Override
    public String toString() {
        return "EventCard{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", registrationInfo='" + registrationInfo + '\'' +
                ", date=" + date +
                ", language='" + language + '\'' +
                ", speakerList=" + speakerList +
                '}';
    }
}
