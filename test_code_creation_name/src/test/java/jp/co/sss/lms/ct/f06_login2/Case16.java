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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA07");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA07");

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

		pageLoadTimeout(20);

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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		// 変更ボタンまでスクロール
		scrollBy("600");

		// 2.変更ボタンをクリックする
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='変更']")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		// 3.確認モーダルの変更ボタンをクリックする
		webDriver.findElement(
				By.id("upd-btn")).click();

		visibilityTimeout(By.cssSelector("span.help-inline.error"), 30);

		// 4. エラーメッセージ「パスワードの長さが最大値(20)を超えています。」と表示されている確認
		assertEquals("パスワードは必須です。\n"
				+ "「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。",
				webDriver
						.findElement(By.xpath("//input[@name='password']/following::span[contains(@class,'error')][1]"))
						.getText());

		assertEquals("現在のパスワードは必須です。", webDriver.findElement(
				By.xpath("//input[@name='currentPassword']/following::span[contains(@class,'error')][1]")).getText());

		// 5.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// 1.新しいパスワードのテキストボックスに「Aa0000000000000000000」と入力する
		webDriver.findElement(By.name("password")).sendKeys("Aa0000000000000000000");

		// 次へボタンまでスクロール
		scrollBy("600");

		// 2.変更ボタンをクリックする
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='変更']")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		// 3.確認モーダルの変更ボタンをクリックする
		webDriver.findElement(
				By.id("upd-btn")).click();

		visibilityTimeout(By.cssSelector("span.help-inline.error"), 30);

		// 4. エラーメッセージ「パスワードの長さが最大値(20)を超えています。」と表示されている確認
		assertEquals("パスワードの長さが最大値(20)を超えています。",
				webDriver.findElement(
						By.xpath("//input[@name='password']/following::span[contains(@class,'error')][1]")).getText());

		// 5.test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// 1.新しいパスワードのテキストボックスに「0000000000」と入力する
		webDriver.findElement(By.name("password")).sendKeys("0000000000");

		// 次へボタンまでスクロール
		scrollBy("600");

		// 2.変更ボタンをクリックする
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='変更']")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		// 3.確認モーダルの変更ボタンをクリックする
		webDriver.findElement(
				By.id("upd-btn")).click();

		visibilityTimeout(By.cssSelector("span.help-inline.error"), 30);

		// 4. エラーメッセージ「「パスワード」には半角英数字のみ使用可能です。」と表示されている確認
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。",
				webDriver
						.findElement(By.xpath("//input[@name='password']/following::span[contains(@class,'error')][1]"))
						.getText());

		// 5.test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		// 1.現在のパスワードのテキストボックスに「StudentAA02」と入力する
		webDriver.findElement(By.name("currentPassword")).sendKeys("StudentAA07");

		// 2.新しいパスワードのテキストボックスに「StudentAA2」と入力する
		webDriver.findElement(By.name("password")).sendKeys("StudentAA2");

		// 3.確認パスワードのテキストボックスに「StudentBB2」と入力する
		webDriver.findElement(By.name("passwordConfirm")).sendKeys("StudentBB2");

		// 次へボタンまでスクロール
		scrollBy("600");

		// 4.変更ボタンをクリックする
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='変更']")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		// 5.確認モーダルの変更ボタンをクリックする
		webDriver.findElement(
				By.id("upd-btn")).click();

		visibilityTimeout(By.cssSelector("span.help-inline.error"), 30);

		// 6. エラーメッセージ「「パスワード」には半角英数字のみ使用可能です。」と表示されている確認
		assertEquals("パスワードと確認パスワードが一致しません。",
				webDriver
						.findElement(By.xpath("//input[@name='password']/following::span[contains(@class,'error')][1]"))
						.getText());

		// 7.test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
