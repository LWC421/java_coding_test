import java.io.*;
import java.util.*;

public class Main{
  
  static int numSection;
  static int numQuery;
  
  static Section[] sections;
  static Section[] queries;
  
  static Set<Section> exacts;                             //구간 자체를 저장
  static HashMap<Integer, TreeSet<Integer>> leftOrder;    //왼쪽을 기준으로 정리된 것
  static HashMap<Integer, TreeSet<Integer>> rightOrder;   //오른쪽을 기준으로 정리된 것
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder("");
    
    StringTokenizer st = null;
    
    numSection = Integer.parseInt(in.readLine());
    sections = new Section[numSection];
    
    int left, right;
    for(int i = 0; i < numSection; i++){
      st = new StringTokenizer(in.readLine(), " ");
      left = Integer.parseInt(st.nextToken());
      right = Integer.parseInt(st.nextToken());
      
      sections[i] = new Section(left, right);
    }
    
    numQuery = Integer.parseInt(in.readLine());
    queries = new Section[numQuery];
    
    for(int i = 0; i < numQuery; i++){
      st = new StringTokenizer(in.readLine(), " ");
      left = Integer.parseInt(st.nextToken());
      right = Integer.parseInt(st.nextToken());
      
      queries[i] = new Section(left, right);
    }
    //입력 종료
    
    exacts = new HashSet<>();
    leftOrder = new HashMap<>();
    rightOrder = new HashMap<>();
    
    for(Section s : sections){
      //각각의 구역에 대해
      exacts.add(s);
      
      TreeSet<Integer> rights = leftOrder.computeIfAbsent(s.left, key -> new TreeSet<>());
      rights.add(s.right);
      
      TreeSet<Integer> lefts = rightOrder.computeIfAbsent(s.right, key -> new TreeSet<>());
      lefts.add(s.left);
    }
    
    for(Section s: queries){
      //각각의 쿼리에 대해
      sb.append(find(s)).append("\n");
    }
    System.out.println(sb.toString());
  }
  
  static int find(Section s){
    Integer left, right;
    
    if(exacts.contains(s)){
      //정확히 매치가 되면
      return 1;
    }
    
    TreeSet<Integer> rights = leftOrder.get(s.left);
    if(rights == null) return -1;
    
    right = rights.higher(s.right);
    if(right == null) return -1;
    
    TreeSet<Integer> lefts = rightOrder.get(s.right);
    if(lefts == null) return -1;
    
    left = lefts.lower(s.left);
    if(left == null) return -1;

    return 2;   //구간 두개로 되는 경우다
  }
  
  static class Section{
    int left, right;
    
    @Override
    public boolean equals(Object o) {
      Section section = (Section) o;
      return left == section.left && right == section.right;
    }
    
    @Override
    public int hashCode() {
      return left * (int)(1e6 + 1) + right;
    }
    
    public Section(int left, int right) {
      this.left = left;
      this.right = right;
    }
  }
}