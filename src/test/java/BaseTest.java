import org.junit.Test;

import java.util.List;

public class BaseTest {

    @Test
    public void test() {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        PageService pageService = new PageService();
        pageService.setup();
        pageService.baseUrlControl();
        pageService.acceptPopup();
        pageService.categoryClick();
        pageService.selectCategory();
        pageService.categoryControl("MÜZİK");
        pageService.dateClick();
        pageService.selectDate();
        pageService.dateControl("Önümüzdeki 30 Gün");
        pageService.locationControl("Tüm Türkiye");
        pageService.goClick();
        int pageNumber = pageService.getResultPage();
        List<PageData> pageDataList = pageService.collectDataList(pageNumber);
        pageService.writeFile(pageDataList);

        pageService.closeDriver();
    }

}
