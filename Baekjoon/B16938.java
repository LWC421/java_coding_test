import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main{

    static int numQuestion;    //문제 개수                    [1~15]
    static int lower;          //합의 최소값                  [1~10^9]
    static int higher;         //합의 최대값                  [1~10^9]
    static int diff;           //최소 최대는 diff이상이어야 한다 [1~10^6]

    static int[] questions;            //문제들의 난이도
    static Set<Integer> visited;    //이미 방문한거 체크

    static int result;              //가능한 가짓수 저장

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get("input.txt"))));
        StringTokenizer st = new StringTokenizer(in.readLine(), " ");

        numQuestion = Integer.parseInt(st.nextToken());
        lower = Integer.parseInt(st.nextToken());
        higher = Integer.parseInt(st.nextToken());
        diff = Integer.parseInt(st.nextToken());

        questions = new int[numQuestion];

        st = new StringTokenizer(in.readLine(), " ");
        for(int i = 0; i < numQuestion; i++){
            questions[i] = Integer.parseInt(st.nextToken());
        }
        //입력 종료

        if(numQuestion == 1){
            //문제가 1개이면 방법은 0가지 이다
            System.out.println("0");
            return;
        }

        //문제들의 난이도를 정렬
        Arrays.sort(questions);

        visited = new HashSet<>();

        int use = (1 << numQuestion) - 1;      //처음에는 모든 문제를 사용한다고 하자
        result = 0; //아직 가능한 가지수가 없다고 표시

        go(use);    //일단 모두 사용하는 걸로 시작한다
        
        System.out.println(result);
    }

    public static void go(int use){
        if(visited.contains(use)){
            //이미 해당 use로 방문 했으면
            return;
        }

        visited.add(use);   //해당 use로 방문했다고 표시

        int lowIndex = 0, highIndex = 0;
        //가장 높은 bit가져오기
        for(int i = numQuestion-1; i >= 0; i--){
            if((use & (1 << i)) != 0){
                //해당 bit를 사용중이면
                highIndex = i;
                break;
            }
        }
        //가장 낮은 bit가져오기
        for(int i = 0; i < numQuestion; i++){
            if((use & (1 << i)) != 0){
                //해당 bit를 사용중이면
                lowIndex = i;
                break;
            }
        }
        
        if(lowIndex == highIndex){
            //만약 두 bit가 동일하다 -> use의 개수가 1개 또는 0개라는 소리다
            return;
        }

        int highQuestion = questions[highIndex];
        int lowQuestion = questions[lowIndex];

        if((highQuestion - lowQuestion) < diff){
            //최대, 최소 난이도의 차이가 diff미만일 경우
            return;
        }
        
        int sumQuestion = 0;
        for(int i = 0; i < numQuestion; i++){
            if( (use & (1 << i)) != 0){
                //해당bit를 사용중이면
                sumQuestion += questions[i];    //해당 문제를 더해주자
            }
        }
        if(sumQuestion < lower){
            //문제의 난이도가 너무 낮은 경우
            return;
        }
        
        if(sumQuestion <= higher){
            //문제의 난이도가 적절한 경우
            result++;   //가지수에다가 더해주자
        }

        //여기까지 왔으면 현재 사용한 use에서 한개를 빼볼 수 있다
        for(int i = 0; i < numQuestion; i++){
            if( (use & (1 << i)) != 0 ){
                //사용중인 bit이면
                go(use ^ (1 << i)); //해당 bit를 사용안하면 어떻게 되는지 보자
            }
        }
    }
}