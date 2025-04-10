import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;

public class Direct_Application {


    static final String anmelden_button_xpath = "//button[text()='Anmelden']";
    static final String einloggen_button_xpath = "//button[text()=' Einloggen']";
    static final String email_login_field_xpath = "//input[@placeholder='Deine E-Mail-Adresse']";
    static final String password_field_xpath = "//input[@placeholder='Dein Passwort']";
    static final String submit_and_login_xpath = "//button[@type='submit' and contains(text(), 'Einloggen')]";

    static final String birthdate_xpath = "//input[@placeholder='Geburtsdatum']";
    static final String confirm_birthdate_xpath = "//input[@id='birthday_confirmed']";
    static final String weiter_button_xpath = "//div[normalize-space(text()) = 'Weiter']";
    static final String vorname_field_xpath = "//input[@id = 'form_job_application_form_first_name']";
    static final String nachname_field_xpath = "//input[@id = 'form_job_application_form_last_name']";
    static final String gender_radio_button_xpath = "//input[@value= 'male']";
    static final String country_field_xpath = "//input[@id='form_job_application_form_nationality_ids-selectized']";
    static final String address_field_xpath = "//input[@id = 'form_job_application_form_street_and_number']";
    static final String postleitzahl_field_xpath = "//input[@id = 'form_job_application_form_zip']";
    static final String ort_field_xpath = "//input[@id = 'form_job_application_form_city_id-selectized']";
    static final String telefonnummer_field_xpath = "//input[@id = 'form_job_application_form_phone_number']";
    static final String email_field_xpath = "//input[@placeholder = 'Deine E-Mail-Adresse']";
    static final String abschluss_field_xpath = "//input[@id = 'form_job_application_form_aspired_graduation-selectized']";
    static final String abschlussjahr_field_xpath = "//input[@id = 'form_job_application_form_expected_graduation_date']";
    static final String math_field_xpath = "//input[@id= 'form_job_application_form_grade_math-selectized']";
    static final String german_field_xpath = "//input[@id= 'form_job_application_form_grade_german-selectized']";
    static final String english_field_xpath = "//input[@id= 'form_job_application_form_grade_english-selectized']";
    static final String anschreiben_upload_field_xpath = "//div[@class='input-field__label' and text()='Anschreiben']/following-sibling::div//button[@class='dz-button']";
    static final String kurzanschreiben_upload_field_xpath = "//label[@class='input-field__label' and text()='Kurzanschreiben']/following-sibling::textarea[@id='form_job_application_form_short_cover_letter']";
    static final String zeugnisse_upload_field_xpath = "//div[@class='input-field__label' and text()='Zeugnisse']/following-sibling::div//button[@class='dz-button']";
    static final String lebenslauf_upload_field_xpath = "//div[@class='input-field__label' and text()='Lebenslauf']/following-sibling::div//button[@class='dz-button']";
    static final String remove_button_xpath = "//a[@class='dz-remove']";
    static final String save_and_continue_button_xpath = "//input[@type='submit']";
    static final String submit_button_xpath = "//a[text()='Bewerbung abschicken']";

    //static final String lebenslauf_upload_field_xpath = "//button[@class= 'dz-button']/../../..//div[text()='Lebenslauf']";




    //static final String URL = "https://www.ausbildung.de/suche/?search=Fachinformatiker%2Fin+f%C3%BCr+Anwendungsentwicklung%7C";
    static final String URL = "https://www.ausbildung.de/suche/?search=Fachinformatiker%2Fin+f%C3%BCr+Anwendungsentwicklung%7C";
    static WebDriver driver;




