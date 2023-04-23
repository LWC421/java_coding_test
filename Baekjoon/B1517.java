import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1517 {
	
	static int[] inputs;
	static int[] sorted;
	static long swapCount;

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int numNumber = Integer.parseInt(in.readLine());
		
		inputs = new int[numNumber];
		sorted = new int[numNumber];
		swapCount = 0;
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		mergeSort(0, inputs.length-1);
		
		System.out.println(swapCount);
	}
	
	public static void mergeSort(int left, int right) {
		if(left < right) {
			int mid = left + (right - left) /2;
			mergeSort(left, mid);
			mergeSort(mid+1, right);
			merge(left, mid, right);
		}
	}
	
	public static void merge(int left, int mid, int right) {
		int lPoint = left;
		int rPoint = mid+1;
		int sPoint = left;
		
		while(lPoint <= mid && rPoint <= right) {
			if(inputs[lPoint] <= inputs[rPoint]) {
				sorted[sPoint++] = inputs[lPoint++];
			}
			else {
				swapCount += (mid-lPoint) + 1;
				sorted[sPoint++] = inputs[rPoint++];
			}
		}
		
		while(lPoint <= mid) {
			sorted[sPoint++] = inputs[lPoint++];
		}
		while(rPoint <= right) {
			sorted[sPoint++] = inputs[rPoint++];
		}
		
		for(int s = left; s <= right; s++) {
			inputs[s] = sorted[s];
		}
	}
}
