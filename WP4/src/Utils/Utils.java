package Utils;

/**
 * This class contains some static methods to convert an array of bytes to a
 * String or a Hexadecimal String
 *
 * @author gparrella
 */
public class Utils {

    private static String digits = "0123456789abcdef";

    /**
     * This method converts an array of data into a String
     *
     * @param data the array of data to convert
     * @return the new converted string
     */
    public static String toString(byte[] data) {
        char[] chars = new char[data.length];

        for (int i = 0; i != chars.length; i++) {
            chars[i] = (char) (data[i] & 0xff);
        }

        return new String(chars);
    }

    /**
     * This method converts an array of bytes into an Hexadecimal String
     *
     * @param data the array of bytes to convert
     * @return a <code>String</code> representing the new converted hexadecimal
     * string
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

    /**
     * Appends different bytes array togheter
     *
     * @param args the different byte array to append
     * @return Returns appended byte array
     */
    public static byte[] append(byte[] ... args) {
        int totalLenght = 0;
        for(byte[] a  : args){
            totalLenght += a.length;
        }
        byte[] c = new byte[totalLenght];
        int index = 0;
        for(byte[] a  : args){
            System.arraycopy(a, 0, c, index, a.length);
            index += a.length;
        }
        return c;
    }
}
