package ru.otus.epam.finalautoproject.tests.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.*;

@Component
public class Pages {
    public MainPage mainPage;
    public EventsPage eventsPage;
    public EventCardPage eventCardPage;
    public TalksLibraryPage talksLibraryPage;
    public TalksCardPage talksCardPage;

    @Autowired
    public Pages(MainPage mainPage, EventsPage eventsPage, EventCardPage eventCardPage,
                 TalksLibraryPage talksLibraryPage, TalksCardPage talksCardPage) {
        this.mainPage = mainPage;
        this.eventsPage = eventsPage;
        this.eventCardPage = eventCardPage;
        this.talksLibraryPage = talksLibraryPage;
        this.talksCardPage = talksCardPage;
    }
}
