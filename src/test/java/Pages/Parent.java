package Pages;

import Utilities.GWD;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.time.Duration;

public class Parent {
    WebDriverWait wait = new WebDriverWait(GWD.getDriver(),
            Duration.ofSeconds(10));

    public void sendKeysFunction(WebElement element, String yazi) {
        waitUntilVisible(element); // gözükene kadar bekle
        scrollToElement(element);  // scroll yap
        element.clear();           // temizle
        element.sendKeys(yazi);    // gönder
    }

    public void scrollToElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) GWD.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public void clickFunction(WebElement element) {
        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }


    public void waitUntilVisible(WebElement element) {

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilClickable(WebElement element) {

        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public void verifyCotainsTextFunction(WebElement element, String value) {

        wait.until(ExpectedConditions.textToBePresentInElement(element, value));
        Assert.assertTrue(element.getText().contains(value));
        new Actions(GWD.getDriver()).sendKeys(Keys.ESCAPE).perform(); //açık dialog kutusu kapansın

    }
    public String findFromExcel(String text) {

        String returnData = "";

        String path = "src/main/resources/Login_Excel.xlsx";

        Sheet sheet = null;

        try {
            FileInputStream inputStream = new FileInputStream(path);  // açtım
            Workbook workbook = WorkbookFactory.create(inputStream);  // workbook'u aldım
            sheet = workbook.getSheetAt(0);  // sheet'e ulaştım
        } catch (Exception e) {

        }


        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

            if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(text)) {
                for (int j = 1; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                    returnData += sheet.getRow(i).getCell(j);

                }
            }
        }

        return returnData;
    }
}
