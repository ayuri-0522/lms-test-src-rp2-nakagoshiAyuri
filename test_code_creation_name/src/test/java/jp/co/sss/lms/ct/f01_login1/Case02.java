package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author 中越愛百合
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		webDriver.get("http://localhost:8080/lms");

		//指定したURLと一致しているか確認する
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		// 1. ログイン画面を開く
		webDriver.get("http://localhost:8080/lms");

		// 2. 誤った情報を入力
		webDriver.findElement(By.id("loginId")).sendKeys("student00");
		webDriver.findElement(By.id("password")).sendKeys("student00");

		// 3. ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		// 4. 検証：エラーメッセージが表示されているか
		WebElement errorMsg = webDriver.findElement(By.className("help-inline"));

		// メッセージが画面に表示されているか（CSS等で隠れていないか）
		assertTrue(errorMsg.isDisplayed());

		// 検証：画面が遷移していない（タイトルが変わっていない）ことの確認
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//test2のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
