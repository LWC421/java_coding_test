import java.io.*;
import java.util.*;

public class B7578 {
	
	static int[] inputs;
	static int[] sorted;
	static long swapCount;	//merge sort에서 스왑횟수 저장

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		Map<Integer, Integer> numToIndex = new HashMap<>();
		swapCount = 0;
		
		int numMachine = Integer.parseInt(in.readLine());
		
		inputs = new int[numMachine];
		sorted = new int[numMachine];
		
		int index = 0;
		st = new StringTokenizer(in.readLine(), " ");
		int machineNum;
		for(int i = 0; i < numMachine; i++) {
			machineNum = Integer.parseInt(st.nextToken());
			numToIndex.put(machineNum, index++);
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < numMachine; i++) {
			machineNum = Integer.parseInt(st.nextToken());
			index = numToIndex.get(machineNum);	//index로 변경
//			fromTo[index] = i;		//index -> 위 기계, i는 아래 기계를 의미한다
			inputs[i] = index;		//i -> 위 기계, index는 아래 기계
		}
		
		mergeSort(0, numMachine-1);
		System.out.println(swapCount);
	}
	
	public static void mergeSort(int left, int right) {
		if(left < right) {
			int mid = left + (right - left)/2;
			mergeSort(left, mid);
			mergeSort(mid+1, right);
			merge(left, mid, right);
		}
	}
	
	public static void merge(int left, int mid, int right) {
		int lPoint = left;	
		int rPoint = mid+1;	//이동할 포인터들 정의
		
		int sPoint = left;	//정렬된 배열의 포인터를 정의
		
		while(lPoint <= mid && rPoint <= right) {
			
			//더 작은 값을 가져오기
			if(inputs[lPoint] < inputs[rPoint]) {
				sorted[sPoint++] = inputs[lPoint++];
			}else {
				//오른쪽에 더 작은값이 있었으면 -> swap해야하므로 그만큼 더해줘야한다
				sorted[sPoint++] = inputs[rPoint++];
				swapCount += mid - lPoint + 1;	//왼쪽부분에 남은 개수만큼 더해줘야한다
			}
		}
		
		//남은 값들 넣어주기
		while(lPoint <= mid) {
			sorted[sPoint++] = inputs[lPoint++];
		}
		while(rPoint <= right) {
			sorted[sPoint++] = inputs[rPoint++];
		}
		
		for(int i = left; i <= right; i++) {
			inputs[i] = sorted[i];
		}
	}
}
