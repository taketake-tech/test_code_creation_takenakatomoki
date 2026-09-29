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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement categoryLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("fieldset ul li a")));
		categoryLink.click();
		WebElement resultTable = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("sortabletable")));
		assertTrue(resultTable.isDisplayed(), "カテゴリ別の検索結果が表示されていません。");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		WebElement dlElement = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//dl[starts-with(@id, 'question-h')]")));

		((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", dlElement);

		WebElement answer = wait.until(driver -> {
			WebElement dd = dlElement.findElement(By.xpath(".//dd[starts-with(@id, 'answer-h')]"));
			String className = dd.getAttribute("class");
			if (className != null && !className.contains("dn")) {
				return dd;
			}
			return null;
		});

		assertTrue(answer.isDisplayed(), "質問に対する答えが表示されていません");

		String actualText = answer.getText();
		assertTrue(actualText.contains("受講者の退職や解雇等"), "期待される回答の文言が含まれていません。");
		assertTrue(actualText.contains("弊社営業担当までご相談下さい。"), "期待される回答の文言が含まれていません。");

		getEvidence(new Object() {
		});
	}
}