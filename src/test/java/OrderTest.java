import io.restassured.response.ValidatableResponse;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.junit.runners.Parameterized;
        import java.util.List;
        import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderTest {

    private List<String> color;
    private Order order;
    private OrderClient orderClient;

    public OrderTest(List<String> color){
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}") // добавили аннотацию
    public static Object[][] getSumData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK","GREY")},
                {null},
        };
    }

    @Test
    public void orderCanBeCreated(){
        order = OrderGenerator.getOrderDefault(color);
        orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);

        assertNotNull(response.extract().path("track"));

    }
}