    static final String LIST_OF_AUSBILDUNG_XPATH = "//article[@class = 'JobPostingCard_cardWrapper__SAt4K']";
    // static final String BEWERBEN_DIRECT_XPATH = "//a[@id = 't-link-direct-application'][1]";
    static final String BEWERBEN_DIRECT_XPATH = "//a[@id = 't-link-direct-application-continuation'][1]";
    static final String Bereits_Beworben_XPATH = "////a[text()='Bereits beworben']";
    //static final String company_name_xpath = "//a[@class='jp-c-header__corporation-link' and contains(text() , 'GmbH')]";
    static final String company_name_xpath = "//h2[@class='title title--left']/a";
    static final String COMPANY_Address_XPATH = "//div[@class='jp-title__address']";

    static final Scanner scanner = new Scanner(System.in);


    static String name_and_address_of_company;




    public static void main(String[] args) throws InterruptedException, IOException, AWTException {

        driver = new ChromeDriver();
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(URL);
        driver.manage().window().maximize();

        Thread.sleep(Duration.ofSeconds(25));

        List<WebElement> List_Of_Ausbildung_Articles = driver.findElements(By.xpath(LIST_OF_AUSBILDUNG_XPATH));
        System.out.println(List_Of_Ausbildung_Articles.size());


        login( wait, action);


        // Loop through the job postings and write company names and URLs to the file
        for (int i = 0; i < List_Of_Ausbildung_Articles.size(); i++) {

            if ( i == 0 || i == 2 || i == 5 )
            {
                continue;
            }

            String path = "h3#jobPostingCard-" + i;
            WebElement target_article = driver.findElement(By.cssSelector(path));

            action.moveToElement(target_article).click().perform();
            switch_tabs(driver, 1);


            WebElement bewerben_button;



                try
                {

                    bewerben_button = driver.findElement(By.xpath(BEWERBEN_DIRECT_XPATH));
                    wait.until(ExpectedConditions.visibilityOf(bewerben_button));

                }
                catch (Exception e)
                {
                    switch_back(driver);
                    continue;
                }




            name_and_address_of_company = get_name_and_address(wait);


            action.click(bewerben_button).perform();


              // we cant use this when already logged in....!
             //select_and_confirm_age( wait , action);


            fill_personal_information_form( wait , action);

            select_country( wait, action);

            fill_contact_information_form( wait ,  action);

            fill_education_information_form( wait , action);

            write_short_anschreiben( wait, action);

            try
            {
                submit_documents( wait , action );
            }
            catch (UnhandledAlertException e)
            {
                System.out.println("Unexpected alert detected!");
                Thread.sleep(Duration.ofMinutes(10));

                // Alert alert = driver.switchTo().alert();
                // alert.accept(); // Handle the alert

            }

            TODO: // add a functionality to ask user how much time he needs for review or if he wants to review.

            Thread.sleep(Duration.ofMinutes(1));

            System.out.println("Do you need to review?  yes/no ");
            String answer = scanner.nextLine();
            boolean result = (answer.equalsIgnoreCase("yes")) ? true : false;

            if (result)
            {
                System.out.println("How much time do you need to review?");
                int minutes = scanner.nextInt();
                Thread.sleep(Duration.ofMinutes(minutes));
            }



            switch_back(driver);

            System.out.println(i + ". Application Form with id " + i + " was filled succesfully.");
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
            driver.switchTo().window(tabsList.getFirst());
        }
        else
        {
            System.out.println("There are less than 2 tabs open.");
        }

    }

    public static void scroll_into_view( WebElement element) throws InterruptedException {

        // Scroll until the element is visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        Thread.sleep(2000);
    }


