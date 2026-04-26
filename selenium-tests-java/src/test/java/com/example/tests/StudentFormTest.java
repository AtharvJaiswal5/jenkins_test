package com.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class StudentFormTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitStudentFormSuccessfully() {
        driver.get(resolveAppUrl());

        driver.findElement(By.id("firstName")).sendKeys("Riya");
        driver.findElement(By.id("lastName")).sendKeys("Verma");
        driver.findElement(By.id("email")).sendKeys("riya.verma@college.edu");
        driver.findElement(By.id("phone")).sendKeys("9876543210");
        driver.findElement(By.id("dob")).sendKeys("2004-03-21");
        driver.findElement(By.id("gender")).sendKeys("Female");
        driver.findElement(By.id("department")).sendKeys("Computer Science");
        driver.findElement(By.id("year")).sendKeys("3");
        driver.findElement(By.id("address")).sendKeys("Sunrise Apartments, FC Road");
        driver.findElement(By.id("city")).sendKeys("Pune");
        driver.findElement(By.id("state")).sendKeys("Maharashtra");
        driver.findElement(By.id("zip")).sendKeys("411001");

        driver.findElement(By.cssSelector("input[name='skills'][value='Java']")).click();
        driver.findElement(By.id("terms")).click();

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formMessage")));
        Assertions.assertTrue(message.getText().contains("successfully"), "Submission success message should appear");

        WebElement preview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("preview")));
        Assertions.assertTrue(preview.getText().contains("Riya Verma"), "Preview should include student name");
    }

    private String resolveAppUrl() {
        String override = System.getProperty("app.url");
        if (override != null && !override.isBlank()) {
            return override;
        }

        Path localIndex = Paths.get("..", "student-form-app", "index.html").toAbsolutePath().normalize();
        return localIndex.toUri().toString();
    }
}