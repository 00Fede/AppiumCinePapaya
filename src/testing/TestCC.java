package testing;

import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class TestCC {

	private static final String PACK = "com.cinepapaya.cinecolombia";

	public static AndroidDriver<WebElement> driver;

	DesiredCapabilities capabilities = new DesiredCapabilities();

	@BeforeMethod
	public void setUpAppium() throws MalformedURLException, InterruptedException {
		/*
		 * APK INFO:com.google.android.calculator2.Calculator
		 * com.google.android.calculator2.CalculatorSecurity
		 */
		String packagename = PACK;
		String URL = "http://127.0.0.1:4723/wd/hub";
		String activityname = PACK + ".activity.SplashScreenActivity"; // Entra
																		// a
																		// actividad
																		// de
																		// login
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "name");
		capabilities.setCapability("udid", "ZY222TQCN4");
		capabilities.setCapability("platformVersion", "6.0");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", packagename);
		capabilities.setCapability("appActivity", activityname);
		driver = new AndroidDriver<WebElement>(new URL(URL), capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@AfterTest
	public void CleanUpAppium() {

		driver.quit();
	}

	/**
	 * Valida si un elemento {@code elementName} esta visible en la aplicación
	 * manejada por el driver {@code driver} en un lapso de tiempo igual o menor
	 * que {@code timeout}. Retorna {@code true} si se cumple la condición de
	 * visibilidad de {@code elementName} dentro del tiempo {@code timeout},
	 * {@code false} si {@code wait.until} lanza alguna excepción como
	 * TimeoutException.
	 * 
	 * @param driver
	 * @param elementName
	 * @param timeout
	 * @return
	 */
	public boolean isElementPresent(AndroidDriver<WebElement> driver, WebElement elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Test
	public void mytest() throws InterruptedException {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.Button")));

			Thread.sleep(5000); // espera del splash
			// en
			// com.cinepapaya.cinecolombia.user.activity.ChooseFavoriteTheatersActivity
			driver.findElementByClassName("android.widget.Button").click();
			// click en boton de navbar
			driver.findElementByClassName("android.widget.ImageButton").click();
			// click en boton mi cuenta
			driver.findElementById(PACK + ":id/tviMyAccount").click();
			// Escribe nombre de usuario
			driver.findElementById(PACK + ":id/eteEmail").sendKeys("feedkiko@gmail.com");
			// Escribe contrasena
			driver.findElementById(PACK + ":id/etePassword").sendKeys("42767283");
			driver.hideKeyboard();
			// Ingresa la cuenta
			driver.findElementById(PACK + ":id/btnLogin").click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PACK + ":id/iviMovie")));

			driver.swipe(360, 1100, 360, 9, 8000);

			// selecciona una pelicula
			driver.findElementById(PACK + ":id/iviMovie").click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PACK + ":id/llaDateMovieTime")));

			WebElement fecha = driver.findElement(By.id(PACK + ":id/hscDates"));

			System.out.println("fecha: " + fecha.getLocation());
			WebElement fechaLin = fecha.findElement(By.id(PACK + ":id/llaDates"));
			//TODO: Swipe horizontally
			System.out.println("fechalin: " + fechaLin.getLocation());
			List<WebElement> fechaLinLins = fechaLin.findElements(By.className("android.widget.LinearLayout"));
			Iterator<WebElement> i = fechaLinLins.iterator();
			while (i.hasNext()) {
				System.out.println("fechaLinLin:" + i.next().getLocation());
			}

			Random rand = new Random();
			// click en una de las fechas
			fechaLinLins.get(rand.nextInt(fechaLinLins.size())).click();

			// lista de teatros disponibles
			WebElement teatros = driver.findElement(By.id(PACK + ":id/rviMovieTimes"));
			// TODO: Swipe vertically
			List<WebElement> teatrosList = teatros.findElements(By.className("android.widget.LinearLayout"));
			Iterator<WebElement> iter = teatrosList.iterator();
			while (iter.hasNext()) {
				System.out.println("teatrosList:" + iter.next().getLocation());
			}
			// click en uno de los teatros (random)
			teatrosList.get(rand.nextInt(teatrosList.size())).click();

			WebElement tiempos = driver.findElement(By.id(PACK + ":id/gviMovieTimes"));
			List<WebElement> tiemposList = tiempos.findElements(By.className("android.widget.TextView"));
			Iterator<WebElement> iter2 = tiemposList.iterator();
			while (iter2.hasNext()) {
				System.out.println("tiemposList:" + iter2.next().getLocation());
			}
			

			do {
				// Click en uno de los tiempos, es posible que el tiempo no este disponible
				System.out.println("Intento de selección de tiempo.");
				tiemposList.get(rand.nextInt(tiemposList.size())).click();
			} while (!isElementPresent(driver, driver.findElement(By.id(PACK + ":id/btnNext")), 15));
			System.out.println("Tiempo de teatro seleccionado");

			List<WebElement> masboletas = driver.findElements(By.id(PACK + ":id/iviPriceButtomPlus"));
			int numboletas = 0;
			// valida si se encuentran los botones para adicionar boletas
			if (masboletas.size() > 0) {
				
				WebElement masboleta = masboletas.get(rand.nextInt(masboletas.size()));
				// Para pruebas mas rapidas
				masboleta = masboletas.get(0);
				numboletas = rand.nextInt(5)+1; //mas 1 para evitar cero boletas
				System.out.println("Se agregaron "+numboletas+ " boletas");
				for (int j = 0; j < numboletas; j++)
					masboleta.click();
			}

			// click para escoger silla

			driver.findElement(By.id(PACK + ":id/btnNext")).click();

			// Espera hasta que aparezca mapa de sillas
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PACK + ":id/customMapView")));

			WebElement mapa_sillas = driver.findElement(By.id(PACK+":id/customMapView"));
			Point loc_mapa = mapa_sillas.getLocation();
			Dimension dimension_mapa = mapa_sillas.getSize();
			int ancho_mapa = dimension_mapa.getWidth();
			int alto_mapa = dimension_mapa.getHeight();
			
			System.out.println("Location de mapa_sillas " + loc_mapa);
			System.out.println("Tamaño de mapa_sillas " + dimension_mapa);
			
			TouchAction touchAction = new TouchAction(driver);
			int x = loc_mapa.getX()+ancho_mapa;
			int y = loc_mapa.getY()+(alto_mapa); //toma 40% de la pantalla para arriba
			System.out.println("coordenadas usada para seleccionar sillas: x = "+x+
					" y = "+y);
			
			
			// selecciona sillas y valida si se seleccionaron todas correctamente
			do {
				if(driver.findElements(By.id(PACK+":id/content")).size() > 0)
					driver.findElement(By.id(PACK + ":id/buttonDefaultPositive")).click();
				
				for (int j = 0; j < numboletas; j++) {
					// selecciona numboletas sillas
					// Forma lenta, automatiza para todo tipo de sala, demora para escoger silla
//					int rand_x = ThreadLocalRandom.current().nextInt(loc_mapa.getX(), x);
//					int rand_y = ThreadLocalRandom.current().nextInt(loc_mapa.getY(), y);
					
					//selecciona silla desde 1/4 de x hasta todo el ancho
					// y desde y_ini hasta 1/5 del alto del view
					int[] p_limx = {(int) (loc_mapa.getX()+ancho_mapa*0.4), x};
					int[] p_limy = {loc_mapa.getY(), (int)(loc_mapa.getY()+(alto_mapa)*0.2)};
					int rand_x = ThreadLocalRandom.current().nextInt(p_limx[0], p_limx[1]);
					int rand_y = ThreadLocalRandom.current().nextInt(p_limy[0], p_limy[1]);
					System.out.println("Silla "+(j+1)+" seleccionada "+"("+rand_x+","+rand_y+")");
					driver.performTouchAction(new TouchAction(driver).tap(rand_x,rand_y));
				}
				
				driver.findElement(By.id(PACK+":id/btnNext")).click();
			} while ((driver.findElements(By.id(PACK + ":id/content")).size() > 0
					&& driver.findElements(By.id(PACK+":id/buttonDefaultPositive")).size() > 0)
					|| driver.findElements(By.id(PACK + ":id/customMapView")).size()>0);
			
			System.out.println(numboletas + " fueron escogidas correctamente");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PACK + ":id/rbuPaymentMethod")));

			List<WebElement> formas_pago = driver.findElements(By.id(PACK + ":id/tviPaymentName"));
			for (WebElement webElement : formas_pago) {
				System.out.println("Forma de pago disponible: " + webElement.getText());
			}
			driver.navigate().back(); // mensaje para salir proceso de compra
			
			driver.findElement(By.id(PACK + ":id/buttonDefaultPositive")).click(); // cancela
																					// proceso
																					// de
																					// compra
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PACK + ":id/tviTotalAmount")));

			driver.navigate().back();
			driver.navigate().back(); // retornar a newhomeactivity

			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.ImageButton")));
			// click en navvar
			driver.findElement(By.className("android.widget.ImageButton")).click();

			// click en boton mi cuenta
			driver.findElementById(PACK + ":id/tviMyAccount").click();
			// clic en boton logout
			driver.findElementById(PACK + ":id/btnLogout").click();
			// confirmar el logout
			driver.findElementById("android:id/button1").click();

			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.print("\n\t Se presento Excepción " + e);
		}
	}
}

