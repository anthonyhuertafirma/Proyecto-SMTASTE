package com.smtaste.restaurant.funcionales;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProductos {
    WebDriver driver;

    @BeforeEach
    public void setup(){
        driver = new ChromeDriver();
        driver.get("http://localhost:63342/front-end/index.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testPresenciaProductos(){
        WebElement boxContainer = driver.findElement(By.xpath("//*[@id=\"box-container\"]"));
        List<WebElement> boxes = boxContainer.findElements(By.className("box"));

        assertFalse(boxes.isEmpty(), "El contenedor no tiene elementos de clase 'box'");
    }

    @Test
    public void testCarritoProductos() {
        WebElement buttonProducto1 = driver.findElement(By.xpath("//*[@id=\"2\"]/div[2]/button"));
        WebElement buttonProducto2 = driver.findElement(By.xpath("//*[@id=\"3\"]/div[2]/button"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", buttonProducto1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonProducto1));

        js.executeScript("arguments[0].click();", buttonProducto1);

        js.executeScript("arguments[0].scrollIntoView(true);", buttonProducto2);
        wait.until(ExpectedConditions.elementToBeClickable(buttonProducto2));
        js.executeScript("arguments[0].click();", buttonProducto2);

        driver.get("http://localhost:63342/front-end/Carro%20de%20Compras.html");
        WebElement cartItems = driver.findElement(By.xpath("//*[@id=\"cart-items\"]"));
        List<WebElement> items = cartItems.findElements(By.className("cart-item"));

        assertFalse(items.isEmpty(), "El contenedor no tiene elementos de clase 'cart-item'");
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
