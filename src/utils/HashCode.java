package utils;

public class HashCode {
    public static int hash(String str) {
        return str.hashCode();
    }

    public static void main(String[] args) {
        System.out.println(hash("ivan"));
    }
}
