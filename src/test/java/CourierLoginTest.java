import io.restassured.response.ValidatableResponse;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

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
    public void courierCanBeLoginAndCheckResponse(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        assertNotNull(loginResponse.extract().path("id"));
    }

    @Test
    public void nonExistentCourierCannotLogIn(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");
        courierClient.delete(id);
        ValidatableResponse loginResponse2 = courierClient.login(CourierCredentials.from(courier));

        String message = loginResponse2.extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    public void courierWithoutPasswordCannotLogIn(){
        courier = CourierCenerator.getWithoutPassword();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        String message = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    public void courierWithoutLoginCannotLogIn(){
        courier = CourierCenerator.getWithoutLogin();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        String message = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    public void courierWithErrorInLoginCannotLogIn(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin()+"error", courier.getPassword()));

        String message = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    public void courierWithErrorInPasswordCannotLogIn(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()+"error"));

        String message = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }
}
