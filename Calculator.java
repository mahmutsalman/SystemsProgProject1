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

        //  Integer part. Convert to binary.
        BigDecimal two = BigDecimal.valueOf(2);
        BigDecimal zero = BigDecimal.ZERO;
        while ( integer.compareTo(zero) > 0 ) {
            //two -> divisor, result[0] = result, result[1] = remainder
            BigDecimal[] result = integer.divideAndRemainder(two);
            sb.append(result[1]);
            integer = result[0];
        }
        sb.reverse();
        //  Fractional part
        int count = 0;
        //If it has fraction, add point
        if ( fractional.compareTo(zero) != 0 ) {
            sb.append(".");
        }
        while ( fractional.compareTo(zero) != 0 ) {
            count++;
            fractional = fractional.multiply(two);
            //if fraction is equal or greater than 0.5, than add a 1. if not add 0.
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

    public String[] normalizer(String s) {
        // takes input example: 1001.1001 and converts into 1.0011001,
        // and returns it and its exponent

        String newNumber = "";
        String E = "";
        if (!s.contains(".")) {
            StringBuilder sb = new StringBuilder(s);
            //TODO remove 0's from the end to the beginning
            int k = 0;
            while(sb.charAt(sb.length() - 2) == 0 && sb.length() != 1) {
                sb.deleteCharAt(sb.length() - 1);
                k++;
            }
            sb.insert(1, ".");
            //E holds the value how many times we shifted the point to the left
            E = Integer.toString(s.length() - 1);
            newNumber = sb.toString();
        }
        else {
            if (s.charAt(0) == '.') {
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(0);
                s = sb.toString();
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '1') {

                        E = Integer.toString(-i-1);
                        sb.insert(i, ".");
                        sb.delete(0, i);
                        //E holds the value how many times we shifted the point to the left
                        newNumber = sb.toString();
                        break;
                    }
                }
            }
            else {
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '.') {
                        StringBuilder sb = new StringBuilder(s);
                        sb.deleteCharAt(i);
                        sb.insert(1, ".");
                        //E holds the value how many times we shifted the point to the left
                        E = String.valueOf(i - 1);
                        newNumber = sb.toString();
                    }
                }
            }

        }
        return new String[] {newNumber, E};
    }


}
