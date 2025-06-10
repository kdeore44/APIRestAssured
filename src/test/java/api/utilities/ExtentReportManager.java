package api.utilities;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	
	    public static ExtentReports extent;
	    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	    @Override
	    public void onStart(ITestContext context) {
	        String timeStamp = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
	        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timeStamp + ".html";

	        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
	        sparkReporter.config().setDocumentTitle("API Automation Report");
	        sparkReporter.config().setReportName("REST API Test Results");
	        sparkReporter.config().setTheme(Theme.STANDARD);

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);
	        extent.setSystemInfo("Environment", "QA");
	        extent.setSystemInfo("Tester", "Kalpesh D");
	    }


	    @Override
	    public void onTestStart(ITestResult result) {
	        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
	        testThread.set(test);
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        testThread.get().log(Status.PASS, "Test Passed");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	    	String screenshotPath = "path/to/screenshot.png";
	    	testThread.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");

	       // testThread.get().log(Status.FAIL, "Test Failed");
	        //testThread.get().log(Status.FAIL, result.getThrowable());
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        testThread.get().log(Status.SKIP, "Test Skipped");
	        testThread.get().log(Status.SKIP, result.getThrowable());
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	        extent.flush();
	    
	}
}



