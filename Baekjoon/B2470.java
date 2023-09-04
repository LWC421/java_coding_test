import java.io.*;
import java.util.*;

public class Main{
  
  static int numMaterial;

  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;
    
    numMaterial = Integer.parseInt(in.readLine());
    
    st = new StringTokenizer(in.readLine(), " ");
    
    TreeSet<Integer> materials = new TreeSet<>();
    int value;
    for(int i = 0; i < numMaterial; i++){
      value = Integer.parseInt(st.nextToken());
      materials.add(value);
    }
    //입력 종료
    
    int result1 = 0, result2 = 0;

    int min = (int)2e9 + 1;         //특성값이 얼마나 0에 가까운지 나타내는 지표
    int sum;
    Integer material2;
    for(int material1 : materials){
      material2 = materials.ceiling(-material1);
      if(material2 != null && material2 != material1) {
        sum = Math.abs(material1 + material2);
        if(sum < min){
          //갱신해야 되면
          min = sum;
          result1 = material1;
          result2 = material2;
        }
      }
      
      material2 = materials.floor(-material1);
      if(material2 != null && material2 != material1) {
        sum = Math.abs(material1 + material2);
        if(sum < min){
          //갱신해야 되면
          min = sum;
          result1 = material1;
          result2 = material2;
        }
      }
    }
    
    
    System.out.println(result1 < result2 ? String.format("%d %d", result1, result2) : String.format("%d %d", result2, result1));
  }
}