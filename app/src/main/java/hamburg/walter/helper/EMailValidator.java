package hamburg.walter.helper;

public class EMailValidator {
    public static boolean isValid(String email) {
        return email.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}");
    }
}
