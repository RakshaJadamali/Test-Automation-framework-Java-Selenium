package com.utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LambdaTestUtility {

	    private static final String HUB_URL = "https://hub.lambdatest.com/wd/hub";
	    private static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	    private static ThreadLocal<DesiredCapabilities> capabilitiesLocal = new ThreadLocal<DesiredCapabilities>();
	    
	    public static WebDriver initializeLambdaTestSession(String browser, String testName)
	    {
	    	try {
	    		javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[] {
	    		new javax.net.ssl.X509TrustManager() {
	    		public java.security.cert.X509Certificate[] getAcceptedIssuers() { return new java.security.cert.X509Certificate[0]; }
	    		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
	    		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
	    		}
	    		};
	    		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
	    		sc.init(null, trustAll, new java.security.SecureRandom());
	    		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((h, s) -> true);
	    		} catch (Exception e) {
	    		e.printStackTrace();
	    		}

	    	
	    	DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("browserName", browser);
	        capabilities.setCapability("browserVersion", "latest");
	        Map<String, Object> ltOptions = new HashMap<>();
	        ltOptions.put("user", "raksjada");
	        ltOptions.put("accessKey", "LT_nlOFXi6qiePaAgXKs7GbZeO9HTxwvgNWfcA4jn802u2kyem");
	        ltOptions.put("build", "Selenium 4");
	        ltOptions.put("name", testName);
	        ltOptions.put("platformName", "Windows 10");
	        ltOptions.put("seCdp", true);
	        ltOptions.put("selenium_version", "latest");
	        capabilities.setCapability("LT:Options", ltOptions);
	        capabilitiesLocal.set(capabilities);
	        WebDriver driver=null;
			
	        try 
	        {
				driver = new RemoteWebDriver(new URL(HUB_URL), capabilitiesLocal.get());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	        driverLocal.set(driver); 

	        return driverLocal.get();
	    }
	    
	    public static void quitSession()
	    {
	    	if(driverLocal!=null)
	    		driverLocal.get().quit();
	    }

}
