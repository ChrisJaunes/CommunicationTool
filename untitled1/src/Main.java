import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String deal(String ta, int len1, int len2) {
        StringBuilder tbb = new StringBuilder();
        for(int i = 0; i < len1; i++) {
            tbb.append(ta.charAt(i));
        }
        for(int i = len2-1; i >= 0; i--) {
            tbb.append(ta.charAt(i));
        }
        String tb = tbb.toString();
//        System.out.println(tb);//
        return tb;
    }
    public static void main(String[] argv) {
        Scanner cin = new Scanner(System.in);
        int z = cin.nextInt();
        while(z > 0) {
            z = z - 1;
            BigInteger a = cin.nextBigInteger();
            List<String> ans = new ArrayList<>();
            while(! a.equals(new BigInteger("0")) ) {
                //System.out.println("+" + a.toString());
                String ta = a.toString();
                int len1 = (ta.length() + 1) / 2;
                int len2 = (ta.length()) / 2;
                String taa = ta.substring(0, len1);
                String tb = deal(taa, len1, len2);
                if(ta.compareTo(tb) < 0) {
                    BigInteger b = new BigInteger(taa);
                    taa = b.add(new BigInteger("-1")).toString();
                    if (taa.length() < len1 || "0".equals(taa)) {
                        tb = "9".repeat(Math.max(0, ta.length() - 1));
                    } else {
                        tb = deal(taa, len1, len2);
                    }
                }
                ans.add(tb);
//                System.out.println(tb);
                a = a.add(new BigInteger(tb).negate());
            }
            System.out.println(ans.size());
            for(String s : ans) {
                System.out.println(s);
            }
        }
    }
}
