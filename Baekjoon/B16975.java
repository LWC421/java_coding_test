import java.io.*;
import java.util.*;

public class Main{

  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    StringBuilder sb = new StringBuilder("");
    
    int numNumber = Integer.parseInt(in.readLine());
    int end = numNumber-1;
    
    long numbers[] = new long[numNumber];
    
    SegTree tree = new SegTree(numNumber);
    st = new StringTokenizer(in.readLine(), " ");
    for(int i = 0; i < numNumber; i++){
      numbers[i] = Long.parseLong(st.nextToken());
    }
    
    tree.init(numbers, 1, 0, end);
    
    
    
    int numQuery = Integer.parseInt(in.readLine());
    for(int q = 0; q < numQuery; q++){
      int command;
      st = new StringTokenizer(in.readLine());
      command = Integer.parseInt(st.nextToken());
      if(command == 1){
        int left, right;
        long updateValue;
        left = Integer.parseInt(st.nextToken());
        right = Integer.parseInt(st.nextToken());
        updateValue = Long.parseLong(st.nextToken());
        tree.update(1, updateValue, 0, end, left-1, right-1);
      }
      else{
        int target;
        target = Integer.parseInt(st.nextToken()) - 1;
        sb.append(tree.query(1, 0, end, target, target)).append("\n");
      }
    }
    
    
    System.out.println(sb.toString());
  }
  
  static class SegTree{
    long[] nodes;
    long[] lazy;
    
    SegTree(int numNode){
      int height = (int)Math.ceil(Math.log(numNode) / Math.log(2));
      int treeSize = (1 << (height + 1));
      nodes = new long[treeSize];
      lazy = new long[treeSize];
    }
    
    long init(long[] numbers, int node, int start, int end){
      if(start == end) return nodes[node] = numbers[start];
      int mid = start + (end - start) / 2;
      return nodes[node] = init(numbers, node*2, start, mid) + init(numbers, node*2+1, mid+1, end);
    }
    
    void updateLazy(int node, int start, int end){
      if(lazy[node] != 0){
        nodes[node] += (end-start+1) * lazy[node];
        
        if(start != end){
          lazy[node*2] += lazy[node];
          lazy[node*2 + 1] += lazy[node];
        }
        lazy[node] = 0;
      }
    }
    
    long query(int node, int start, int end, int left, int right){
      updateLazy(node, start, end);
      if(left > end || right < start) return 0;
      if(left <= start && end <= right) return nodes[node];
      
      int mid = start + (end-start)/2;
      long leftSum = query(node*2, start, mid, left, right);
      long rightSum = query(node*2+1, mid+1, end, left, right);
      return leftSum + rightSum;
    }
    
    void update(int node, long value, int start, int end, int left, int right){
      updateLazy(node, start, end);
      if(left > end || right < start) return;
      if(left <= start && end <= right) {
        nodes[node] += (end-start+1) * value;
        if(start != end){
          lazy[node*2] += value;
          lazy[node*2 + 1] += value;
        }
        return;
      }
      int mid = start + (end - start) / 2;
      update(node*2, value, start, mid, left, right);
      update(node*2+1, value, mid+1, end, left, right);
      nodes[node] = nodes[node*2] + nodes[node*2 + 1];
    }
  }
}