import java.io.*;
import java.util.*;

public class B2042 {

	public static void main(String[] args) throws Exception{
		
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numNumber = Integer.parseInt(st.nextToken());
		int numCommand = Integer.parseInt(st.nextToken()) +  Integer.parseInt(st.nextToken());
		
		
		long[] inputs = new long[numNumber];
		
		//초기값 받기
		long number;
		for(int i = 0; i < numNumber; i++) {
			number = Long.parseLong(in.readLine());
			inputs[i] = number;
		}
		SegmentTree tree = new SegmentTree(numNumber, inputs);
		
		
		long command, C; 
		int B;
		for(int i = 0; i < numCommand; i++) {
			st = new StringTokenizer(in.readLine());
			command = Long.parseLong(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			C = Long.parseLong(st.nextToken());
			if(command == 1) {
				tree.update(B-1, C);							//B번째 값을 C로 변경한다
			} 
			else {
				sb.append(tree.search(B-1, (int)C-1)).append('\n');	// [B ~ C]구간 합 구하기
			}
		}
		
		System.out.println(sb.toString());
	}

	static class SegmentTree{
		long[] tree;
		int LEAF_START;
		int LEAF_END;
		
		int height;
		int inputSize;
		
		public SegmentTree(int size, long[] inputs) {
			int MSB;
			this.inputSize = size;	//몇 개 들어왔었는지 기록
			for(MSB = 31; MSB >= 0; MSB--) {
				if( (size & (1 << MSB)) != 0) {
					break;
				}
			}
			MSB++;	//1개 더하고
			LEAF_START = (int)Math.pow(2, MSB);
			LEAF_END = LEAF_START * 2;
			tree = new long[(int) (LEAF_START * 2) + 1];
			
			//초기값들 leaf노드에 넣기
			
			int curr;
			for(int i = 0; i < size; i++) {
				curr = LEAF_START+i;
				while(curr != 0) {
					tree[curr] += inputs[i];
					curr = curr/2;	//부모노드로 이동
				}
			}
		}
		
		public void update(int index, long value) {
			int curr = LEAF_START + index;
			long diff = value - tree[curr];	//차이값 저장
			
			while(curr != 0) {
				tree[curr] += diff;
				curr /= 2;
			}
		}
		
		public long search(int left, int right) {
			long result = searchChild(1, 0, LEAF_START-1, left, right);	//1번 루트부터 시작해서 left, right를 넣자
			
			return result;
		}
		
		private long searchChild(int index, int start, int end, int left, int right) {
			
			if(left > end || right < start) {
				return 0;
			}
			
			if(left <= start &&  end <= right ) {
				return tree[index];
			}
			int mid = (start+end) / 2;
			
			long leftSum = searchChild(index*2, start, mid, left, right);
			long rightSum = searchChild(index*2+1, mid+1, end, left, right);
			
			return leftSum + rightSum;
		}
	}
}
