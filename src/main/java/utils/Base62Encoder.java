package utils;

public class Base62Encoder {

    private static final String BASE62 =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int autoIncrement = 1;

    public  String encode(String input) {

        long number = Math.abs(autoIncrement++);

        return encodeNumber(number);
    }

    private static String encodeNumber(long number) {

        if (number == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (number > 0) {

            int remainder = (int) (number % 62);

            sb.append(BASE62.charAt(remainder));

            number /= 62;
        }

        return sb.reverse().toString();
    }
}