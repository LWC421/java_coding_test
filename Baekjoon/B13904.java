import java.io.*;
import java.util.*;

public class Main{

  static int numProblem;
  static int maxDeadline;
  static List<Integer>[] scores;
  static Data[] inputs;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    
    maxDeadline = -1;
    
    numProblem = Integer.parseInt(in.readLine());
    
    inputs = new Data[numProblem];
    
    for(int i = 0; i < numProblem; i++){
      int deadline, score;
      st = new StringTokenizer(in.readLine());
      deadline = Integer.parseInt(st.nextToken());
      score = Integer.parseInt(st.nextToken());
      
      maxDeadline = Math.max(deadline, maxDeadline);
      inputs[i] = new Data(deadline, score);
    }
    
    
    scores = new LinkedList[maxDeadline + 1];
    for(int i = 1; i <= maxDeadline; i++){
      scores[i] = new LinkedList<>();
    }
    
    for(int i = 0; i < numProblem; i++){
      scores[inputs[i].deadline].add(inputs[i].score);
    }
    
    
    int result = 0;
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
    for(int today = maxDeadline; today > 0; today--){
      pq.addAll(scores[today]);
      if( !pq.isEmpty() ){
        result += pq.poll();
      }
    }
    
    
    System.out.println(result);
  }
  
  static class Data{
    int deadline, score;
    public Data(int deadline, int score){
      this.deadline = deadline;
      this.score = score;
    }
  }
}