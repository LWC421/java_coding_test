import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2243 {
	
	static final int DEPTH = 20;
	static final int LEAF_START = 1_048_576; //2^20 이다
	static int[] counts;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		counts = new int[LEAF_START * 2 + 1];
		
		int numCommand = Integer.parseInt(in.readLine());
		for(int c = 0; c < numCommand; c++) {
			st = new StringTokenizer(in.readLine());
			int command = Integer.parseInt(st.nextToken());
			
			if(command == 1) {
				//순위로 사탕 꺼내기
				int surr = Integer.parseInt(st.nextToken());
				sb.append(pop(surr)).append("\n");
			}
			else {
				//맛으로 사탕 넣거나 빼기
				int value = Integer.parseInt(st.nextToken());
				int count = Integer.parseInt(st.nextToken());
				insert(value, count);
			}
		}
		
		System.out.println(sb.toString());
	}
	
	//surr는 순위를 의미
	public static int pop(int surr) {
		int index = 1;		//root부터 시작해서
		int left, right;
		int current = surr;	//순위에 대해 조정해간다
		for(int i = 0; i < DEPTH; i++) {
			counts[index]--;	//한개가 빠질 예정이므로 -1씩하면서 가자
			left = index*2;
			right = index*2 + 1;
			
			if(counts[left] < surr) {
				//왼쪽 노드들의 개수가 적으면 -> 오른쪽으로 가야한다
				surr -= counts[left];	//왼쪽 노드의 개수만큼은 줄이면서 오른쪽으로 가야한다
				index = right;
			} 
			else {
				//왼쪽 노드로 가야하면
				index = left;
			}
		}
		
		counts[index]--;	//LEAF에서도 -1을 줄이고
		return index-LEAF_START + 1;	//맛을 반환해주자
	}
	
	public static void insert(int value, int count) {
		int index = LEAF_START + value - 1;	//여기부터 시작
		while(index > 0) {
			counts[index] += count;	//개수를 증가시키면서 위로 올라가자
			index /= 2;
		}
	}
}
