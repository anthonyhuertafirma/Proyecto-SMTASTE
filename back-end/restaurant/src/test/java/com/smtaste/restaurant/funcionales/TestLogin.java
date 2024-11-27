package com.smtaste.restaurant.funcionales;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class TestLogin {
    WebDriver driver;

    @BeforeEach
    public void setup(){
        driver = new ChromeDriver();
        driver.get("http://localhost:63342/front-end/login.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @Test
    public void testExistenceInputs() {
        WebElement emailTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[1]"));
        WebElement passwordTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[2]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button"));

        assertNotNull("El campo de email no existe", emailTextBox);
        assertNotNull("El campo de contraseña no existe", passwordTextBox);
        assertNotNull("El boton de login no existe", loginButton);
    }

    @Test
    public void testEmailValidation() {
        WebElement emailTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[1]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button"));

        emailTextBox.sendKeys("asd");
        loginButton.click();

        String validationMessage = emailTextBox.getAttribute("validationMessage");
        assertEquals(
                "Incluye un signo \"@\" en la dirección de correo electrónico. La dirección \"asd\" no incluye el signo \"@\".",
                validationMessage);
    }

    @Test
    public void testInvalidUser() {
        WebElement emailTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[1]"));
        WebElement passwordTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[2]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button"));

        emailTextBox.sendKeys("userInvalid@gmail.com");
        passwordTextBox.sendKeys("invalidPassword");
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"errorMessage\"]"));

        assertEquals("Usuario no encontrado", errorMessage.getText());
    }

    @Test
    public void testAdminLogin() {
        WebElement emailTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[1]"));
        WebElement passwordTextBox = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/input[2]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button"));

        emailTextBox.sendKeys("admin@gmail.com");
        passwordTextBox.sendKeys("12345");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        assertEquals("Inicio exitoso! Bienvenido, admin", alert.getText());
        alert.accept();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
