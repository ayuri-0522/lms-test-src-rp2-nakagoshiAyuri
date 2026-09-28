package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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

	//待ち時間を最大10秒に設定する
	final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// 1.上部メニューの「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.partialLinkText("ようこそ受講生")).click();

		// 2. セクション詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlContains("http://localhost:8080/lms/user/detail"));

		// 3. セクション詳細画面のURLになっているか確認する
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/user/detail"));

		// 4. h2タグがユーザー詳細になったか確認する
		assertEquals("ユーザー詳細", webDriver.findElement(By.tagName("h2")).getText());

		// 5.test6のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 週次レポートの列までスクロール
		scrollBy("1000");

		// 1.dailyReportSubmitId = 3 の「修正」ボタンをクリック
		webDriver.findElement(
				By.xpath(
						"//form[@action='/lms/report/regist'][.//input[@name='dailyReportSubmitId' and @value='3']]//input[@value='修正する']"))
				.click();

		// 2. セクション詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlContains("http://localhost:8080/lms/report/regist"));

		// 3. レポート登録画面のURLになっているか確認する
		assertTrue(webDriver.findElement(By.tagName("h2")).getText().contains("週報【デモ】"));

		// 4. レポート登録画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		// 5.test7のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// 1. 理解度のselectボックスをクリック
		webDriver.findElement(By.cssSelector("select.form-control")).click();

		// 2. ヘッダーの機能タグのヘルプリンクをクリック
		visibilityTimeout(By.cssSelector("option[value='2']"), 10);

		// 3. ヘッダーの機能リンクをクリック
		webDriver.findElement(
				By.cssSelector("option[value='2']")).click();

		// 提出ボタンまでスクロール
		scrollBy("1000");

		// 4. 『提出する』ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 5. レポート登録画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/report/complete", webDriver.getCurrentUrl());

		// 6.「学習項目を入力した場合は、理解度は必須です。」と表示の確認
		assertTrue(
				webDriver.getPageSource().contains("学習項目を入力した場合は、理解度は必須です。"),
				"エラーメッセージが表示されていません。");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 1.　学習項目にITパスポートと入力する
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("ITパスポート");

		// 2. 理解度のselectボックスをクリック
		webDriver.findElement(By.cssSelector("select.form-control")).click();

		// 2. ヘッダーの機能タグのヘルプリンクをクリック
		visibilityTimeout(By.cssSelector("option[value='2']"), 10);

		// 3. ヘッダーの機能リンクをクリック
		webDriver.findElement(
				By.cssSelector("option[value='']")).click();

		// 提出ボタンまでスクロール
		scrollBy("1000");

		// 4. 『提出する』ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 5. レポート登録画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/report/complete", webDriver.getCurrentUrl());

		// 6.「学習項目を入力した場合は、理解度は必須です。」と表示の確認
		assertTrue(
				webDriver.getPageSource().contains("理解度を入力した場合は、学習項目は必須です。"),
				"エラーメッセージが表示されていません。");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// 2. 理解度のselectボックスをクリック
		webDriver.findElement(By.cssSelector("select.form-control")).click();

		// 2. ヘッダーの機能タグのヘルプリンクをクリック
		visibilityTimeout(By.cssSelector("option[value='2']"), 10);

		// 3. ヘッダーの機能リンクをクリック
		webDriver.findElement(
				By.cssSelector("option[value='']")).click();

		webDriver.findElement(By.cssSelector("textarea")).sendKeys("あいうえお");

		// 提出ボタンまでスクロール
		scrollBy("1000");

		// 4. 『提出する』ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 5. レポート登録画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/report/complete", webDriver.getCurrentUrl());

		// 6.「学習項目を入力した場合は、理解度は必須です。」と表示の確認
		assertTrue(
				webDriver.getPageSource().contains("理解度を入力した場合は、学習項目は必須です。"),
				"エラーメッセージが表示されていません。");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
