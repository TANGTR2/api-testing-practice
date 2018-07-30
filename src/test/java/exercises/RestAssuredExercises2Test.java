package exercises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class RestAssuredExercises2Test {

	private static RequestSpecification requestSpec;

	@BeforeAll
	public static void createRequestSpecification() {

		requestSpec = new RequestSpecBuilder().
			setBaseUri("http://localhost").
			setPort(9876).
			setBasePath("/api/f1").
			build();
	}
	
	/*******************************************************
	 * Use junit-jupiter-params for @ParameterizedTest that
	 * specifies in which country
	 * a specific circuit can be found (specify that Monza 
	 * is in Italy, for example) 
	 ******************************************************/

	//todo

	static Stream<Arguments> circuitCountryProvider() {
		return Stream.of(
				Arguments.of("monza", "Italy"),
				Arguments.of("spa", "Belgium")
		);
	}

	@ParameterizedTest
	@MethodSource("circuitCountryProvider")
	public void checkCountryForCircuit(String circuitId, String country) {

		given().
				spec(requestSpec).
				pathParam("circuit", circuitId).
				when().
				get("circuits/{circuit}.json").
				then().log().all().
				assertThat().
				body("MRData.CircuitTable.Circuits[0].Location.country", equalTo(country));
	}

	/*******************************************************
	 * Use junit-jupiter-params for @ParameterizedTest that specifies for all races
	 * (adding the first four suffices) in 2015 how many  
	 * pit stops Max Verstappen made
	 * (race 1 = 1 pitstop, 2 = 3, 3 = 2, 4 = 2)
	 ******************************************************/

	//todo
	static Stream<Arguments> racePitstopProvider() {
		return Stream.of(
				Arguments.of("1", 1),
				Arguments.of("2", 3),
				Arguments.of("3", 2),
				Arguments.of("4", 2)
		);
	}

	@ParameterizedTest
	@MethodSource("racePitstopProvider")
	public void checkNumberOfPitstopsForMaxVerstappenIn2015(String round, Integer pitstops) {

		given().
				spec(requestSpec).
				pathParam("round", round).
				when().
				get("/2015/{round}/drivers/max_verstappen/pitstops.json").
				then().log().all().
				assertThat().
				body("MRData.RaceTable.Races[0].PitStops.size()", equalTo(pitstops));
	}


	/*******************************************************
	 * Request data for a specific circuit (for Monza this 
	 * is /circuits/monza.json)
	 * and check the country this circuit can be found in
	 ******************************************************/
	
//	@Test
//	public void checkCountryForCircuit() {
//
//		given().
//			spec(requestSpec).
//				when().
//				then();
//	}
	
	/*******************************************************
	 * Request the pitstop data for the first four races in
	 * 2015 for Max Verstappen (for race 1 this is
	 * /2015/1/drivers/max_verstappen/pitstops.json)
	 * and verify the number of pit stops made
	 ******************************************************/
	
//	@Test
//	public void checkNumberOfPitstopsForMaxVerstappenIn2015() {
//
//		given().
//			spec(requestSpec).
//		when().
//		then();
//	}
}