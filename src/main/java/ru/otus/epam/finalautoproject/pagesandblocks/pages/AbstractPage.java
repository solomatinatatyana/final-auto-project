package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;


public abstract class AbstractPage {
    public SelfHealingDriver driver;

    public AbstractPage(SelfHealingDriver driver) {
        this.driver = driver;
    }
}
