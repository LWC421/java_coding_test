import java.io.*;
import java.util.*;

public class Main{

    final static int MAX_VALUE = 100_000 * 10;

    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(in.readLine());
        int[][] numbers = new int[length][3];
        int[][] maxs = new int[length][3];
        int[][] mins = new int[length][3];

        for(int y = 0; y < length; y++){
            String[] line = in.readLine().split( " ");
            for(int x = 0; x < 3; x++){
                numbers[y][x] = Integer.parseInt(line[x]);
                mins[y][x] = MAX_VALUE;
            }
        }
        //입력 종료

        //첫번재 줄은 입력값으로 초기화
        for(int x = 0; x < 3; x++){
            maxs[0][x] = numbers[0][x];
            mins[0][x] = numbers[0][x];
        }

        //내려가기
        int prev;
        for(int y = 1; y < length; y++){
            prev = y - 1;

            maxs[y][0] = numbers[y][0] + max(maxs[prev][0], maxs[prev][1]);
            maxs[y][1] = numbers[y][1] + max(maxs[prev][0], maxs[prev][1], maxs[prev][2]);
            maxs[y][2] = numbers[y][2] + max(maxs[prev][1], maxs[prev][2]);

            mins[y][0] = numbers[y][0] + min(mins[prev][0], mins[prev][1]);
            mins[y][1] = numbers[y][1] + min(mins[prev][0], mins[prev][1], mins[prev][2]);
            mins[y][2] = numbers[y][2] + min(mins[prev][1], mins[prev][2]);
        }
        //종료

        StringBuilder sb = new StringBuilder("");
        int last = length - 1;
        sb.append(max(maxs[last][0], maxs[last][1], maxs[last][2])).append(" ").append(min(mins[last][0], mins[last][1], mins[last][2]));
        System.out.println(sb.toString());

    }

    public static int max(int ...row){
        int max = 0;
        for(int num: row){
            if(num > max){
                max = num;
            }
        }

        return max;
    }

    public static int min(int ...row){
        int min = MAX_VALUE;
        for(int num: row){
            if(num < min){
                min = num;
            }
        }

        return min;
    }
}