import java.io.*;
import java.util.*;

public class Main{

  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    StringBuilder sb = new StringBuilder("");
    
    st = new StringTokenizer(in.readLine());
    
    int mapLength = Integer.parseInt(st.nextToken());
    int sight = Integer.parseInt(st.nextToken());
    
    st = new StringTokenizer(in.readLine());
    
    int[] map = new int[mapLength];
    
    for(int i = 0; i < mapLength; i++){
      map[i] = Integer.parseInt(st.nextToken());
    }
    
    TreeMap<Integer, Integer> tree = new TreeMap<>();
    
    
    for(int i = 0, limit=sight*2-2; i < limit; i++){
      tree.computeIfPresent(map[i], (key, value) -> value+1);
      tree.putIfAbsent(map[i], 1);
    }
    
    int prev = 0;
    for(int i = sight*2-2; i < mapLength; i++){
      tree.computeIfPresent(map[i], (key, value)->value+1);
      tree.putIfAbsent(map[i], 1);
      sb.append(tree.lastKey()).append(" ");
      tree.computeIfPresent(map[prev++], (key, value)->{
        if(value == 1) return null;
        return value-1;
      });
    }
    
    System.out.println(sb.toString());
  }
}