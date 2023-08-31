import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class Main{

    final static int MAX_VALUE = 1_000 * 10_000 + 1;

    static int numNode;         //노드의 개수
    static int numQuery;        //거리르 알고 싶은 노드 쌍

    static int[][] distances;   //거리

    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        StringBuilder sb = new StringBuilder("");
        StringTokenizer st = null;


        st = new StringTokenizer(in.readLine(), " ");

        numNode = Integer.parseInt(st.nextToken());
        numQuery = Integer.parseInt(st.nextToken());

        distances = new int[numNode+1][numNode+1];
        for(int y = 0; y <= numNode; y++){
            for(int x = 0; x <= numNode; x++){
                distances[y][x] = MAX_VALUE;
            }
        }

        int from, to, dist;
        for(int i = 1; i < numNode; i++){
            //노드 정보 받기
            st = new StringTokenizer(in.readLine(), " ");
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
            dist = Integer.parseInt(st.nextToken());

            distances[from][to] = dist;
            distances[to][from]= dist;
        }


        for(int i = 0; i < numQuery; i++){
            st = new StringTokenizer(in.readLine(), " ");
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());

            sb.append(getDistance(from, to)).append("\n");
        }

        System.out.println(sb.toString());
    }

    static int getDistance(int from, int to){
        PriorityQueue<Data> pq = new PriorityQueue<>();

        for(int n = 1; n <= numNode; n++){
            if(distances[from][n] != MAX_VALUE){
                pq.add(new Data(n, distances[from][n]));
            }
        }

        Data curr = null;
        int nDistance;

        while( !pq.isEmpty() ){
            curr = pq.poll();
            if(curr.node == to) break;  //도착했으면
            for(int n = 1; n <= numNode; n++){
                //나와 연결된 곳들 보기
                if(n == curr.node) continue;    //내가 나로 갈 이유는 없다

                nDistance = curr.distance + distances[curr.node][n];
                if(nDistance < distances[from][n]){
                    //현재꺼 거쳐가는게 더 가까울 경우
                    pq.add(new Data(n, nDistance));
                    distances[from][n] = nDistance;
                }
            }
        }

        return distances[from][to];
    }

    static class Data implements Comparable<Data>{
        int node;
        int distance;

        public Data(int node, int distance){
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(Data o){
            return  this.distance - o.distance;
        }
    }
}