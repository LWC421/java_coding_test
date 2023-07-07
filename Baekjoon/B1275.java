import java.io.*;
import java.util.*;

public class Main {

  static int length;    //입력으로 주어지는 수열의 길이
  static int numQuery;  //턴의 개수


  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder("");
    StringTokenizer st = new StringTokenizer(in.readLine());

    length = Integer.parseInt(st.nextToken());
    numQuery = Integer.parseInt(st.nextToken());

    int[] inputs = new int[length];
    st = new StringTokenizer(in.readLine());
    for (int i = 0; i < length; i++) {
      //수열 입력받기
      inputs[i] = Integer.parseInt(st.nextToken());
    }

    SegmentTree seg = new SegmentTree(inputs);

    int left, right;      //합을 구할 구간
    int target, number;   //바꿀 숫자의 위치, 바꿀 숫자
    int temp;             //스왑을 위해 필요한 수

    for (int q = 0; q < numQuery; q++) {
      //쿼리의 개수 만큼 입력 받기
      st = new StringTokenizer(in.readLine());
      left = Integer.parseInt(st.nextToken()) - 1;
      right = Integer.parseInt(st.nextToken()) - 1;
      target = Integer.parseInt(st.nextToken()) - 1;
      number = Integer.parseInt(st.nextToken());
      if(left > right){
        //항상 left가 더 작은 수가 되도록 하기
        temp = left;
        left = right;
        right = temp;
      }

      //결과 찾고
      sb.append(seg.query(left, right)).append('\n');
      //업데이트 하기
      seg.update(target, number);
    }

    System.out.print(sb.toString());
  }

  static class SegmentTree{

    long[] tree;
    int depth;  //root의 경우 0
    int start;  //leaf node의 맨 왼쪽 index
    int end;    //leaf node의 맨 오른쪽 index +1

    public SegmentTree(int[] inputs){
      int length = inputs.length;

      int b = 31;
      for(; b >= 0; b--){
        int msb = 1 << b;
        if( (length & (msb)) != 0){
          break;
        }
      }
      start = (int)Math.pow(2, b+1);
      end = start * 2;
      tree = new long[end];

      for (int i = 0; i < length; i++) {
        this.update(i, inputs[i]);
      }
    }

    public void update(int target, int number){
      int currIndex = start + target;
      long diff = number - tree[currIndex];
      while(currIndex > 0){
        tree[currIndex] += diff;
        currIndex /= 2;
      }
    }

    public long query(int qLeft, int qRight){
      return sum(1, 0, end-start-1, qLeft, qRight);
    }

    public long sum(int index, int left, int right, int qLeft, int qRight){
      long sum = 0;
      int mid = left + ((right - left) / 2);

      if(right < qLeft || qRight < left){
        //쿼리의 구간과 겹치는 것이 하나도 없는 경우
        return 0;
      }

      if(qLeft <= left && right <= qRight){
        //쿼리의 구간이 현재 index의 구간을 모두 포함시키는 경우
        return tree[index];
      }
      else{
        sum += sum(index*2, left, mid, qLeft, qRight);
        sum += sum(index*2 + 1, mid+1, right, qLeft, qRight);

        return sum;
      }
    }
  }
}
