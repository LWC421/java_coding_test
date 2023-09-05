import java.io.*;
import java.util.*;

public class Main{
  
  static char[] inputs;
  static Data data[];
  static List<Pair> pairs;
  static int maxLength;
  static TreeSet<String> subset;
  
  final static char EMPTY = '\u0000';
  final static String EMPTY_STRING = "\u0000";
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder("");
    
    String input = in.readLine();
    //입력 종료
    
    data = new Data[input.length()];
    
    
    inputs = input.toCharArray();
    //(문자, index)로 넣어주기
    for(int i = 0; i < inputs.length; i++){
      data[i] = new Data(inputs[i], i);
    }
    
    Stack<Data> stack = new Stack<>();
    pairs = new LinkedList<>();
    
    //알맞은 괄호찾기
    Data top = null;
    for(Data d: data){
      if(d.c == '('){
        stack.push(d);
      }
      else if(d.c == ')'){
        top = stack.pop();
        pairs.add(new Pair(top.index, d.index));
      }
    }
    
    maxLength = pairs.size();
    subset = new TreeSet<>();
    
    subSet(0, 0);
    
    
    for(String s: subset){
      sb.append(s).append("\n");
    }
    
    System.out.println(sb.toString());
  }
  
  static void subSet(int count, int set){
    if(count == maxLength){
      //모든 것들을 고려했으면
      if(set == 0) return;  //공집합이면
      
      //set을 없앤 문자를 만들자
      char[] copied = Arrays.copyOf(inputs, inputs.length);
      for(int i = 0; i < maxLength; i++){
        
        Pair pair = null;
        if( (set & (1 << i)) != 0){
          //넣을 거면
          pair = pairs.get(i);
          copied[pair.left] = EMPTY;
          copied[pair.right] = EMPTY;
        }
      }
      
      subset.add(String.valueOf(copied).replace(EMPTY_STRING, ""));
      
      return;
    }
    
    subSet(count+1, set);                     //현재꺼 안 넣은거
    subSet(count+1, set | (1 << count));  //넣은거
  }
  
  static class Data{
    char c;
    int index;
    public Data(char c, int index){
      this.c = c;
      this.index = index;
    }
  }
  
  static class Pair{
    int left, right;
    public Pair(int left, int right){
      this.left = left;
      this.right = right;
    }
    @Override
    public String toString(){
      return String.format("[%d, %d]", left, right);
    }
  }
}