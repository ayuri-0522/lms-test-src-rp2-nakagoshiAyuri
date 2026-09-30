package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		// 1.出勤ボタンをクリックする
		webDriver.findElement(By.name("punchIn")).click();

		// 2.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 2.勤怠情報変更画面が表示されるまで待つ
		pageLoadTimeout(10);

		scrollBy("600");

		// 3.タイトルが表示されたが確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// 5.出勤欄に出勤時間が表示された確認
		assertTrue(webDriver.findElement(By.cssSelector("td.w80")).getText().isEmpty());

		//6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		scrollBy("-600");

		// 1.退勤ボタンをクリックする
		webDriver.findElement(By.name("punchOut")).click();

		// 2.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 2.勤怠情報変更画面が表示されるまで待つ
		pageLoadTimeout(10);

		scrollBy("600");

		// 3.タイトルが表示されたが確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// 5.退勤欄に退勤時間が表示された確認
		assertTrue(webDriver.findElement(By.cssSelector("td.w80")).getText().isEmpty());

		//6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
