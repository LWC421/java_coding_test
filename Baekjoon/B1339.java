import java.io.*;
import java.util.*;

public class Main{
  
  static int numInput;

  static String[] inputs;
  
  static Integer[] weights;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    numInput = Integer.parseInt(in.readLine());
    
    inputs = new String[numInput];
    weights = new Integer['Z' - 'A' + 1];
    Arrays.fill(weights, 0);
    
    for(int i = 0; i < numInput; i++){
      inputs[i] = in.readLine();
    }
    //입력 종료
    
    //각 문자에 대한 가중치 설정
    for(String s: inputs){
      int weight = 1;
      String reversed = new StringBuilder(s).reverse().toString();    //문자 뒤집고
      for(char c: reversed.toCharArray()){
        weights[c - 'A'] += weight;
        weight *= 10;
      }
    }
    Arrays.sort(weights, Collections.reverseOrder());   //가중치가 큰 것이 앞에 오도록 하고
    
    long result = 0;
    for(int i = 0; i < 10; i++){
      result += weights[i] * (9 - i);
    }
    
    System.out.println(result);
  }
}