    public static String get_name_and_address( WebDriverWait wait ) {

        WebElement company_name = driver.findElement(By.xpath(company_name_xpath));
        wait.until(ExpectedConditions.visibilityOf(company_name));

        String Firm_Name = company_name.getText();

        WebElement company_address = driver.findElement(By.xpath(COMPANY_Address_XPATH));
        wait.until(ExpectedConditions.visibilityOf(company_address));

        StringBuilder name_and_address = new StringBuilder();

        String[] Firm_Address;
        String firm_address = company_address.getText();

        if ( firm_address.contains(",") )
        {
                     Firm_Address =  firm_address
                                    .replaceAll("[\\p{So}\\p{Cn}]", "")
                                    .replace(", " , ",")
                                    .split(",");
            name_and_address.append(Firm_Name);
            name_and_address.append(Firm_Address[0]);
            name_and_address.append("\n");
            name_and_address.append(Firm_Address[1]);
            name_and_address.append("\n");
            System.out.println(name_and_address);
        }
        else if ( Firm_Name.contains("Bartels-Langness") )
        {
            String address = "Bartels-Langness Handelsgesellschaft mbH & Co. KG\nAlte Weide 7-13\n24116 Kiel\nGermany";
            name_and_address.append(Firm_Name);
            name_and_address.append(address);
        }
        else if (firm_address.equalsIgnoreCase("E.ON SE"))
        {
            String address = "E.ON Digital Technology GmbH\nTresckowstraße 5\n30457 Hannover";
            name_and_address.append(Firm_Name);
            name_and_address.append(address);

        } else
        {
            name_and_address.append(Firm_Name);
            name_and_address.append(firm_address);
        }


        return name_and_address.toString();

    }


    public static void login(WebDriverWait wait, Actions action) throws InterruptedException {
       WebElement anmelden_button = driver.findElement(By.xpath(anmelden_button_xpath));
       wait.until(ExpectedConditions.visibilityOf(anmelden_button));
       action.click(anmelden_button).perform();

       WebElement einloggen_button = driver.findElement(By.xpath(einloggen_button_xpath));
       wait.until(ExpectedConditions.visibilityOf(einloggen_button));
       action.click(einloggen_button).perform();

       WebElement email_field = driver.findElement(By.xpath(email_login_field_xpath));
       wait.until(ExpectedConditions.visibilityOf(email_field));
       action.click(email_field).perform();
       action.sendKeys("mahboobullahzegham@gmail.com").perform();

       WebElement password_field = driver.findElement(By.xpath(password_field_xpath));
       wait.until(ExpectedConditions.visibilityOf(password_field));
       action.sendKeys(Keys.TAB).perform();
       action.click(password_field).perform();
       action.sendKeys("#marc23zco").perform();

       WebElement submit_button = driver.findElement(By.xpath(submit_and_login_xpath));
       wait.until(ExpectedConditions.elementToBeClickable(submit_button));
       action.click(submit_button).perform();

       Thread.sleep(8000);

    }


    public static void fill_personal_information_form(WebDriverWait wait , Actions action) throws InterruptedException {


        WebElement name = driver.findElement(By.xpath(vorname_field_xpath));
        wait.until(ExpectedConditions.visibilityOf(name));


        if ( (name.getAttribute("value").isEmpty()) )
        {
            action.click(name).perform();
            action.sendKeys("Mahboobullah").perform();
        }



        WebElement lastname = driver.findElement(By.xpath(nachname_field_xpath));
        wait.until(ExpectedConditions.visibilityOf(lastname));

        if ( (lastname.getAttribute("value").isEmpty()) )
        {
            action.sendKeys(Keys.TAB).perform();
            action.sendKeys("Zegham").perform();
        }



        WebElement gender_radio_button = driver.findElement(By.xpath(gender_radio_button_xpath));
        wait.until(ExpectedConditions.visibilityOf(gender_radio_button));

        if (  !(name.isSelected()) )
        {
            action.click(gender_radio_button).perform();
        }



    }


    public static void select_country(WebDriverWait wait, Actions action) throws InterruptedException {
        try
        {

            WebElement country = driver.findElement(By.xpath(country_field_xpath));
            wait.until(ExpectedConditions.visibilityOf(country));
            scroll_into_view(country);

            if (  !(country.getDomAttribute("value").equalsIgnoreCase("Afghanistan")) )
            {
                country.clear();
                action.click(country).perform();
                action.sendKeys("Afghanistan").perform();
                action.sendKeys(Keys.ENTER).perform();

                country.sendKeys(Keys.ESCAPE);

            }



        }
        catch (Exception e)
        {

        }

    }


