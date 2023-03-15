import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class B1477 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int numCenter = Integer.parseInt(st.nextToken()); //N, 현재 휴게소의 개수 [0 ~ 50]
		int moreCenter = Integer.parseInt(st.nextToken()); //M, 더 지으려는 휴게소 개수 [1 ~ 100]
		int length = Integer.parseInt(st.nextToken()); //L, 고속도로의 길이 [100 ~ 1000]
	
		int[] centers = new int[numCenter+2];
		centers[0] = 0;
		centers[numCenter+1] = length;
		st = new StringTokenizer(in.readLine());
		int num;
		for(int i = 1; i <= numCenter; i++) {
			//휴게소 위치들 받기
			num = Integer.parseInt(st.nextToken());
			centers[i] = num;
		}
		
		Arrays.sort(centers);
		
		int left = 1;
		int right = length;
		int mid;
		int distance;
		int count;
		while(left <= right) {
			count = 0;
			mid = (left+right) / 2;	//휴게소의 간격을 정해보자
			for(int i = 1, limit=numCenter+2; i < limit; i++) {
				distance = centers[i] - centers[i-1];	//두개 간의 거리를 확인해보자
				count += ((distance-1) / mid);			//두 휴게소 간의 거리 간격에 몇 개의 휴게소를 더 추가해야 하는지 확인
			}
			if(count > moreCenter) {
				//세워야 하는 휴게소의 수가 한도보다 많다 -> 휴게소 간의 간격을 늘려야 한다
				left = mid+1;
			}
			else {
				//세워야 하는 휴게소의 수가 한도보다 적다 -> 휴게소 간의 간격을 줄여봐도 된다
				right = mid-1;
			}
		}
		
		System.out.println(left);
	}
}
