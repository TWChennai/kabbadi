package pages;

import config.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage goToGoogle() {
        driver.get("http://www.google.com");
        return new BasePage(driver);
    }

    public ListAdminInvoicesPage returnToKabbadi() {
        driver.get(Configuration.KABBADI_URL);
        return new ListAdminInvoicesPage(driver);
    }

    public String getTitle(){
        return  driver.getTitle();
    }

    void fillFields(String fieldName, String fieldValue) {
        driver.findElement(By.name(fieldName)).sendKeys(fieldValue);
    }
}
