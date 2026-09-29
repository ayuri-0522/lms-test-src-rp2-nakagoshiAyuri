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
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA03");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA03");

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		// 次へボタンまでスクロール
		scrollBy("1200");

		// 1.次へボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(20);

		// 4. 利用規約詳細画面が遷移した（タイトルが変わったか）ことの確認
		assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());

		// エラーメッセージまでスクロール
		scrollBy("1200");

		// 3.「セキュリティ規約への同意は必須です。」とエラーメッセージが表示されているか確認
		assertEquals("セキュリティ規約への同意は必須です。", webDriver.findElement(By.className("error")).getText());

		// 4.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