    public static void fill_contact_information_form(WebDriverWait wait , Actions action) throws InterruptedException {

        WebElement address = driver.findElement(By.xpath(address_field_xpath));
        wait.until(ExpectedConditions.visibilityOf(address));

        scroll_into_view(address);

        if (  !(address.getDomAttribute("value").equalsIgnoreCase("Schmiedestraße 20")) )
        {
            action.click(address).perform();
            action.sendKeys("Schmiedestraße 20").perform();
            action.sendKeys(Keys.ESCAPE).perform();
        }



        WebElement postleitzahl = driver.findElement(By.xpath(postleitzahl_field_xpath));
        wait.until(ExpectedConditions.visibilityOf(postleitzahl));

        if ( !(postleitzahl.getAttribute("value").equals("29525")) )
        {
            action.click(postleitzahl).perform();
            postleitzahl.clear();
            action.sendKeys("29525").perform();
            action.sendKeys(Keys.ESCAPE).perform();
        }



        WebElement ort = driver.findElement(By.xpath(ort_field_xpath));

        wait.until(ExpectedConditions.visibilityOf(ort));

        if ( !(ort.getAttribute("value").equals("Uelzen")) )
        {
            action.click(ort).perform();
            ort.clear();
            action.sendKeys("Uelzen").perform();
            action.sendKeys(Keys.ESCAPE).perform();
        }


       try
       {

           WebElement phone = driver.findElement(By.xpath(telefonnummer_field_xpath));

           wait.until(ExpectedConditions.visibilityOf(phone));

           if ( !(phone.getAttribute("value").equals("015214724401")) )
           {
               action.click(phone).perform();
               phone.clear();
               action.sendKeys("015214724401").perform();
               action.sendKeys(Keys.ESCAPE).perform();
           }

       } catch (Exception _ ) {}



//        Thread.sleep(1000);
//
//        WebElement email = driver.findElement(By.xpath(email_field_xpath));
//
//        wait.until(ExpectedConditions.visibilityOf(email));
//
//        action.sendKeys(Keys.TAB).perform();
//        action.sendKeys("mahboobullahzegham@gmail.com").perform();





    }


    public static void fill_education_information_form(WebDriverWait wait , Actions action) throws InterruptedException {

        try
        {

            WebElement diploma_type = driver.findElement(By.xpath(abschluss_field_xpath));

            scroll_into_view(diploma_type);

            wait.until(ExpectedConditions.visibilityOf(diploma_type));

            if ( !(diploma_type.getAttribute("value").equals("Fachabitur")) )
            {
                action.click(diploma_type).perform();
                diploma_type.clear();
                action.sendKeys("Fachabitur").perform();
            }

        }
        catch (Exception _ ){}
        finally
        {
            try
            {

                WebElement graduation_year = driver.findElement(By.xpath(abschlussjahr_field_xpath));
                wait.until(ExpectedConditions.visibilityOf(graduation_year));

                if (  !(graduation_year.getDomAttribute("value").equalsIgnoreCase("2017")) )
                {
                    action.click(graduation_year).perform();
                    graduation_year.clear();
                    action.sendKeys("2017").perform();
                }



            }
            catch (Exception _ ){}
            finally
            {
                try
                {

                    WebElement math = driver.findElement(By.xpath(math_field_xpath));

                    wait.until(ExpectedConditions.visibilityOf(math));

                    if ( !(math.getAttribute("value").equals("1-")) )
                    {
                        action.click(math).perform();
                        math.clear();
                        action.sendKeys("1-").perform();
                    }


                }
                catch (Exception _ ){}
                finally
                {
                    try
                    {

                        WebElement german = driver.findElement(By.xpath(german_field_xpath));

                        wait.until(ExpectedConditions.visibilityOf(german));

                        if ( !(german.getAttribute("value").equals("3+")) )
                        {
                            action.click(german).perform();
                            german.clear();
                            action.sendKeys("3+").perform();
                        }


                    }
                    catch (Exception _ ){}
                    finally
                    {

                       try
                       {

                           WebElement english = driver.findElement(By.xpath(english_field_xpath));

                           wait.until(ExpectedConditions.visibilityOf(english));

                           if ( !(english.getAttribute("value").equals("1")) )
                           {
                               action.click(english).perform();
                               english.clear();
                               action.sendKeys("1").perform();
                           }


                           Thread.sleep(5000);
                       }
                       catch (Exception _ ){}

                    }
                }
            }
        }


    }


