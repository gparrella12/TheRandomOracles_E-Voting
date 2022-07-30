package src;

/**
 *
 * @author gparrella
 */
public class Utils {

    private static String digits = "0123456789abcdef";

    /**
     * This method converts an array of bytes into a String
     * @param bytes
     * @return the new converted string
     */
    public static String toString(byte[] bytes) {
        char[] chars = new char[bytes.length];

        for (int i = 0; i != chars.length; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }

        return new String(chars);
    }

    /**
     * This method converts an array of bytes into an Hexadecimal String
     * @param data
     * @return the new converted string
     */
    public static String toHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i != data.length; i++) {
            int v = data[i] & 0xff;
            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
        }
        return buf.toString();
    }
}
