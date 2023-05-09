import java.io.*;
import java.util.*;

public class B10868{
	
	final static int LIMIT = 1_000_000_001;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		//������ ����
		int numNumber = Integer.parseInt(st.nextToken());
		//������ ����
		int numQuery = Integer.parseInt(st.nextToken());
	
		int[] inputs = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(in.readLine());
		}
		//�� �Է� ����
		
		MinTree tree = new MinTree(numNumber, inputs);
		
		//���� �Է�
		int left, right;
		for(int i = 0; i < numQuery; i++) {
			st = new StringTokenizer(in.readLine());
			left = Integer.parseInt(st.nextToken()) - 1;	//���ϰ� �ϱ����� -1��
			right = Integer.parseInt(st.nextToken()) - 1;	//-1�ؼ� �ϱ�
			
			sb.append(tree.search(left, right)).append('\n');
		}
		System.out.println(sb.toString());
	}
	
	static class MinTree{
		int[] tree;
		int LEAF_START;	//��������� ���� index
		int LAST_INDEX;	//������ �ε���
		int size;
		int start;
		int end;
		
		public MinTree(int size, int[] inputs) {
			this.size = size;	//�Է� ���� ���� �ޱ�
			
			int height = (int)Math.ceil((Math.log(size) / Math.log(2)));
			LEAF_START = (int)Math.pow(2, height);
			LAST_INDEX = LEAF_START + size - 1;
			
			tree = new int[(int)Math.pow(2, height+1)];
			for(int i = 1, limit=LEAF_START*2; i < limit; i++) {
				tree[i] = LIMIT;
			}
			
			int curr;
			for(int i = 0; i < size; i++) {
				curr = LEAF_START + i;	//���⼭ �����ؼ�
				while(curr >= 1) {
					//�θ�� �ö󰡸鼭 ����
					if(tree[curr] > inputs[i]) {
						tree[curr] = inputs[i];
					}
					curr /= 2;	
				}
			}
			
			//�� Ʈ���� ù �������� �� ���� �־��ֱ�
			start = 0;
			end = LEAF_START - 1;
		}
		
		public int search(int left, int right) {
			//ó������ start ~ end�� ���� �����Ѵ�
			return search(1, left, right, start, end);	
		}
		
		private int search(int index, int left, int right, int start, int end) {
			if(left > end || right < start) {
				//������ �����
				return LIMIT;
			}
			
			if(left <= start && end <= right) {
				//���� �ȿ� ���� ���
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
