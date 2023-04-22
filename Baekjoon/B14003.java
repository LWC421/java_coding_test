import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class B14003 {

	static int[] inputs;
	static int[] positions;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int numNumber = Integer.parseInt(in.readLine());
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		inputs = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		int length = LIS(inputs);
		sb.append(length).append('\n');
		
		Stack<Integer> stack = new Stack<>();
		int curr = length-1;
		
		for(int i = numNumber-1; i >= 0; i--) {
			if(positions[i] == curr) {
				//만약 넣어야되는 거면
				stack.add(inputs[i]);
				curr--;
			}
		}
		while( !stack.isEmpty()) {
			sb.append(stack.pop()).append(" ");
		}
		
		System.out.println(sb.toString());
	}
	
	public static int LIS(int[] inputs) {
		int[] lis = new int[inputs.length];
		positions = new int[inputs.length];
		
		int lisLength = 0;
		lis[0] = inputs[0];
		
		
		for(int i = 1; i < inputs.length; i++) {
			if(lis[lisLength] < inputs[i]) {
				//더 큰 값일 경우
				lis[++lisLength] = inputs[i];
				positions[i] = lisLength;
			} else {
				//더 작은 값일 경우 넣을 수 있는 지점을 찾자
				int targetIndex = lower(lis, 0, lisLength, inputs[i]);
				lis[targetIndex] = inputs[i];
				positions[i] = targetIndex;
			}
		}
		
//		System.out.println(Arrays.toString(positions));
		
		return lisLength + 1;
	}
	
	public static int lower(int[] lis, int left, int right, int target) {
		int mid;
		
		while(left < right) {
			mid = left + ((right -left) / 2);
			if(lis[mid] >= target) {
				right = mid;
			} else {
				left = mid+1;
			}
		}
		
		
		return left;
	}
}
