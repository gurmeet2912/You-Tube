package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
//import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;
        

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }
         @Test
         public void testCase01() throws InterruptedException{
                System.out.println("Start Test Case : Test Case 01");
                driver.get("https://www.youtube.com/");
                String url=driver.getCurrentUrl();

                Assert.assertTrue(url.contains("youtube"),"URL Verification Passed");               

                //WebElement tripleLines = driver.findElement(By.cssSelector("#guide-icon"));
                //tripleLines.click();

                Thread.sleep(3000);

                WebElement about = driver.findElement(By.xpath("//a[text()='About']"));
                about.click();   
                
                List<WebElement> message= driver.findElements(By.xpath("//section[contains(@class, 'ytabout__content')]"));
                 for(WebElement msg: message){
                     System.out.println(msg.getText());
                 }

                Thread.sleep(3000);   
                
                System.out.println("End Test Case : Test Case 01");
        }

        @Test
        public void testCase02() throws InterruptedException{
                System.out.println("Start Test Case : Test Case 02");
                driver.get("https://www.youtube.com/");

               // WebElement tripleLines = driver.findElement(By.cssSelector("#guide-icon"));
                //tripleLines.click();

                Thread.sleep(3000);

                WebElement films= driver.findElement(By.xpath("//div[@id='sections']//descendant::ytd-guide-section-renderer[3]/div/ytd-guide-entry-renderer[4]"));
                films.click();

                String pageTitle = driver.getTitle();
                if(pageTitle.contains("Films")){
                    System.out.println("Films Tab Clicked");
                }

                Thread.sleep(3000);

                WebElement topSelling = driver.findElement(By.xpath("//span[text()='Top selling']"));
                topSelling.click();

                Thread.sleep(3000);

            //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            //   //  boolean moreMovies=true;
            //   WebElement arrowButtonSelector = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#right-arrow-button > ytd-button-renderer > yt-button-shape > button")));

            //    while(arrowButtonSelector.isDisplayed()){
            //     arrowButtonSelector.click();
            //     Thread.sleep(2000);
            //    }                                
                  
               }       

        @Test
        public void testCase03() throws InterruptedException{
                System.out.println("Start Test Case : Test Case 02");
                driver.get("https://www.youtube.com/");
        }

        @Test
        public void testCase04() throws InterruptedException{
                System.out.println("Start Test Case : Test Case 02");
                driver.get("https://www.youtube.com/");
        }

        @Test(dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
        public void testCase05(String searchTerms) throws InterruptedException{
                System.out.println("Start Test Case : Test Case 05");
                driver.get("https://www.youtube.com/");

                System.out.println("Verify video views count: " + searchTerms);

                WebElement searchBox = driver.findElement(By.xpath("//input[contains(@id, 'search')]"));
                searchBox.sendKeys(searchTerms);
                long totalCount = 10_00_00_000;
                long totalViewsInCrores = 0;


                List<WebElement> views = driver.findElements(By.xpath("//*[@id='metadata-line']/span[1]"));
               
                for(WebElement v : views){
                Pattern pattern = Pattern.compile("(\\d+)([kKmM]?) views");
                Matcher matcher = pattern.matcher((CharSequence) v);

            if (matcher.find()) {
                int number = Integer.parseInt(matcher.group(1));
                String suffix = matcher.group(2).toLowerCase();

                if (suffix.equals("k")) {
                    number *= 1000;
                } else if (suffix.equals("m")) {
                    number *= 1000000;
                }

                totalViewsInCrores += number;
                if(totalViewsInCrores==totalCount){
                    System.out.println("End Test Case");
                }
            }
        }
              
                // for(WebElement v : views){
                //     String s= v.getText();
                //     String[] parts = s.split("\\D+");
                //     if (parts.length > 0) {
                //         String numberStr = parts[0];
                //         int number = Integer.parseInt(numberStr);

                // }



        }
      

               
        @AfterTest 
        public void endTest() {
           driver.close();
                driver.quit();
        }
}