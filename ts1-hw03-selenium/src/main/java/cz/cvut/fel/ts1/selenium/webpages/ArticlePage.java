package cz.cvut.fel.ts1.selenium.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class ArticlePage {
    private WebDriver driver;

    @FindBy(how = How.CSS, using = "h1.c-article-title")
    private WebElement title;

    @FindBy(how = How.CSS, using = "header time")
    private WebElement publishedAt;

    @FindBy(how = How.CSS, using = "#article-info-section a[href^=\"https://doi.org\"]")
    private WebElement doi;

    public ArticlePage(WebDriver driver) {
        this.driver = driver;

        // Check if the current page is the Search results page
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (driver.findElements(By.cssSelector("article header h1.c-article-title")).isEmpty()) {
            throw new IllegalStateException("This is not the Article page " +
                    "current page is: " + driver.getCurrentUrl());
        }

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return title.getText();
    }

    public String getPublishedAt() {
        return publishedAt.getAttribute("datetime");
    }

    public String getDoi() {
        return doi.getAttribute("href");
    }
}
