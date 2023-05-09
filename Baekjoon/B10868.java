import java.io.*;
import java.util.*;

public class B10868{
	
	final static int LIMIT = 1_000_000_001;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		//숫자의 개수
		int numNumber = Integer.parseInt(st.nextToken());
		//쿼리의 개수
		int numQuery = Integer.parseInt(st.nextToken());
	
		int[] inputs = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(in.readLine());
		}
		//수 입력 종료
		
		MinTree tree = new MinTree(numNumber, inputs);
		
		//쿼리 입력
		int left, right;
		for(int i = 0; i < numQuery; i++) {
			st = new StringTokenizer(in.readLine());
			left = Integer.parseInt(st.nextToken()) - 1;	//편하게 하기위해 -1로
			right = Integer.parseInt(st.nextToken()) - 1;	//-1해서 하기
			
			sb.append(tree.search(left, right)).append('\n');
		}
		System.out.println(sb.toString());
	}
	
	static class MinTree{
		int[] tree;
		int LEAF_START;	//리프노드의 시작 index
		int LAST_INDEX;	//마지막 인덱스
		int size;
		int start;
		int end;
		
		public MinTree(int size, int[] inputs) {
			this.size = size;	//입력 받은 개수 받기
			
			int height = (int)Math.ceil((Math.log(size) / Math.log(2)));
			LEAF_START = (int)Math.pow(2, height);
			LAST_INDEX = LEAF_START + size - 1;
			
			tree = new int[(int)Math.pow(2, height+1)];
			for(int i = 1, limit=LEAF_START*2; i < limit; i++) {
				tree[i] = LIMIT;
			}
			
			int curr;
			for(int i = 0; i < size; i++) {
				curr = LEAF_START + i;	//여기서 시작해서
				while(curr >= 1) {
					//부모로 올라가면서 갱신
					if(tree[curr] > inputs[i]) {
						tree[curr] = inputs[i];
					}
					curr /= 2;	
				}
			}
			
			//이 트리의 첫 시작지점 끝 지점 넣어주기
			start = 0;
			end = LEAF_START - 1;
		}
		
		public int search(int left, int right) {
			//처음에는 start ~ end를 전부 포함한다
			return search(1, left, right, start, end);	
		}
		
		private int search(int index, int left, int right, int start, int end) {
			if(left > end || right < start) {
				//범위를 벗어나면
				return LIMIT;
			}
			
			if(left <= start && end <= right) {
				//범위 안에 들어올 경우
				return tree[index];
			}
			int mid = start + (end - start) / 2;
			
			int leftChild = search(index*2, left, right, start, mid);
			int rightChild = search(index*2 + 1, left, right, mid+1, end);
			
			if(leftChild < rightChild) {
				return leftChild;
			}
			else {
				return rightChild;
			}
		}
	}
}
