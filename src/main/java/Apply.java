import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class Apply {


    static final String URL = "https://www.ausbildung.de/suche/?search=Fachinformatiker%2Fin+f%C3%BCr+Anwendungsentwicklung%7C";
    static WebDriver driver;

    static Map< String , String > Ausbildung_list = new LinkedHashMap<>();

    // static final String ALLOW_COOKIES_BUTTON_XPATH = "//button[text() = 'Allow all cookies']";
    static final String AUSBILDUNG_LINK_XPATH = "//article[@class = 'JobPostingCard_cardWrapper__SAt4K']/a";

    static final String LIST_OF_AUSBILDUNG_XPATH = "//article[@class = 'JobPostingCard_cardWrapper__SAt4K']";
    static final String BEWERBEN_ONLINE_XPATH = "//a[@id = 't-link-online-application'][1]";
    static final String BEWERBEN_DIRECT_XPATH = "//a[@id = 't-link-direct-application'][1]";
    static final String COMPANY_NAME_XPATH = "//div[@class='jp-title__address']";


    public static void main(String[] args) throws InterruptedException {

        //Ausbildung_list.clear();
        System.out.println(Ausbildung_list);

        driver = new ChromeDriver();
        Actions action = new Actions(driver);

        WebDriverWait wait = new WebDriverWait( driver , Duration.ofSeconds(10) );

        driver.get(URL);
        driver.manage().window().maximize();

        Thread.sleep(Duration.ofSeconds(30));

        List<WebElement> List_Of_Ausbildung_Articles = driver.findElements(By.xpath(LIST_OF_AUSBILDUNG_XPATH));

        System.out.println(List_Of_Ausbildung_Articles.size());

        for ( int i = 0; i < List_Of_Ausbildung_Articles.size(); i++)
        {

            String path = "h3#jobPostingCard-" + i;
            WebElement target_article = driver.findElement(By.cssSelector(path));

            action.moveToElement(target_article).click().perform();

            switch_tabs(driver , 1);

            WebElement company_name_address = driver.findElement(By.xpath(COMPANY_NAME_XPATH));
            wait.until(ExpectedConditions.visibilityOf(company_name_address));
            String company_name = company_name_address.getText();

            WebElement bewerben_button;

            String application_type;

            try
            {
                bewerben_button = driver.findElement(By.xpath(BEWERBEN_ONLINE_XPATH));
                wait.until(ExpectedConditions.visibilityOf(bewerben_button));
                application_type = "online";

            }
            catch (Exception e)
            {
                bewerben_button = driver.findElement(By.xpath(BEWERBEN_DIRECT_XPATH));
                wait.until(ExpectedConditions.visibilityOf(bewerben_button));
                application_type = "direct";

            }

            String New_URL;

            if (application_type.equals("online"))
            {
                New_URL = bewerben_button.getDomAttribute("href");
            }
            else
            {
                action.click(bewerben_button).perform();
                New_URL = driver.getCurrentUrl();
            }


            Ausbildung_list.put( company_name , New_URL );
            create_ausbildung_file.ausbildung_map.put( company_name , New_URL );
            System.out.println( i + ".  company_name_and_address " + company_name + " with the URL _ : ");
            System.out.println(Ausbildung_list.get(company_name));
            System.out.println(" was added to the list.");

            switch_back(driver);

        }





    }


    public static void switch_tabs(WebDriver driver , int index)
    {
        // Get all window handles
        Set<String> allTabs = driver.getWindowHandles();

        // Convert the set to a list (to easily switch to the next tab)
        List<String> tabsList = new ArrayList<>(allTabs);

        // Switch to the next tab (if there is one)
        if (tabsList.size() > index)
        {
            String nextTab = tabsList.get(index);  // Switch to the second tab (index 1)
            driver.switchTo().window(nextTab);
        }
        else
        {
            System.out.println("No other tabs are open.");
        }

    }




    public static void switch_back(WebDriver driver)
    {
        // Get all window handles
        Set<String> allTabs = driver.getWindowHandles();
        List<String> tabsList = new ArrayList<>(allTabs);

        // Check if there are at least 2 tabs

        if (tabsList.size() > 1)
        {
            // Close the tab at index 1
            driver.close();

            // Optionally, switch back to the first tab (index 0)
            driver.switchTo().window(tabsList.get(0));
        }
        else
        {
            System.out.println("There are less than 2 tabs open.");
        }

    }


    public static void add_collection()
    {
        System.out.println(Ausbildung_list.size());

        create_ausbildung_file.ausbildung_map.putAll(Ausbildung_list);
    }

}
