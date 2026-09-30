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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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

	private WebElement getStartHourElement() {
		return webDriver.findElement(By.id("startHour0"));
	}

	private WebElement getStartMinuteElement() {
		return webDriver.findElement(By.id("startMinute0"));
	}

	private WebElement getEndHourElement() {
		return webDriver.findElement(By.id("endHour0"));
	}

	private WebElement getEndMinuteElement() {
		return webDriver.findElement(By.id("endMinute0"));
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

		// 5.test3のエビデンスを取得する
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

		// 5.test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// 1.出勤時間の時間を09を選択する
		Select startHourSelect = new Select(getStartHourElement());
		startHourSelect.selectByVisibleText("09");

		// 2.退勤時間の分を00を選択する
		Select endMinuteselect = new Select(getEndMinuteElement());
		endMinuteselect.selectByVisibleText("00");

		scrollBy("600");

		// 3.更新ボタンを押下
		webDriver.findElement(By.className("update-button")).click();

		// 4.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 5.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 30);

		// 6.エラーメッセージが表示される
		String startErrorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 出勤時間が正しく入力されていません。", startErrorMessage);

		// 7.エラーメッセージが表示される
		String endErrorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 出勤時間が正しく入力されていません。", endErrorMessage);

		// 8..test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// 1.出勤時間の時間を空文字を選択する
		Select startHourSelect = new Select(getStartHourElement());
		startHourSelect.selectByVisibleText("");

		// 2.退勤時間の時を18を選択する
		WebElement endHourElement = getEndHourElement();
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", endHourElement);
		Select endHourSelect = new Select(endHourElement);
		endHourSelect.selectByVisibleText("18");

		scrollBy("600");

		// 3.更新ボタンを押下
		webDriver.findElement(By.className("update-button")).click();

		// 4.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 5.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 30);

		// 6.エラーメッセージが表示される
		String errorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errorMessage);

		// 7.test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		// 1. 出勤時間の時を19を選択する
		WebElement startHourElement = getStartHourElement();
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", startHourElement);
		Select startHourSelect = new Select(startHourElement);
		startHourSelect.selectByVisibleText("19");

		// 2.出勤時間の分を00を選択する
		Select startMinuteSelect = new Select(getStartMinuteElement());
		startMinuteSelect.selectByVisibleText("00");

		scrollBy("600");

		// 3.更新ボタンを押下
		webDriver.findElement(By.className("update-button")).click();

		// 4.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 5.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 30);

		// 6.エラーメッセージが表示される
		String errorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", errorMessage);

		// 7.test3のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		// 1.出勤時間の時間を17を選択する
		Select startHourSelect = new Select(getStartHourElement());
		startHourSelect.selectByVisibleText("17");

		// 2.中抜け時間を3時間を選択する
		WebElement blankTimeElement = webDriver
				.findElement(By.cssSelector("select[name='attendanceList[0].blankTime']"));

		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", blankTimeElement);

		Select blankTimeSelect = new Select(blankTimeElement);

		blankTimeSelect.selectByVisibleText("3時間");

		scrollBy("600");

		// 3.更新ボタンを押下
		webDriver.findElement(By.className("update-button")).click();

		// 4.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 5.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 30);

		// 6.エラーメッセージが表示される
		String errorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 中抜け時間が勤務時間を超えています。", errorMessage);

		// 7..test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		// 1. 備考欄に「あ」と100文字打ち込む
		String text101 = "あ".repeat(101);
		int i = 0;
		WebElement noteInput = webDriver.findElement(By.cssSelector("input[name='attendanceList[" + i + "].note']"));
		noteInput.sendKeys(text101);

		// 2.中抜け時間をクリアにする
		WebElement blankTimeElement = webDriver
				.findElement(By.cssSelector("select[name='attendanceList[0].blankTime']"));

		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", blankTimeElement);

		Select blankTimeSelect = new Select(blankTimeElement);

		blankTimeSelect.selectByVisibleText("");

		scrollBy("600");

		// 3.更新ボタンを押下
		webDriver.findElement(By.className("update-button")).click();

		// 4.確認ダイアログのOKボタンを押下する
		webDriver.switchTo().alert().accept();

		// 5.h2タグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 60);

		// 6.エラーメッセージが表示される
		String errorMessage = webDriver.findElement(By.className("help-inline")).getText();
		assertEquals("* 備考の長さが最大値(100)を超えています。", errorMessage);

		// 7..test3のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

}
