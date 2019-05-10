/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.opencsv.CSVReader;

import driver.ActionClass;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * @author deepa This class has all the general utility methods used in the
 *         project
 */
public class GeneralUtilities extends ActionClass {

	public GeneralUtilities() throws Exception {

		// super();
		// TODO Auto-generated constructor stub
	}

	public void SwtichTab() throws Exception {
		Set<String> handles = driver.getWindowHandles();
		String currentHandle = driver.getWindowHandle();

		System.out.println(currentHandle);

		for (String handle : handles) {
			Thread.sleep(5000);

			// System.out.println(handle);

			if (!handle.equals(currentHandle)) {
				driver.switchTo().window(handle);
				System.out.println(handle);
			}
		}

	}

	public void ClosingTheBrowserPopUp() throws Exception {
		Set<String> handles = driver.getWindowHandles();
		String currentHandle = driver.getWindowHandle();

		System.out.println(currentHandle);

		for (String handle : handles) {
			Thread.sleep(5000);

			// System.out.println(handle);

			if (!handle.equals(currentHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}

		}

		driver.switchTo().window(currentHandle);

	}

	public WebElement SelectTrack(String track) {
		WebElement TrackSelected = null;

		List<WebElement> liEle = driver.findElements(By.xpath("//ul[@class='listtolist_ui']/li"));

		for (int i = 0; i < liEle.size(); i++) {
			if (liEle.get(i).getText().equals(track)) {
				TrackSelected = liEle.get(i);
				break;

			}

		}

		return TrackSelected;

	}

	public void FullPageScreenShot(String path, String filename) throws Exception {
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);

		ImageIO.write(fpScreenshot.getImage(), "PNG", new File(path + filename));

	}

	public void SettingDownloadFolder(String downloadFilepath) {
		// String downloadFilepath =
		// "C:\\Users\\deepa\\eclipse-workspace\\SophiePMTestAutomation2\\AutoITScripts";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		//driver = new ChromeDriver(options);
	}

	public File getLatestFileFromDir(String dirPath, String pattern) {
		File dir = new File(dirPath);

		// File[] files = dir.listFiles();
		File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith(pattern)) {
					return true;
				}
				// TODO Auto-generated method stub
				return false;
			}
		});

		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];

				System.out.println(lastModifiedFile);
			}
		}
		return lastModifiedFile;

	}

	public int toGetTheNumberOfFieldsInCSV(File file) throws Exception {
		int CsvColumnCount = 0;
		CSVReader csvReader = new CSVReader(new FileReader(file));

		String[] header = csvReader.readNext();
		try {
			csvReader.close();

		} catch (Exception e) {

			System.out.println("reader didnt close");

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(10000);

		if (header != null) {
			CsvColumnCount = header.length;
		}

		return CsvColumnCount;
	}

	// Below method copy the file from the source directory to destination directory
	public boolean CopyFile(File src, File dest) {
		boolean flag = false;
		try {

			FileUtils.moveFileToDirectory(src, dest, false);
			flag = true;
			// src.renameTo(dest);
		} catch (Exception e) {
			flag = false;
			System.out.println("File move failed");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

}