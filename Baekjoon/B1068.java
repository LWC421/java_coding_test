import java.io.*;
import java.util.*;


public class Main{
  
  static int numNode;
  static List<Integer>[] children;
  static boolean[] deleted;
  static int targetNode;

  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
  
    numNode = Integer.parseInt(in.readLine());
    children = new List[numNode];
    deleted = new boolean[numNode];
    for(int i = 0; i < numNode; i++) {
      children[i] = new LinkedList<>();
    }
    
    
    StringTokenizer st = new StringTokenizer(in.readLine(), " ");
    for(int i = 0; i < numNode; i++){
      int parent = Integer.parseInt(st.nextToken());
      if(parent != -1) children[parent].add(i);    //parent의 자식으로 i가 있다는 뜻이다
    }
    
    targetNode = Integer.parseInt(in.readLine());
    
    Queue<Integer> q = new ArrayDeque<>();
    q.add(targetNode);
    
    int target;
    while( !q.isEmpty() ){
      target = q.poll();
      deleted[target] = true;
      for(int n: children[target]) q.add(n);
    }
    int result = 0;
    
    Loop: for(int i = 0; i < numNode; i++){
      if(deleted[i]) continue Loop;
      List<Integer> child = children[i];
      //각각의 노드의 자식들을 보면서
      for(int c : child){
        if(!deleted[c]){
          //삭제가 안된 자식 노드가 있으면 -> 리프노드가 아니다
          continue Loop;
        }
      }
      result++;
    }
    
    System.out.println(result);
  }
}