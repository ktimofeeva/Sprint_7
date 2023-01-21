import io.restassured.response.ValidatableResponse;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import static org.apache.http.HttpStatus.*;
        import static org.junit.Assert.assertEquals;

public class CourierCreateTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courier = CourierCenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        if (id != 0)
            courierClient.delete(id);
    }

    @Test
    public void courierCanBeCreatedAndCheckStatusCode() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = response.extract().statusCode();

        assertEquals(SC_CREATED, statusCode);
    }

    @Test
    public void twoIdenticalCouriersCannotBeCreated() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = response.extract().statusCode();

        assertEquals(SC_CONFLICT, statusCode);
    }


    @Test
    public void courierCanBeCreatedAndCheckResponse() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        String body = response.extract().asString();

        assertEquals("{\"ok\":true}", body);
    }

    @Test
    public void courierWithoutLoginCannotBeCreated() {
        courier = CourierCenerator.getWithoutLogin();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void courierWithoutPasswordCannotBeCreated() {
        courier = CourierCenerator.getWithoutPassword();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
}
