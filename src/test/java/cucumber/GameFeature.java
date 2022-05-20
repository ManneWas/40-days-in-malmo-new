package cucumber;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class GameFeature {
	@Given("I exist")
	public void iExist()
	{
		Assertions.assertTrue(true);
	}

	@When("I run this test")
	public void iRunThisTest()
	{
		System.out.println("I exist!");


	}

	@Then("It works")
	public void itWorks()
	{
		Assertions.assertTrue(true);
	}
}
