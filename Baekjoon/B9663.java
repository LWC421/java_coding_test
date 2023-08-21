import java.util.*;
import java.io.*;

public class Main {

    static int[] queens;
    static int length;
    static int result;

    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        length = Integer.parseInt(in.readLine());
        queens = new int[length];
        result = 0;

        go(0);

        System.out.print(result);
    }

    public static void go(int y){
        if(y == length){
            //마지막까지 다 놓고 왔으면
            result++;
            return;
        }

        for(int x = 0; x < length; x++){
            //하나의 줄에 어떤 거 넣을 지
            if(check(y, x)){
                //놓을 수 있으면
                queens[y] = x;  //놓고
                go(y+1);
            }
        }
    }

    //[y, x]칸에 놓을 수 있는지 확인
    static boolean check (int y, int x){
        //처음 줄에 넣는 곳이면 무조건 가능
        if(y == 0) return true;

        int prevX;
        for(int prevY = 0; prevY < y; prevY++){
            prevX = queens[prevY];
            if(prevX == x) return false;                        //같은 열에 있는 경우
            if(Math.abs(prevX - x) == y - prevY) return false;  //대각선인 경우
        }

        return true;
    }
}
