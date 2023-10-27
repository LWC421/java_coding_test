import java.io.*;
import java.util.*;

public class Main{

  static int numPoint;
  static Coord[] coords;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    
    numPoint = Integer.parseInt(in.readLine());
    coords = new Coord[numPoint];
    
    for(int i = 0; i < numPoint; i++){
      long x, y;
      st = new StringTokenizer(in.readLine());
      x = Long.parseLong(st.nextToken());
      y = Long.parseLong(st.nextToken());
      coords[i] = new Coord(x, y);
    }
    
    //우선 최하단의 점이 [0]에 오도록 정렬
    Arrays.sort(coords);
    
    Arrays.sort(coords, 1, numPoint, (a, b) -> {
      long ccw = ccw(coords[0], a, b);
      if(ccw > 0) return -1;
      if(ccw < 0) return 1;
      return Long.compare(distance(coords[0], a), distance(coords[0], b));
    });
    
    Stack<Coord> stack = new Stack<>();
    stack.push(coords[0]);
    stack.push(coords[1]);
    
    for(int i = 2; i < numPoint; i++){
      while(stack.size() > 1 && ccw( stack.get(stack.size()-2), stack.get(stack.size()-1), coords[i] ) <= 0) {
        stack.pop();
      }
      stack.push(coords[i]);
    }
    
    System.out.println(stack.size());
  }
  
  static long ccw(Coord a, Coord b, Coord c){
    long forward = (a.x * b.y) + (b.x * c.y) + (c.x*a.y);
    long backward = (b.x * a.y) + (c.x * b.y) + (a.x * c.y);
    return forward - backward;
  }
  
  static long distance(Coord a, Coord b){
    long diffX = (b.x - a.x);
    long diffY = (b.y - a.y);

    return (diffX*diffX) + (diffY*diffY);
  }
  
  static class Coord implements Comparable<Coord>{
    long x, y;
    public Coord(long x, long y){
      this.x = x;
      this.y = y;
    }
    @Override
    public int compareTo(Coord o){
      if(this.y != o.y) return Long.compare(this.y, o.y);
      return Long.compare(this.x, o.x);
    }
    @Override
    public String toString(){
      return String.format("(%d, %d)", x, y);
    }
  }
}