import java.io.*;
import java.util.*;

public class Main{
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    StringBuilder sb = new StringBuilder("");
    
    int TEST_CASE = Integer.parseInt(in.readLine());
    
    while( TEST_CASE-- > 0 ){
      //테스트 케이스
      int numPoint = Integer.parseInt(in.readLine());
      List<Coord> coords = new ArrayList<>(numPoint);
      TreeSet<Integer> set = new TreeSet<>();
      
      for(int i = 0; i < numPoint; i++){
        st = new StringTokenizer(in.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        set.add(y);
        coords.add(new Coord(x, y));
      }
      
      //입력 종료
      Collections.sort(coords);
      int index = 0;
      Map<Integer, Integer> yToIndex = new HashMap<>();
      
      for(int y: set){
        yToIndex.put(y, index++);
      }
      
      int i = 0;
      int[] yCoords = new int[numPoint];
      for(Coord c: coords){
        yCoords[i++] = yToIndex.get(c.y);
      }
      
      
      long result = 0;
      SegTree tree = new SegTree(set.size());
      for(int y: yCoords){
        result += tree.query(y);
        tree.insert(y);
      }
      
      sb.append(String.format("%d\n", result));
    }
    
    System.out.println(sb.toString());
  }
  
  static class SegTree{
    int leafStart;
    int end;
    long[] nodes;
    
    SegTree(int size){
      int h = (int)Math.ceil(Math.log(size) / Math.log(2));
      leafStart = (int)Math.pow(2, h);
      this.end = leafStart-1;
      nodes = new long[leafStart*2];
    }
    
    void insert(int y){
      int curr = leafStart + y;
      while(curr > 0){
        nodes[curr] += 1;
        curr /= 2;
      }
    }
    
    long query(int target){
      int start = 0;
      int end = this.end;
      
      return query(1,
          start, end,
          target, end);
    }
    
    private long query(int index,
                       int start, int end,
                       int left, int right){
      if( right < start || end < left ) return 0;
      if( left <= start &&  end <= right){
        return nodes[index];
      }
      
      int mid = start + (end - start)/2;
      long lSum = query(index*2, start, mid, left, right);
      long rSum = query(index*2+1, mid+1, end, left, right);
      
      return lSum + rSum;
    }
    
    @Override
    public String toString(){
      return Arrays.toString(nodes);
    }
  }
  
  static class Coord implements Comparable<Coord>{
    int x, y;
    public Coord(int x, int y){
      this.x = x;
      this.y = y;
    }
    
    @Override
    public int compareTo(Coord o){
      if(this.x != o.x) return this.x -  o.x;
      return o.y - this.y;
    }
    
    @Override
    public String toString(){
      return String.format("(%d, %d)", x, y);
    }
  }
}