import java.io.*;
import java.util.*;

public class Main{

    // 빈칸, 치킨집, 집
    // [1, 1]부터 시작
    // 치킨집을 M개만 남기고 나머지 폐업, 치킨거리 최소화 하기

    final static int EMPTY      = 0;
    final static int HOUSE      = 1;
    final static int CHICKEN    = 2;

    static int length;         //맵의 길이
    static int remainChicken;  //남겨야 하는 치킨집 개수

    static int numChicken;     //초기 맵의 치킨집의 개수
    static int deleteChicken;  //지워야 하는 치킨집의 개수

    static List<Coord> houses;      //집 정보 저장
    static List<Coord> chickens;    //치킨집 정보 저장

    static TreeSet<Data>[] distances;       //key: 거리, value: 해당 치킨집

    static int minDistance = 50*50*13;
    
    public static void main(String[] args) throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        length = Integer.parseInt(st.nextToken());
        remainChicken  = Integer.parseInt(st.nextToken());

        houses = new ArrayList<>(0);
        chickens = new ArrayList<>(0);

        int num;
        for(int y = 0; y < length; y++){
            st = new StringTokenizer(in.readLine());
            for(int x = 0; x < length; x++){
                num = Integer.parseInt(st.nextToken());
                if(num == CHICKEN) {
                    numChicken++; //치킨집이면
                    chickens.add(new Coord(y, x));
                }
                else if(num == HOUSE) {
                    houses.add(new Coord(y, x));
                }
            }
        }
        //입력 종료

        deleteChicken = numChicken - remainChicken; //지워야 하는 치킨집의 개수
        distances = new TreeSet[houses.size()]; //집의 개수만큼 배열 초기화
        for(int i = 0; i < houses.size(); i++){
            distances[i] = new TreeSet<>();
        }

        getDistances();

        combination(0, 0, new HashSet<Integer>());

        System.out.print(minDistance);
    }

    public static void getDistances(){
        //각 집별로 각 치킨집마다 가는 비용들 저장

        int houseLength = houses.size();
        for(int h = 0; h < houseLength; h++){
            TreeSet houseDistance = distances[h];
            Coord house = houses.get(h);
            for(int c = 0; c < numChicken; c++){
                //각각의 치킨집에 대해
                Coord chicken = chickens.get(c);
                int distance = Math.abs(chicken.y - house.y) + Math.abs(chicken.x - house.x);
                houseDistance.add(new Data(c, distance));
            }
        }
    }

    public static void combination(int count, int index, Set<Integer> indexes){
        if(count == deleteChicken){
            //지워야하는 치킨집의 개수만큼 후보를 정했으면
            calc(indexes);
            return;
        }

        for(int i = index; i < numChicken; i++){
            //치킨집을 보면서
            indexes.add(i);
            combination(count+1, i+1, indexes);
            indexes.remove(i);
        }
    }

    public static void calc(Set<Integer> indexes){
        //indexes에 해당하는 치킨집들이 폐점을 했다고 가정하면 얼마가 나오는지
        int result = 0;
        for(TreeSet<Data> houseDistance: distances){
            for(Data dist: houseDistance){
                if(indexes.contains(dist.id)){
                    //지워야 되는 치킨집이면
                    continue;
                }
                result += dist.distance;
                break;
            }
        }
        minDistance = Math.min(minDistance, result);
    }

    static class Coord{
        int y;
        int x;
        public Coord(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    static class Data implements Comparable<Data>{
        int id;
        int distance;

        public Data(int id, int distance){
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(Data o) {
            if(this.distance == o.distance){
                return this.id - o.id;
            }
            return this.distance - o.distance;
        }

        @Override
        public String toString(){
            return String.format("[id:%d, dis:%d]", id, distance);
        }
    }
}