    public static void submit_documents(WebDriverWait wait , Actions action) throws InterruptedException, AWTException {
        try
        {
            List<WebElement> remove_buttons = driver.findElements(By.xpath(remove_button_xpath));
            for ( WebElement remove_button : remove_buttons)
            {
                scroll_into_view(remove_button);
                action.click(remove_button).perform();
                wait.until(ExpectedConditions.alertIsPresent());

                // Switch to the alert
                Alert alert = driver.switchTo().alert();

                // Example: Click "OK" on the alert
                alert.accept();  // Uncomment to click "OK"

            }

        }
        catch ( Exception _ ) {}
        finally {
            try
            {
                // upload_anschreiben(wait , action);
            }
            catch ( Exception _ ) {}
            finally
            {
                try
                {
                    upload_resume( wait, action);
                }
                catch ( Exception _ ) {}
                finally
                {
                    try
                    {
                        upload_Base_documents( wait, action);
                    }
                    catch ( Exception _ ) {}
                    finally
                    {
                        WebElement save_and_continue_button = driver.findElement(By.xpath(save_and_continue_button_xpath));
                        wait.until(ExpectedConditions.visibilityOf(save_and_continue_button));
                        scroll_into_view(save_and_continue_button);
                        // action.click(save_and_continue_button).perform();
                        Thread.sleep(10000);
                    }
                }
            }
        }


        }


