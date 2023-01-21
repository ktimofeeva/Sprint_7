import org.apache.commons.lang3.RandomStringUtils;

public class CourierCenerator {

    public static Courier getDefault(){
        final String login = RandomStringUtils.randomAlphabetic(5);
        final String password = RandomStringUtils.randomAlphabetic(5);
        final String firstName = RandomStringUtils.randomAlphabetic(5);

        return new Courier(login, password,firstName);
    }

    public static Courier getWithoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(5);
        final String firstName = RandomStringUtils.randomAlphabetic(5);

        return new Courier(login,null,firstName);
    }

    public static Courier getWithoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(5);
        final String firstName = RandomStringUtils.randomAlphabetic(5);

        return new Courier(null,password, firstName);
    }
}
