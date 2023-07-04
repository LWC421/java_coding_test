import java.io.*;
import java.util.*;

public class B1806 {
	
	//10,000이하의 자연수
	
	static int inputLength;
	static long minNumber;
	static int[] inputs;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
	
		inputLength = Integer.parseInt(st.nextToken());		//입력되는 수의 길이
		minNumber = Long.parseLong(st.nextToken());		//합의 최소 숫자
		
		inputs = new int[inputLength];
		
		st = new StringTokenizer(in.readLine());
		for(int i = 0; i < inputLength; i++) {
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		//마지막까지 고려 될때까지 반복
		
		// 현재 최소수 만족 -> 길이 재고 왼쪽거 빼기
		// 현재 최소수 불만족 -> 오른쪽거 넣기
		
		int minLength = 100_000_001;	//만족하는 수열의 최소 길이
		long currSum = 0;		//구간에 대한 합
		currSum += inputs[0];	//일단 처음수 더하고 시작
		int left = 0;
		int right = 1;
		int length;
		
		while(left != inputLength) {
			//왼쪽커서가 마지막으로 갈때까지
			if(currSum >= minNumber) {
				//합이 조건을 만족 하면
				length = right - left;	//길이 재기
				if(length < minLength) {
					//더 짧은 길이의 수열이면
					minLength = length;
				}
				
				//최소수를 만족했으면 왼쪽거를 빼고 커서 이동시키기
				currSum -= inputs[left];
				left++;
			}
			else {
				//합이 조건을 만족 못 시키면 -> 현재 오른쪽 부분을 넣자
				if(right >= inputLength) {
					break;	//현재 오른쪽이 범위를 벗어나면 그만하자
				}
				
				currSum += inputs[right];
				right++;
			}
		}
		
		System.out.println(minLength == 100_000_001 ? 0 : minLength);
	}
}
