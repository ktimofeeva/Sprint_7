import java.util.List;

public class OrderGenerator {

    public static Order getOrderDefault(List color){
        return new Order(
                "Иван",
                "Иванов",
                "Новодмитровская, 14",
                "Савеловская",
                "555555",
                10,
                "2022-06-06",
                "Сall in an hour",
                color);
    }
}
