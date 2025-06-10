package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	String filePath = System.getProperty("user.dir") + "//testData//testdata.xlsx";

	// Returns all columns for each user
	@DataProvider(name = "testdata")
	public String[][] getAllData() throws Exception {
		XLUtility xl = new XLUtility(filePath);

		int totalRows = xl.getRowCount("Sheet1");
		int totalCols = xl.getCellCount("Sheet1", 0); // Read header to get column count

		String[][] data = new String[totalRows - 1][totalCols]; // Skip header

		for (int i = 0; i < totalRows - 1; i++) {
			for (int j = 0; j < totalCols; j++) {
				data[i][j] = xl.getCellData("Sheet1", i + 1, j); // Read from row 1 onwards
			}
		}

		return data;
	}

	// Returns only usernames as a single-column data provider
	@DataProvider(name = "getUserNames")
	public Object[][] getUserNames() throws Exception {
		XLUtility xl = new XLUtility(filePath);

		int totalRows = xl.getRowCount("Sheet1");

		Object[][] userNames = new Object[totalRows - 1][1];

		for (int i = 0; i < totalRows - 1; i++) {
			System.out.println("Loaded username: " + xl.getCellData("Sheet1", i + 1, 1).trim()); // DEBUG
			
			userNames[i][0] = xl.getCellData("Sheet1", i + 1, 1).trim(); // Column 1 = UserName
		}

		return userNames;
	}
}
