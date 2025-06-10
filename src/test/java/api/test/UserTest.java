package api.test;

import java.lang.System.Logger;
import java.util.logging.LogManager;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Log;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker ;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		System.out.println("TestPost Method started");
		
		Response response=UserEndPoints.createUser(userPayload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("TestPost Method ended");
	}
	
	@Test(priority = 2,dependsOnMethods = {"testPostUser"})
	public void testGetUserByName() {
		System.out.println("TestGet Method started");
		 System.out.println("Trying to fetch user: " + userPayload.getUsername());
		 
		Response response= UserEndPoints.readUser(userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		System.out.println("TestGet Method ended");
				
	}
	
	@Test(priority = 3)
	public void testUpdateUserbyName() {
		System.out.println("TestUpdate Method started");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response= UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload );
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// Checking updated user information
		Response responseAfterUpdate= UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		System.out.println("TestUpdate Method ended");
		
	}
	
	@Test(priority = 4)
	
	public void testDeleteUserbyName() {
		System.out.println("TestDelete Method started");
		
		Response response= UserEndPoints.deleteUser(this.userPayload.getUsername());
			Assert.assertEquals(response.getStatusCode(), 200);
			System.out.println("TestDelete Method ended");
	}
	
}
