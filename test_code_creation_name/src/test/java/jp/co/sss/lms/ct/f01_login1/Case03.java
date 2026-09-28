package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms/");

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement loginIdInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginId")));
		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

		assertTrue(loginIdInput.isDisplayed(), "ログインIDの入力欄が表示されていません");
		assertTrue(passwordInput.isDisplayed(), "パスワードの入力欄が表示されていません");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02B");

		webDriver.findElement(By.className("btn-primary")).click();

		WebElement detailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("active")));

		assertTrue(detailElement.isDisplayed(), "ここはコース詳細画面ではありません。");

		getEvidence(new Object() {
		});

	}

}
