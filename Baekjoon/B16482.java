import java.io.*;
import java.util.*;

public class Main{

  static int maxAlpha;   //N, 사용할 수 있는 알파벳 종류의 최대값   [1 ~ 26]
  static char[] inputs;
  
  static int[] counts;      //각 알파벳 별 구간에 속한 개수
  
  static int maxLength;
  
  public static void main(String[] args) throws Exception{
//    System.setIn(new FileInputStream("input.txt"));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    maxAlpha = Integer.parseInt(in.readLine());
    inputs = in.readLine().toCharArray();
    //입력 종료
    
    counts = new int['z' - 'a' + 1];

    int length = 0;         //구간에 속한 글자의 길이
    int kindOfAlpha = 0;    //구간에 속한 알파벳의 종류
    
    maxAlpha = Math.min(maxAlpha, inputs.length);
    
    //일단 넣기
    for(int i = 0; i < maxAlpha; i++){
      int targetCount = counts[inputs[i] - 'a'];
      if(targetCount == 0){
        //만약 해당 알파벳이 들어온적이 없던 거면
        kindOfAlpha++;
      }
      counts[inputs[i] - 'a']++;
      length++;
    }
    
    maxLength = length;   //일단 length로 초기화
    
    int left = 0;   //구간의 left
    for(int right = maxAlpha; right < inputs.length; right++){
      
      int targetCount = counts[inputs[right] - 'a'];
      if(targetCount == 0){
        //들어온적이 없던 알파벳이면
        kindOfAlpha++;
        
        if(kindOfAlpha == maxAlpha + 1){
          //만약 해당 구간의 알파벳들의 종류가 이미 max이면
          while(--counts[inputs[left++] - 'a'] != 0); //left를 하나씩 오른쪽으로 옮기면서 성공적으로 알파벳이 빼질때까지 반복한다
          kindOfAlpha--;
        }
      }
      
      counts[inputs[right] - 'a']++;
      
      maxLength = Math.max(maxLength, right - left + 1);
    }
    
    
    System.out.println(maxLength);
  }
}