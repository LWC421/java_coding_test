import java.io.*;
import java.util.*;

public class Main{

    // A0 = 1
    // Ai = Afloor(i / P) + Afloor(i / Q)

    static long target;       // N, [0 ~ 1e12]
    static long div1;        // P, [2 ~ 1e9]
    static long div2;        // Q, [2 ~ 1e9]

    static HashMap<Long, Long> dp;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("input.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine(), " ");

        target = Long.parseLong(st.nextToken());
        div1 = Long.parseLong(st.nextToken());
        div2 = Long.parseLong(st.nextToken());

        dp = new HashMap<>();

        long result = dp(target);
        System.out.println(result);
    }

    static long dp(long index){
        if(index == 0) return 1;

        if(dp.containsKey(index)){
            return dp.get(index);
        }
        else{
            long result = dp(index / div1) + dp(index / div2);
            dp.put(index, result);
            return result;
        }
    }
}