    public static void write_short_anschreiben(WebDriverWait wait, Actions action) throws AWTException, InterruptedException {


        //anschreiben_upload_field_xpath
        WebElement short_anschreiben_upload_box = null;

        try
        {
            short_anschreiben_upload_box = driver.findElement(By.xpath(kurzanschreiben_upload_field_xpath));
            wait.until(ExpectedConditions.visibilityOf(short_anschreiben_upload_box));
            scroll_into_view(short_anschreiben_upload_box);
        }
        catch (Exception _ ) {}


        if (short_anschreiben_upload_box != null)
        {

            action.click(short_anschreiben_upload_box).perform();


            try
            {
                String path = "E:\\Ausbildung.de\\src\\test\\resources\\Kurz_Anschreiben.txt";

                File short_anschreiben = new File(path);

                FileReader fileReader  = new FileReader(short_anschreiben);

                BufferedReader bufferedReader = new BufferedReader(fileReader);

                StringBuilder fileContent = new StringBuilder();
                String line;



                // Read file and store content in StringBuilder
                while ((line = bufferedReader.readLine()) != null) {


                    if (line.equalsIgnoreCase("mahboobullahzegham@gmail.com"))
                    {
                        // Append new line for formatting
                        fileContent.append(line).append("\n");

                        fileContent.append("\n");

                        fileContent.append(name_and_address_of_company).append("\n");
                    }
                    else
                    {
                        fileContent.append(line).append("\n");
                    }
                }

                // Close file readers
                bufferedReader.close();
                fileReader.close();

                // Write the content into the input box
                action.sendKeys(fileContent.toString()).perform();
                action.sendKeys(Keys.TAB).perform();


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // Thread.sleep(200000);


        }

    }


    public static void upload_anschreiben(WebDriverWait wait, Actions action) throws AWTException, InterruptedException {


       //anschreiben_upload_field_xpath
        WebElement anschreiben_upload_box = null;

       try
       {
           anschreiben_upload_box = driver.findElement(By.xpath(anschreiben_upload_field_xpath));
           wait.until(ExpectedConditions.visibilityOf(anschreiben_upload_box));
           scroll_into_view(anschreiben_upload_box);
       }
       catch (Exception _ ) {}


        if (anschreiben_upload_box != null)
       {

           action.click(anschreiben_upload_box).perform();

           // Wait a bit for file dialog to open

               try
               {
                   // Use ProcessBuilder instead of exec()
                   ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\49157\\Desktop\\Auto_IT\\Upload_Anschreiben.exe");
                   Process process = processBuilder.start();

                   // Optional: Wait for the process to finish
                   int exitCode = process.waitFor();
                   System.out.println("AutoIt script executed with exit code: " + exitCode);

                   System.out.println("Anschreiben uploaded successfully!");

               }
               catch (IOException | InterruptedException e)
               {
                   e.printStackTrace();
               }



       }

    }


    public static void upload_resume(WebDriverWait wait, Actions action) throws AWTException, InterruptedException {



        WebElement resume_upload_box = null;

        try
        {
            resume_upload_box = driver.findElement(By.xpath(lebenslauf_upload_field_xpath));
            wait.until(ExpectedConditions.visibilityOf(resume_upload_box));
            scroll_into_view(resume_upload_box);
        }
        catch (Exception _ ) {}


        if (resume_upload_box != null)
        {

            action.click(resume_upload_box).perform();

            // Wait a bit for file dialog to open

            try
            {
                // Use ProcessBuilder instead of exec()
                ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\49157\\Desktop\\Auto_IT\\Upload_Resume.exe");
                Process process = processBuilder.start();

                // Optional: Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("AutoIt script executed with exit code: " + exitCode);

                System.out.println("Resume uploaded successfully!");

            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }



        }

    }


    public static void upload_Base_documents(WebDriverWait wait, Actions action) throws AWTException, InterruptedException {



        WebElement zeugnisse_upload_box = null;

        try
        {
            zeugnisse_upload_box = driver.findElement(By.xpath(zeugnisse_upload_field_xpath));
            wait.until(ExpectedConditions.visibilityOf(zeugnisse_upload_box));
            scroll_into_view(zeugnisse_upload_box);
        }
        catch (Exception _ ) {}


        if (zeugnisse_upload_box != null)
        {

            action.click(zeugnisse_upload_box).perform();

            // Wait a bit for file dialog to open

            try
            {
                // Use ProcessBuilder instead of exec()
                ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\49157\\Desktop\\Auto_IT\\Upload_Base_Documents.exe");
                Process process = processBuilder.start();

                // Optional: Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("AutoIt script executed with exit code: " + exitCode);

                System.out.println("Base Documents uploaded successfully!");

            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }



        }

    }





    public static void select_popup() throws InterruptedException, AWTException {
        Robot robot = new Robot();
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }

    public static void  select_and_confirm_age(WebDriverWait wait , Actions action) throws InterruptedException {

        WebElement age = driver.findElement(By.xpath(birthdate_xpath));

        wait.until(ExpectedConditions.visibilityOf(age));

//        action.moveToElement(age).perform();
//        action.click(age).perform();
        action.sendKeys(Keys.TAB).perform();
        age.sendKeys("18.07.2000");

        Thread.sleep(1000);

        WebElement confirm_checkbox = driver.findElement(By.xpath(confirm_birthdate_xpath));

        wait.until(ExpectedConditions.visibilityOf(confirm_checkbox));
        action.sendKeys(Keys.TAB).perform();
        action.click(confirm_checkbox).perform();

        Thread.sleep(1000);

        WebElement weiter_button = driver.findElement(By.xpath(weiter_button_xpath));

        wait.until(ExpectedConditions.visibilityOf(weiter_button));

//      action.moveToElement(weiter_button).perform();
        action.sendKeys(Keys.TAB).perform();
        action.click(weiter_button).perform();

    }





}
