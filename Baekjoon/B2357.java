import java.io.*;
import java.util.*;

public class B2357 {
	
	final static int LIMIT = 1_000_000_001;

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numNumber = Integer.parseInt(st.nextToken());	//입력 된 수의 개수
		int numQuery = Integer.parseInt(st.nextToken());	//입력 될 쿼리의 개수

		int[] inputs = new int[numNumber];
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(in.readLine());
		}
		//수 입력 종료
		
		MinMaxTree minMaxTree = new MinMaxTree(numNumber, inputs);
		
		int left, right;
		for(int i= 0; i < numQuery; i++) {
			st = new StringTokenizer(in.readLine());
			left = Integer.parseInt(st.nextToken()) - 1; 
			right = Integer.parseInt(st.nextToken()) - 1;
			MinMax minMax = minMaxTree.search(left, right);
			sb.append(minMax.min).append(" ").append(minMax.max).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	static class MinMaxTree{
		MinMax[] tree;
		int LEAF_START;
		int size;
		
		public MinMaxTree(int size, int[] inputs) {
			this.size = size;
			
			int MSB;
			for(MSB = 31; MSB >= 0; MSB--) {
				if( (size & (1 << MSB)) != 0) {
					break;
				}
			}
			
			MSB++;
			LEAF_START = (int)Math.pow(2, MSB);
			
			tree = new MinMax[LEAF_START*2 + 1];
			
			for(int i = 1; i < tree.length; i++) {
				tree[i] = new MinMax();
			}
			
			for(int i = 0; i < size; i++) {
				int curr = LEAF_START + i;
				while(curr > 0) {
					tree[curr].setMax(inputs[i]);
					tree[curr].setMin(inputs[i]);
					curr /= 2;
				}
			}
		}
		
		public MinMax search(int left, int right) {
			return searchChild(1, 0, LEAF_START-1, left, right, new MinMax());
		}
		
		private MinMax searchChild(int index, int start, int end, int left, int right, MinMax minMax) {
			if(left > end || right < start) {
				return minMax;
			}
			if(left <= start && end <= right) {
				minMax.setMax(tree[index].max);
				minMax.setMin(tree[index].min);
				return minMax;
			}
			
			int mid = (start+end) / 2;
			MinMax leftChild = searchChild(index*2, start, mid, left, right, minMax);
			MinMax rightChild = searchChild(index*2 +1, mid+1, end, left, right, minMax);
			
			minMax.setMax(leftChild.max);
			minMax.setMax(rightChild.max);
			
			minMax.setMin(leftChild.min);
			minMax.setMin(rightChild.min);
			
			return minMax;
		}
	}
	
	static class MinMax{
		int min;
		int max;
		
		public MinMax() {
			this.min = LIMIT;
			this.max = 0;
		}
		
		public void setMin(int value) {
			if(this.min > value) {
				this.min= value;
			}
		}
		
		public void setMax(int value) {
			if(this.max < value) {
				this.max= value;
			}
		}
	}
}
