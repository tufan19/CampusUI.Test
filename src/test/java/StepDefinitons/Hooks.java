package StepDefinitons;

import Utilities.ExcelUtility;
import Utilities.GWD;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @After// her senaryodan sonra çalışacak
    public void after(Scenario senaryo) {
        System.out.println("senaryo bitti");

        ExcelUtility.writeExcel("src/test/java/ApachePOI/resource/ScenarioStatus.xlsx",senaryo,GWD.threadBrowserGet());

        if (senaryo.isFailed()){
            // extend report çalışan kısım
            TakesScreenshot ts=(TakesScreenshot) GWD.getDriver();
            final byte[] hafizadakiHali=ts.getScreenshotAs(OutputType.BYTES);
            senaryo.attach(hafizadakiHali, "image/png","screenshot name");
        }




        GWD.quitDriver();
    }
}
