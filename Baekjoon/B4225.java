import java.io.*;
import java.util.*;

public class Main{
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    StringBuilder sb = new StringBuilder("");
    
    int numPoint = 0;
    int test_case = 1;
    while( (numPoint = Integer.parseInt(in.readLine())) != 0 ){
      //테스트케이스 시작
      Coord[] coords = new Coord[numPoint];
      
      for(int i = 0; i < numPoint; i++){
        st = new StringTokenizer(in.readLine(), " ");
        double x, y;
        x = Double.parseDouble(st.nextToken());
        y = Double.parseDouble(st.nextToken());
        
        coords[i] = new Coord(x, y);
      }
      //테스트 케이스 입력 종료
      double result = solve(numPoint, coords);
      sb.append(String.format("Case %d: ", test_case++));
      sb.append(String.format("%.2f", result)).append("\n");
    }
    
    System.out.println(sb.toString());
  }
  
  static double solve(int numPoint, Coord[] coords){
    Arrays.sort(coords, (a, b) -> {
      if(a.y != b.y) return Double.compare(a.y, b.y);
      return Double.compare(a.x, b.x);
    });
    
    
    Coord base = coords[0];
    Arrays.sort(coords, 1, numPoint, (a, b) -> {
      double ccw = ccw(base, a, b);
      if(ccw > 0){
        return -1;
      }
      else if(ccw == 0){
        double aDistance = distance(base, a);
        double bDistance = distance(base, b);
        return Double.compare(aDistance, bDistance);
      }
      else{
        return 1;
      }
    });
    
    Stack<Coord> stack = new Stack<>();
    stack.push(coords[0]);
    stack.push(coords[1]);
    
    for(int i = 2; i < numPoint; i++){
      while( stack.size() >= 2 && ccw(stack.get(stack.size()-2), stack.get(stack.size()-1), coords[i]) <= 0 ) stack.pop();
      stack.push(coords[i]);
    }
    
    Coord[] convex = new Coord[stack.size()];
    int size=stack.size();
    for(int i = 0; i < size; i++){
      convex[i] = stack.pop();
    }
    
    double minDist = 1000*1000*1000;
    for(int i = 0; i < size; i++){
      Coord c1 = convex[i];
      Coord c2 = convex[ (i+1) % size];
      
      double localMax = 0;
      for(int t = 0; t < size; t++){
        if(t == i || t == (i+1) % size) continue;
        double dist = lineToDotDistance(c1, c2, convex[t]);
//        System.out.printf("%s-%s -> %s : %s\n", c1, c2, convex[t], dist);
        localMax = Math.max(localMax, dist);
      }
      minDist = Math.min(localMax, minDist);
    }

    return minDist;
  }
  
  static double ccw(Coord a, Coord b, Coord c){
    double forward = (a.x*b.y) + (b.x*c.y) + (c.x*a.y);
    double backward = (b.x*a.y) + (c.x*b.y) + (a.x*c.y);
    return forward - backward;
  }
  
  static double distance(Coord a, Coord b){
    return (a.x - b.x) * (a.x - b.x) + (a.y - b.y)*(a.y-b.y);
  }
  
  static double lineToDotDistance(Coord c1, Coord c2, Coord target){
    double ccw = Math.abs(ccw(c1, c2, target));

    double dist = Math.sqrt(distance(c1, c2));
    
    double result = ccw / dist;
    result *= 100;
    if(result - (long)result > 1e-12){
      result += 1;
      result = (long)result;
    }
    
    return result/100;
  }
  
  
  static class Coord{
    double x, y;
    Coord(double x, double y){
      this.x = x;
      this.y = y;
    }
    @Override
    public String toString(){
      return String.format("(%f, %f)", x, y);
    }
  }
}