import java.io.*;
import java.util.*;

public class Main{
  
  final static long MODULER = 1000000000 + 7;

  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    
    int numPoint = Integer.parseInt(in.readLine());
    
    Coord[] coords = new Coord[numPoint];
    for(int i = 0; i < numPoint; i++){
      st = new StringTokenizer(in.readLine());
      
      int x = Integer.parseInt(st.nextToken());
      int y = Integer.parseInt(st.nextToken());
      coords[i] = new Coord(x, y);
    }
    
    // Compress of X
    Arrays.sort(coords, (a, b) -> {
      return a.x - b.x;
    });
    TreeSet<Integer> set = new TreeSet<>();
    for(Coord c: coords){
      set.add(c.x);
    }
    
    Map<Integer, Integer> xToIndex = new HashMap<>();
    
    int index = 0;
    for(Integer c: set){
      xToIndex.put(c, index++);
    }
    
    // Convert x to index;
    for(Coord c: coords){
      c.x = xToIndex.get(c.x);
    }
    
    // key=y, value=List of x, order by y desc
    TreeMap<Integer, List<Integer>> map = new TreeMap<>((a, b) -> {
      return b-a;
    });
    for(Coord c: coords){
      map.put(c.y, new LinkedList<>());
    }
    
    
    for(Coord c: coords){
      map.get(c.y).add(c.x);
    }
    
    SegTree tree = new SegTree(set.size());
    long result = 0;
    
    int end = set.size() - 1;
    for(Map.Entry<Integer, List<Integer>> entry: map.entrySet()){
      List<Integer> xValues = entry.getValue();
      
      // search left and right
      for(int xIndex: xValues){
        if(xIndex == 0 || xIndex == end) continue;  // left or right side
        long leftQuery = tree.query(0, xIndex-1);
        long rightQuery = tree.query(xIndex+1, end);
        
        result += (leftQuery * rightQuery) % MODULER;
        result = result % MODULER;
      }
      
      
      // insert to segment tree
      for(int xIndex: xValues){
        tree.insert(xIndex);
      }
    }
    
    
    
    System.out.println(result);
  }
  
  static class SegTree{
    int leafStart;
    int end;
    long[] nodes;
    
    SegTree(int size){
      int h = (int)Math.ceil(Math.log(size) / Math.log(2));
      leafStart = (int)Math.pow(2, h);
      end = leafStart - 1;
      nodes = new long[leafStart * 2];
    }
    
    void insert(int index){
      int curr = leafStart + index;
      while(curr > 0){
        nodes[curr] += 1;
        curr/=2;
      }
    }
    
    long query(int left, int right){
      int index = 1;
      int start = 0;
      int end = this.end;
      
      return query(index, start, end, left, right);
    }
    
    long query(int index, int start, int end, int left, int right){
    
      if(right < start || end < left) return 0;
      if( left <= start && end <= right ) {
        return nodes[index];
      }
      
      int mid = start + (end - start) / 2;
      long lSum = query(index*2, start, mid, left, right);
      long rSum = query(index*2 + 1, mid+1, end, left, right);
      return lSum + rSum;
    }
    
    
    @Override
    public String toString(){
      return Arrays.toString(nodes);
    }
  }
  
  static class Coord{
    int x, y;
    public Coord(int x, int y){
      this.x = x;
      this.y = y;
    }
    @Override
    public String toString(){
      return String.format("(%d, %d)", x, y);
    }
  }
}