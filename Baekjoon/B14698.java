import java.io.*;
import java.util.*;

public class Main{

    static int TC;
    static int numSlime;
    static long[] slimes;

    final static long DIVIDER = 1_000_000_007;

    public static void main(String[] args) throws Exception{
//        System.setIn(new FileInputStream("input.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder("");
        StringTokenizer st = null;

        TC = Integer.parseInt(in.readLine());

        for(int test_case = 0; test_case < TC; test_case++){
            //하나하나의 테스트 케이스들
            numSlime = Integer.parseInt(in.readLine());
            slimes = new long[numSlime];

            st = new StringTokenizer(in.readLine(), " ");

            for(int i = 0; i < numSlime; i++){
                slimes[i] = Long.parseLong(st.nextToken());
            }
            //테스트 케이스 입력 종료

            long result = 1;
            PriorityQueue<Data> pq = new PriorityQueue<>();
            for(long slime: slimes) pq.add(new Data(0, slime/DIVIDER, slime%DIVIDER));

            long cost;
            long first, second;
            Data a, b;
            while( pq.size() > 1){
                a = pq.poll();
                b = pq.poll();

                cost = a.third * b.third;

                first = (a.first * b.third) + (b.first * a.third) + (a.second * b.second);
                second = (a.second * b.third) + (b.second * a.third);
                second += (cost / DIVIDER);

                first += second / DIVIDER;
                second  = second % DIVIDER;

                cost = cost % DIVIDER;
                result = (result * cost) % DIVIDER;

                pq.add(new Data(first, second, cost));
            }

            sb.append(result).append("\n");
        }
        System.out.println(sb.toString());
    }

    static class Data implements Comparable<Data>{
        long first;
        long second;
        long third;

        public Data(long first, long second, long third){
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public int compareTo(Data o){
            if(this.first != o.first){
                return (int)(this.first - o.first);
            }
            if(this.second != o.second){
                return (int)(this.second - o.second);
            }
            return (int)(this.third - o.third);
        }

        @Override
        public String toString(){
            return String.format("[%d, %d, %d]", first, second, third);
        }
    }
}