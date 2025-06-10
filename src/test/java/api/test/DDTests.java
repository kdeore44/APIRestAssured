package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	
	@Test(priority = 1,dataProvider = "testdata", dataProviderClass =DataProviders.class )
	public void testPostUser(String userID,String userName,String fName,String lName,String useremail,String Pwd,String Phone) 
	{
		
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstname(fName);
		userPayload.setLastname(lName);
		userPayload.setEmail(useremail);
		userPayload.setPassword(Pwd);
		userPayload.setPhone(Phone);
		
		Response response= UserEndPoints.createUser(userPayload);
		
		Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 201);
		
	}
	
	@Test(priority=2,dependsOnMethods = {"testPostUser"}, dataProvider = "getUserNames",dataProviderClass = DataProviders.class)
	public void testGetUserByUserName(String userName) {
		Response response= UserEndPoints.readUser(userName);
		System.out.println("GET before DELETE status:"+ response.statusCode());
		 Assert.assertEquals(response.getStatusCode(), 200, "User not found: " + userName);
		
	}
	
	@Test(priority = 3,dependsOnMethods = "testPostUser", dataProvider = "getUserNames",dataProviderClass = DataProviders.class)
	public void testDeleteUserbyUserName(String userName) {
		Response response =UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(),200);
		System.out.println("This Users are deleted:"+ userName);

		
	}

}
