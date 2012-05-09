package pages;

import config.Configuration;
import forms.InvoiceForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

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

    protected void fillFields(String fieldName, String fieldValue) {
        WebElement field = driver.findElement(By.name(fieldName));
        if (field.getTagName().contains("select"))
            new Select(field).selectByVisibleText(fieldValue);
        else
            field.sendKeys(fieldValue);
    }

    protected void fillFormWithValidDetails(InvoiceForm invoiceForm) {
        Map<String, Object> fields = invoiceForm.getFields();
        for (String fieldName : fields.keySet()) {
            fillFields(fieldName, (fields.get(fieldName) + ""));
        }
        driver.findElement(By.cssSelector("input[name=submitButton]")).click();
        driver.findElement(By.id("submit-modal")).click();
    }
    protected void fillFormWithInValidDetails(InvoiceForm invoiceForm) {
        Map<String, Object> fields = invoiceForm.getFields();
        for (String fieldName : fields.keySet()) {
            fillFields(fieldName, (fields.get(fieldName) + ""));
        }
        driver.findElement(By.cssSelector("input[name=submitButton]")).click();
    }
}
