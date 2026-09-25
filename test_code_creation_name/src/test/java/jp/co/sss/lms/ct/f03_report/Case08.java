package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 1. sectionIdが2の行を取得
		WebElement sectionRow = webDriver.findElement(
				By.xpath("//tr[.//input[@name='sectionId' and @value='2']]"));

		// 2. 『詳細』ボタンをクリック
		sectionRow.findElement(
				By.cssSelector("input[type='submit'][value='詳細']")).click();

		// 3. コース詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/section/detail"));

		// 3. セクション詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

		// 4. ボタンが「日報【デモ】を提出する」に変更されているか確認
		assertEquals("提出済み日報【デモ】を確認する",
				webDriver.findElement(By.cssSelector("input.btn-default[value='提出済み日報【デモ】を確認する']"))
						.getAttribute("value"));

		// 5.test3のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 1.「日報【デモ】を提出する」をクリック
		webDriver.findElement(By.cssSelector("input.btn-default[value='提出済み日報【デモ】を確認する']")).click();

		// 2. レポート登録画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/report/regist"));

		// 3. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		// 4. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// 5.test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 1.テキストボックスに「修正」と入力する

		webDriver.findElement(By.id("content_0")).sendKeys("修正");

		// 2. 『提出する』ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 3. コース詳細画面のURLが表示されるまで待つ
		wait.until(ExpectedConditions.urlContains("http://localhost:8080/lms/section/detail"));

		// 4. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// 5. コース詳細画面のURLになっているか確認する
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/section/detail"));

		// 6.test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// 1.上部メニューの「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.partialLinkText("ようこそ"));

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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
	}

}
