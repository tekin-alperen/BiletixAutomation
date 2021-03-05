import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PageService {

    private static final By btnEviDonAccept = By.id("_evidon-accept-button");
    private static final By cmbCategorySb = By.id("category_sb");
    private static final By cmbDateSb = By.id("date_sb");
    private static final By cmbCitySb = By.id("city_sb");
    private static final By btnDiscoverBar = By.className("discoverbar__button");
    private static final By lblComboResultHeader = By.id("combo_result_header");
    private static final String DATE = "Cmt, 27/03/21";


    private static final By eventNameList = By.cssSelector("#all_result > div.result_render > div:nth-child(n) > div > div.grid_12.alpha.fld1.col-xs-12.searchResultInfo1.hiddenOnMobile > div.grid_5.omega.searchResultInfo1b > a");
    private static final By eventStatusList = By.cssSelector("#all_result > div.result_render > div:nth-child(n) > div > div.grid_12.alpha.fld1.col-xs-12.searchResultInfo1.hiddenOnMobile > div.grid_5.omega.searchResultInfo1b > span");
    private static final By eventDateList = By.cssSelector("#all_result > div.result_render > div:nth-child(n) > div > div.grid_3.alpha.fld3.col-xs-12.searchResultInfo3.hiddenOnMobile");

    private static final By btnNextbut = By.className("nextbut");

    Elements elements = new Elements();

    public void setup() {
        elements.oneWaitElement(15);
        elements.setup();
    }

    public void baseUrlControl(){

        Assert.assertEquals(elements.currentUrl(),"https://www.biletix.com/anasayfa/TURKIYE/tr" );
    }

    public void acceptPopup() {
        elements.clickElement(btnEviDonAccept);
    }

    public void categoryClick() {
        elements.clickElement(cmbCategorySb);
    }

    public void selectCategory() {
        elements.selectElement(cmbCategorySb, 1);
    }

    public void categoryControl(String selectedValue) {
        Assert.assertTrue(selectedValue + "category not selected", elements.findElement(cmbCategorySb).getText().contains(selectedValue));
    }

    public void dateClick() {
        elements.clickElement(cmbDateSb);
    }

    public void selectDate() {
        elements.selectElement(cmbDateSb, 6);
    }

    public void dateControl(String selectedValue) {
        Assert.assertTrue("date" + selectedValue + "not selected", elements.findElement(cmbDateSb).getText().contains(selectedValue));
    }
    public void locationControl(String selectedValue) {
        Assert.assertTrue("location" + selectedValue + "not selected", elements.findElement(cmbCitySb).getText().contains(selectedValue));
    }

    public void goClick() {
        elements.clickElement(btnDiscoverBar);
        elements.oneWaitElement(10);
        elements.twoWaitElement(60);
    }

    public int getResultPage() {
        String[] resultNumber = elements.findElement(lblComboResultHeader).getText().split(" ");
        int pageNumber = Integer.parseInt(resultNumber[0]);
        if (pageNumber % 10 == 0) {
            pageNumber = pageNumber / 10;
        } else {
            pageNumber = pageNumber / 10 + 1;
        }
        return pageNumber;
    }

    public List<PageData> collectDataList(int pageNumber) {
        List<PageData> pageDataList = new ArrayList<>();
        for (int j = 1; j <= pageNumber; j++) {
            List<WebElement> listOfElementsEventName = elements.findElementList(eventNameList);
            List<WebElement> listOfElementsEventStatus = elements.findElementList(eventStatusList);
            List<WebElement> listOfElementsEventDate = elements.findElementList(eventDateList);

            for (int i = 0; i < listOfElementsEventName.size(); i++) {
                PageData pageData = new PageData();
                pageData.setEventName(listOfElementsEventName.get(i).getText());
                pageData.setEvenStatu(listOfElementsEventStatus.get(i).getText());
                pageData.setEventDate(listOfElementsEventDate.get(i).getText());
                pageDataList.add(pageData);

            }
            elements.clickElement(btnNextbut);

        }
        List<PageData> newPageList = new ArrayList<>();
        for (int i = 0; i < pageDataList.size(); i++) {
            if (!pageDataList.get(i).getEventDate().equals(DATE)) {
                newPageList.add(pageDataList.get(i));
            }
        }
        return newPageList;
    }

    public File createFile() {
        try {
            final File parentDir = new File("file");
            parentDir.mkdirs();
            File file = new File(parentDir, "/Aktiviteler.txt");
            file.createNewFile();
            return file;
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return null;
    }

    public void writeFile(List<PageData> liste) {
        try {
            File file = createFile();
            final BufferedWriter outputFile = new BufferedWriter(new FileWriter(file));
            outputFile.append("EventName" + " EventStatus" + " EventDate");
            outputFile.newLine();
            for (PageData pageData : liste) {
                outputFile.append(pageData.getEventName() + " ");
                outputFile.append(pageData.getEvenStatu() + " ");
                outputFile.append(pageData.getEventDate());
                outputFile.newLine();
            }
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }


    public void closeDriver() {
        elements.closeDriver();

    }

}

