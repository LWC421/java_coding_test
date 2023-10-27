import java.io.*;
import java.util.*;

public class Main{

  static int numLine;
  static Line[] lines;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    
    numLine = Integer.parseInt(in.readLine());
    lines = new Line[numLine];
    
    for(int i = 0; i < numLine; i++){
      int from, to;
      st = new StringTokenizer(in.readLine());
      from = Integer.parseInt(st.nextToken());
      to = Integer.parseInt(st.nextToken());
      lines[i] = new Line(from, to);
    }
    
    Arrays.sort(lines);
    
    int result = 0;
    Line curr;
    int left = -1_000_000_000 - 1;
    int right = -1_000_000_000 - 1;
    for(int i = 0; i < numLine; i++){
      curr = lines[i];
      if(right < curr.from){
        //안 겹치면
        result += right - left;
        left = curr.from;
        right = curr.to;
      }
      else{
        //겹치면
        right = Math.max(right, curr.to);
      }
    }
    result += right - left;
    
    System.out.println(result);
  }
  
  static class Line implements Comparable<Line>{
    int from, to;
    public Line(int from, int to){
      this.from = from;
      this.to = to;
    }
    @Override
    public int compareTo(Line o){
      if(this.from != o.from) return this.from - o.from;
      return this.to - o.to;
    }
    @Override
    public String toString(){
      return String.format("%d -> %d", from, to);
    }
  }
}