import java.io.*;
import java.util.*;

public class Main{
  
  // 목표물의 방향으로 공격방향 바꾸기
  // 공격 방햐엥 있는 풍선들의 체력을 d씩 낮춘다
  
  // 타워는 [0][0]에 존재한다
  
  static int numBalloon;    //N, 풍선의 개수, [1 ~ 200_000]
  static int numAttack;     //M, 공격 횟수   [1 ~ 200_000]
  
  static long[] hps;         //풍선들의 hp들을 저장
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st = null;
    
    st = new StringTokenizer(in.readLine(), " ");
    
    numBalloon = Integer.parseInt(st.nextToken());
    numAttack = Integer.parseInt(st.nextToken());
    
    hps = new long[numBalloon];
    
    int x = 0, y = 0;
    long hp;
    
    TreeMap<Long, Long> counter = new TreeMap<>();
    for(int i = 0; i< numBalloon; i++){
      //풍선의 정보받기
      st = new StringTokenizer(in.readLine(), " ");
      x = Integer.parseInt(st.nextToken());
      y = Integer.parseInt(st.nextToken());
      hp = Long.parseLong(st.nextToken());
      
      hps[i] = hp;
      counter.computeIfAbsent(hp, (key) -> 0L);
      counter.computeIfPresent(hp, (key, value) -> value+1);
    }
    
    long acc = 0;
    long key, value;
    
    TreeMap<Long, Long> accCounter = new TreeMap<>();   //누적 카운터로 변경
    
    for(Map.Entry<Long, Long> e: counter.entrySet()){
      key = e.getKey();
      value = e.getValue();
      acc += value;
      
      accCounter.put(key, acc);
    }
    
    int gcd = GCD(Math.abs(x), Math.abs(y));
    y /= gcd;
    x /= gcd;   //정규화 과정을 거치자
    
    int attackY, attackX, demage;
    
    long accDemage = 0;  //누적 데미지
    for(int i = 0; i < numAttack; i++){
      //각각의 공격에 대해
      st = new StringTokenizer(in.readLine());
      
      attackX = Integer.parseInt(st.nextToken());
      attackY = Integer.parseInt(st.nextToken());
      demage = Integer.parseInt(st.nextToken());
      
      gcd = GCD(Math.abs(attackY), Math.abs(attackX));
      
      attackX /= gcd;
      attackY /= gcd;
      
      if(attackX == x && attackY == y){
        //공격방향이 일치하면
        accDemage += demage;  //데미지 누적
      }
      
      Map.Entry<Long, Long> entry = accCounter.floorEntry(accDemage);
      
      sb.append(numBalloon - (entry == null ? 0 : entry.getValue())).append("\n");
    }
    
    System.out.println(sb.toString());
  }
  
  
  static int GCD(int a, int b){
    if(b == 0){
      return a;
    }
    return GCD(b, a % b);
  }
}