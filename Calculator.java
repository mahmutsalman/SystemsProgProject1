import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    public String decimalToBinary(BigDecimal decimal) {
        return decimalToBinary(decimal, 50);
    }

    public String decimalToBinary(BigDecimal decimal, int digits) {
        BigDecimal integer = decimal.setScale(0, RoundingMode.FLOOR);
        BigDecimal fractional = decimal.subtract(integer);

        StringBuilder sb = new StringBuilder();

        //  Integer part
        BigDecimal two = BigDecimal.valueOf(2);
        BigDecimal zero = BigDecimal.ZERO;
        while ( integer.compareTo(zero) > 0 ) {
            BigDecimal[] result = integer.divideAndRemainder(two);
            sb.append(result[1]);
            integer = result[0];
        }
        sb.reverse();

        //  Fractional part
        int count = 0;
        if ( fractional.compareTo(zero) != 0 ) {
            sb.append(".");
        }
        while ( fractional.compareTo(zero) != 0 ) {
            count++;
            fractional = fractional.multiply(two);
            sb.append(fractional.setScale(0, RoundingMode.FLOOR));
            if ( fractional.compareTo(BigDecimal.ONE) >= 0 ) {
                fractional = fractional.subtract(BigDecimal.ONE);
            }
            if ( count >= digits ) {
                break;
            }
        }

        return sb.toString();
    }

    public String[] normalizer(String s) { // takes input example: 1001.1001 and converts into 1.0011001,
        // and returns it and its exponent

        String newNumber = "";
        String exp = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(i);
                sb.insert(1, ".");
                exp = String.valueOf(i-1);
                newNumber = sb.toString();
            }
        }

        return new String[] {newNumber, exp};
    }

    public String rounder(String s) {
        return "s";
    }
}
