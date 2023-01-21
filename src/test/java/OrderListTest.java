import io.restassured.response.ValidatableResponse;
        import org.junit.Test;
        import static org.junit.Assert.assertNotNull;

public class OrderListTest {

    @Test
    public void orderListCanBeObtained(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.orderList();

        assertNotNull(response.extract().path("orders.id"));
    }
}
