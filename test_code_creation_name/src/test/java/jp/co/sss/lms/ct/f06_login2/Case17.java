package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// 1. ログイン・パスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA05");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA05");

		// 2. ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(30);

		// 3. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/user/agreeSecurity", webDriver.getCurrentUrl());

		// 4. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());

		// 5.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		// 次へボタンまでスクロール
		scrollBy("1200");

		// 1.次へボタンをクリック
		webDriver.findElement(
				By.xpath("//label[contains(., '同意します')]")).click();

		pageLoadTimeout(10);

		// 1.次へボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.tagName("h2"), 10);

		// 4. 利用規約詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("パスワード変更 | LMS", webDriver.getTitle());

		// 5. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/password/changePassword", webDriver.getCurrentUrl());

		// 6.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		// 1.現在のパスワードのテキストボックスに「StudentAA02」と入力する
		webDriver.findElement(By.name("currentPassword")).sendKeys("StudentAA05");

		// 2.新しいパスワードのテキストボックスに「StudentAA2」と入力する
		webDriver.findElement(By.name("password")).sendKeys("StudentAA7");

		// 3.確認パスワードのテキストボックスに「StudentBB2」と入力する
		webDriver.findElement(By.name("passwordConfirm")).sendKeys("StudentAA7");

		// 次へボタンまでスクロール
		scrollBy("600");

		// 4.変更ボタンをクリックする
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='変更']")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		// 5.確認モーダルの変更ボタンをクリックする
		webDriver.findElement(
				By.id("upd-btn")).click();

		// 3. コース詳細画面のURLになっているか確認する
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		// 4. コース詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// 5.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
