package org.example

import Appointment
import User
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import org.openqa.selenium.chrome.ChromeDriver


fun main() {
    // System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver")
    val chatId = System.getenv("ChatId").toLong()
    val bot = bot {
        token = System.getenv("TOKEN")
        dispatch {
            text {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
            }
        }
    }

    while (true) {
        if (findAppointment().isFound) {
            bot.sendMessage(ChatId.fromId(chatId), text = "New Appointment is available: ${findAppointment().date}")
        }
        TimeUnit.SECONDS.sleep(900)
        if (findAppointment().isFound) {
            bot.sendMessage(ChatId.fromId(chatId), text = "New Appointment is available: ${findAppointment().date}")
        }
    }
}


fun findAppointment(): Appointment {
    val options = ChromeOptions()
    options.addArguments("--headless")
    options.addArguments("--disable-extensions")
    options.addArguments("--no-sandbox")
    options.addArguments("--disable-dev-shm-usage")
    println("Starting ChromeDriver")
    val driver = ChromeDriver(options)

    driver.get("https://serviceportal.hamburg.de/HamburgGateway/FVP/FV/Bezirke/DigiTermin/Dsgvo")

// Select Einbürgerungsabteilung
    println("Suche Einbürgerunsabteilung aus")
    val einbuergerung: WebElement =
        driver.findElement(By.xpath("//tr[@class='link']//div[text()='Einbürgerungsabteilung']"))
    einbuergerung.click()

// Check Hinweis and Datenschutz
    println("Click checkboxes")
    val checkBoxHinweis: WebElement = driver.findElement(By.id("Hinweis_label"))
    checkBoxHinweis.click()

    val checkBoxDatenschutz = driver.findElement(By.id("DsgvoAcceptance_label"))
    checkBoxDatenschutz.click()

    val weiterButton = driver.findElement(By.id("buttonNext"))
    weiterButton.click()

// Fill in the form with personal data
    println("Fülle Formular aus")
    val vorname = driver.findElement(By.xpath("//input[@id='Vorname']"))
    vorname.sendKeys(User().vorname)
    val nachname = driver.findElement(By.xpath("//input[@id='Nachname']"))
    nachname.sendKeys(User().nachname)
    val email = driver.findElement(By.xpath("//input[@id='EMail']"))
    email.sendKeys(User().email)

    val weiterButton2 = driver.findElement(By.id("buttonNext"))
    weiterButton2.click()

// Select the number of appointments
    println("Wähle die Anzahl der Termine aus")
    val dropdown = Select(driver.findElement(By.xpath("//select")))
    dropdown.selectByIndex(1)

    val weiterButton3 = driver.findElement(By.id("buttonNext"))
    weiterButton3.click()

// Check if there are any appointments available
// Explicitly wait for the input field to be present
    TimeUnit.SECONDS.sleep(5)
    val noAppointments = driver.findElement(By.id("KeineTermineGefundenAlert"))
    when (noAppointments.isDisplayed) {
        true -> println("No appointments available")
        false -> {
            val availableDate = driver.findElement(By.xpath("//*[@id=\"amSchnellsten\"]/div/div/p"))
            val availableDateText = availableDate.text.trim()
            println("Appointments available on: $availableDateText")
            return Appointment(availableDateText, true)
        }
    }
    return Appointment("", false)
}
