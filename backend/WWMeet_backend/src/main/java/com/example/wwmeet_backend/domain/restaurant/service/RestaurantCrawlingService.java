package com.example.wwmeet_backend.domain.restaurant.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantCrawlingService {

    public List<String> getRestaurantImageList(List<String> urlList) {
        long start = System.currentTimeMillis();
        List<String> imageUrlList = new ArrayList<>();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable--gpu");
        chromeOptions.addArguments("--disable-popup-blocking");
        System.setProperty("webdriver.chrome.driver",
            "/Users/qwert/Downloads/chromedriver-mac-arm64/chromedriver");
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

        for (String url : urlList) {
            log.error(url);
            chromeDriver.get(url);
            chromeDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

            WebElement div = null;
            try {
                div = chromeDriver.findElement(By.className("bg_present"));
            } catch (Exception e) {
                imageUrlList.add("");
                continue;
            }

            String[] imageUrlArray = div.getAttribute("style").split("\"");
            log.error(imageUrlArray[1]);
            imageUrlList.add("https:" + imageUrlArray[1]);
        }

        long end = System.currentTimeMillis();

        log.error(end - start + " ms");
        return imageUrlList;
    }
}
