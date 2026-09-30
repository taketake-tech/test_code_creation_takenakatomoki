package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginId")));

		assertEquals("ログイン | LMS", webDriver.getTitle(), "ログイン画面のタイトルが一致しません。");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02B");
		webDriver.findElement(By.className("btn-primary")).click();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.titleIs("コース詳細 | LMS"));

		assertEquals("コース詳細 | LMS", webDriver.getTitle(), "コース詳細画面のタイトルが一致しません。");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		webDriver.findElement(By.className("dropdown-toggle")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement helpLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ")));
		helpLink.click();

		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));
		assertEquals("ヘルプ | LMS", webDriver.getTitle(), "ヘルプ画面に遷移していません。");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		String originalWindow = webDriver.getWindowHandle();

		WebElement faqLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("よくある質問")));
		faqLink.click();
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle(), "よくある質問画面が正しく表示されていません。");
		getEvidence(new Object() {
		});
	}

}