package test_Derrick686Site;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.TakesScreenshot;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.media.VideoFormatKeys.QualityKey;


public class TestBase {
    final static Logger logger = LoggerFactory.getLogger(TestBase.class);
    private ScreenRecorder sr;
    String recordsFolder= "src/test/resources/records";


    public static final String XPATH_EMAIL_LOGIN_FIELD = "sw-form-capture-email-input";
    public static final String XPATH_PASSWORD_LOGIN_FIELD = "sw-form-password-input";
    public static final String XPATH_LOGIN_SUBMIT_BTN = "sw-sign-in-submit-btn";


    public static final String BILLYE_EMAIL = "billye@example.com";
    public static final String LUCIE_EMAIL = "lucie@example.com";
    public static final String EDRA_EMAIL = "edra@example.com";

    public static final String VALID_PASSWORD = "123456";
    public static final String INVALID_PASSWORD = "123";

    public static final String SITE_LOGIN_PAGE = "https://derrick686.softr.app/login";
    public static final String SITE_CLIENTS_PAGE = "https://derrick686.softr.app/clients";

    public static final String CONFIRM_MESSAGE = "Welcome to your Client Portal";
    public static final String ERROR_MESSAGE = "Invalid email or password";
    WebDriver wd;

//todo------------------------------------- @BeforeSuite-------@AfterSuite ----------------------------------------------

    @BeforeSuite(alwaysRun=true)
    public void initBrowser() {
                    System.out.println("sout Initialise BeforeSuite: initBrowser()");
                    logger.info("logger Initialise BeforeSuite: initBrowser()");
        wd = new FirefoxDriver();
        wd.manage().window().maximize();
    }

    @AfterSuite
    public void closeBrowser(){
                    System.out.println("Finish AfterSuit: closeBrowser()");
                    logger.info("After Suite logger");
        wd.close();
        wd.quit();
    }



//todo------------------------------------------------ МЕТОДЫ ---------------------------------------------------------
    public void Auth(String email, String password) throws InterruptedException {
        field(By.id(XPATH_EMAIL_LOGIN_FIELD), email);
        field(By.id(XPATH_PASSWORD_LOGIN_FIELD), password);
        button(By.id(XPATH_LOGIN_SUBMIT_BTN));
        wait(2000);
    }

    public void field(By by, String key) {
        WebElement fieldInput = wd.findElement(by);
        fieldInput.clear();
        fieldInput.click();
        fieldInput.sendKeys(key);
    }
    public void button(By by) {
        WebElement button = wd.findElement(by);
        button.click();
    }

    public void wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public Boolean searchInPageSource(String text){ // метод contains можно использовать
        return wd.getPageSource().contains(text);
    }

    public void fieldSearchClient(String stringS) throws InterruptedException {
        WebElement input = wd.findElement(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"));
        input.click();
        input.clear();
        input.sendKeys(stringS);
        input.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    public void logout() {
        wd.findElement(By.id("navbarDropdown")).click();
        //Find element by class name via cssSelector
        //Attention to that spaces in class name are replaced by dots for css selector
        wd.findElement(By.cssSelector(".d-item.d-flex.justify-content-start.align-items-center.navigate")).click();

        //Second option: locate a link by its text
        //wd.findElement(By.partialLinkText("Sign Out")).click();
    }


/** ----------------------------------------- DATAPROVIDER ---------------------------------------------------*/
//-----------------------------методы с учетом Использования DataProveder-----------------------------
    public void authDataProvider(String usernameDP, String passwordDP) throws InterruptedException {
        //Enter manager's email
        WebElement username = wd.findElement(By.id("sw-form-capture-email-input"));
        username.click();
        username.clear();
        username.sendKeys(usernameDP);

        //Enter manager's password
        WebElement password = wd.findElement(By.id("sw-form-password-input"));
        password.click();
        password.clear();
        password.sendKeys(passwordDP);

        //Submit the form
        WebElement button = wd.findElement(By.id("sw-sign-in-submit-btn"));
        button.click();

        Thread.sleep(2000);
    }


/** ---------------------------------------Screenshots y Records-------------------------------------------------*/

    public String takeScreenshot() {
        File tmp = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("src/test/resources/screenshots/screen"+System.currentTimeMillis()+".png");
        try {
            screenshot.createNewFile();
            com.google.common.io.Files.copy(tmp,screenshot);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return "Failed to create a screenshot";
        }
        return screenshot.getAbsolutePath();
    }

    public void startRecording() {
        File file = new File(recordsFolder);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0,0, dimension.width, dimension.height);
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        logger.info("Starting screen recording");
        try {
            sr = new Recorder(gc, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_MJPG,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, "MyVideo");
            sr.start();
        } catch (IOException | AWTException e1){
            logger.error(e1.getMessage());
        }
    }

    public void stopRecoding() {
        logger.info("Stopping screen recording");
        try {
            sr.stop();
        } catch (IOException e1){
            logger.error(e1.getMessage());
        }
    }

    public void deleteAllRecordings(){
        File dir = new File(recordsFolder);
        for (File f: dir.listFiles()){
            try
            {
                f.delete();
            }
            catch (Exception e)
            {
                logger.error("Error while cleaning Records folder " + e.getMessage());
            }
        }
        logger.info("Cleaned Records folder");
    }
}







//--------------------------------------------- ЗАМЕТКИ ---------------------------------------------------
//    @AfterMethod logOut
//    @AfterTest quit

//                Способы проверки элементов на странице:
//    WebElement element = wd.findElement(By.linkText("text"));        linkText
//    WebElement element = wd.findElement(By.partialLinkText("text")); partialLinkText

//    Assert.assertEquals(wd.getPageSource().contains(ERROR_MESSAGE),Boolean.TRUE);
//    Assert.assertFalse(searchInPageSource("CLIENTS"));  //метод contains
//    Assert.assertTrue(wd.findElements(By.partialLinkText("CLIENTS")).isEmpty()); //можно проверить что элемент отсутствует .isEmpty()


