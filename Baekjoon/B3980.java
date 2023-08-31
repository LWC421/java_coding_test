import java.io.*;
import java.util.*;

public class Main{
  
  // 11! = 39_916_800

  static int TC;
  static int[][] powers;    //[선수][포지션]
  
  final static int NUM_MEMBER = 11;
  final static int NUM_POSITION = 11;
  
  static int maxPower;
  static boolean[] used;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    StringBuilder sb = new StringBuilder("");
    
    TC = Integer.parseInt(in.readLine());
    
    while(TC-- > 0){
      //각각의 테스트케이스에 대해
      powers = new int[NUM_MEMBER][NUM_POSITION];
      used = new boolean[NUM_MEMBER];
      maxPower = 0;
      
      for(int mem = 0; mem < NUM_MEMBER; mem++){
        st = new StringTokenizer(in.readLine(), " ");
        for(int pos = 0; pos < NUM_POSITION; pos++){
          powers[mem][pos] = Integer.parseInt(st.nextToken());
        }
      }
      //테스트 케이스 하나 입력 종료
      
      dfs(0, new int[11]);
      sb.append(maxPower).append("\n");
    }
    
    System.out.println(sb.toString());
  }
  
  public static void dfs(int count, int[] positions){
    if(count == NUM_MEMBER){
      //모든 멤버를 고려했으면
      int result = 0;
      for(int mem = 0; mem < NUM_MEMBER; mem++){
         result += powers[mem][positions[mem]];
      }
      maxPower = Math.max(result, maxPower);
      return;
    }
    
    for(int pos = 0; pos < NUM_POSITION; pos++){
      if(powers[count][pos] == 0) continue; //해당 포지션에 대해 설 수 없으면
      if(used[pos]) continue;               //해당 포지션을 다른선수가 차지하고 있으면
      
      used[pos] = true;         //일단 차지하고 있다고 표시하고
      positions[count] = pos;   //해당 포지션이라고 표시
      dfs(count+1, positions);    //다음 선수에대해 고려해보기
      used[pos] = false;        //되돌리기
    }
    
  }
}