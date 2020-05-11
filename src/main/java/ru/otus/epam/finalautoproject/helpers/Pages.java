package ru.otus.epam.finalautoproject.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventCardPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

@Component
public class Pages {
    protected MainPage mainPage;
    protected EventsPage eventsPage;
    protected EventCardPage eventCardPage;

    @Autowired
    public Pages(MainPage mainPage, EventsPage eventsPage, EventCardPage eventCardPage) {
        this.mainPage = mainPage;
        this.eventsPage = eventsPage;
        this.eventCardPage = eventCardPage;
    }


}
