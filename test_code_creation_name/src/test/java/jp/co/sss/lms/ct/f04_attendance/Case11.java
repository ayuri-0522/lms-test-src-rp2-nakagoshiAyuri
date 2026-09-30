package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		//指定したURLの画面を開く
		goTo("http://localhost:8080/lms");

		//指定したURLと一致しているか確認する
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		//test1のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 1. ログイン・パスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA1");

		// 2. ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 3. h2が表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 10);

		// 4. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		// 5. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//6.test2のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// 1.ヘッダー勤怠ボタンをクリックする
		webDriver.findElement(By.linkText("勤怠")).click();

		//確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 2.勤怠情報変更画面が表示されるまで待つ
		pageLoadTimeout(10);

		// 3.タイトルが表示されたが確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// 4. h2タグと同じか確認する
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());

		//6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// 1.「勤怠情報を直接編集する」を押下
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();

		// 2.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 10);

		// 3.タイトルが表示されたが確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// 4. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		//6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		List<WebElement> buttons = webDriver.findElements(By.cssSelector("button.default-button"));

		for (int i = 0; i < buttons.size(); i++) {
			WebElement button = buttons.get(i);

			((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", button);

		}

		scrollBy("600");

		// 最後に更新ボタンへ
		WebElement updateButton = webDriver.findElement(By.className("update-button"));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", updateButton);

		//確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 2.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 20);

		// 4. h2タグと同じか確認する
		assertEquals("勤怠管理", webDriver.findElement(By.tagName("h2")).getText());

		scrollBy("100");

		// 5.出勤09:00、退勤18:00が表示されているか確認する
		assertTrue(webDriver.findElement(By.cssSelector("td.w80")).getText().matches("09:00|18:00"));

		//6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
