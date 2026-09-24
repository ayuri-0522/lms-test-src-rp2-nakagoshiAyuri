package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

	//1.待ち時間を最大10秒に設定する
	final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//指定したURLの画面を開く
		webDriver.get("http://localhost:8080/lms");

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

		// 3. コース詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/course/detail"));

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 1. ヘッダーの機能リンクをクリック
		webDriver.findElement(By.className("dropdown-toggle")).click();

		// 2. ヘッダーの機能タグのヘルプリンクをクリック
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ")));

		// 3. ヘッダーの機能リンクをクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 4. コース詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/help"));

		// 5. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		// 6. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// 7.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 1.元のタブを取得
		String originalWindow = webDriver.getWindowHandle();

		// 2.ヘッダーの機能タグのヘルプリンクをクリック
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("よくある質問")));

		// 3.ヘッダーの機能リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// 4.新しいタブが開くまで待つ
		wait.until(
				ExpectedConditions.numberOfWindowsToBe(2));

		// 5.新しいタブへ切り替える
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		// 6. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		// 7. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// 8.test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 1.キーワードの入力欄に「キーワード」と入力する
		webDriver.findElement(By.id("form")).sendKeys("キャンセル");

		//2.『検索』ボタンをクリックする
		webDriver.findElement(By.className("btn-primary")).click();

		// 3.『キャンセル』の文字が含まれる検索結果を検索結果に表示する

		WebElement question = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("dt")));

		assertTrue(question.getText().contains("キャンセル"));

		// 4.test5のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力した「キャンセル」を消去")
	void test06() {
		// 1.『検索』ボタンをクリックする
		webDriver.findElement(
				By.cssSelector("input[value='クリア']")).click();

		// 2. キーワード入力欄の文字「キャンセル」の文字が消えたかの確認
		assertEquals("", webDriver.findElement(By.id("form")).getAttribute("value"));

		// 3.test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
