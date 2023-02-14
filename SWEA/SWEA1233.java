import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA1233 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		
		int T = 10;	//테스트 케이스
		int N;		//정점의 수
		StringTokenizer st = null;
		Tree tree = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(in.readLine());
			tree = new Tree(N);

			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(in.readLine(), " ");
				st.nextToken();	//노드의 번호가 나온다
				tree.add(st.nextToken());	//해당 데이터가 나온다
//				st.nextToken();	//좌측 자식 번호
//				st.nextToken();	//우측 자식 번호
			}
			tree.bfs();
			sb.append("#").append(test_case).append(" ").append(tree.result).append("\n");
		}
		System.out.println(sb.toString());
	}

	static class Tree{
		Object[] nodes;
		int lastIndex;
		int size;
		int result;	//결과 저장

		public Tree(int size) {
			this.size = size;
			nodes = new Object[size+1];
			lastIndex = 0;
			result = 1;	//일단 맞다고 하기
		}

		public void add(String t) {
			nodes[++lastIndex] = t;
		}

		public void bfs() {
			int current = 1;
			Queue<Integer> q = new ArrayDeque<>();
			q.add(current);

			while( !q.isEmpty() ) {
				current = q.poll();

				//자식 노드가 항상 두개씩 있다 -> 리프노드가 아니다 -> 연산자이어야 한다
				if(current * 2 + 1 <= lastIndex) {
					//연산자가 아닐 경우
					if( !nodes[current].equals("*") &&
							!nodes[current].equals("/") && 
							!nodes[current].equals("+") && 
							!nodes[current].equals("-") ) {
						result = 0;	//계산 불가능이라 하기
						return;
					}
					q.add(current*2);
					q.add(current*2 + 1);
				}
				else {	//리프 노드인 경우이다
					//연산자인 경우
					if( nodes[current].equals("*") ||
						nodes[current].equals("/") || 
						nodes[current].equals("+") || 
						nodes[current].equals("-") ) {
						result = 0;	//계산 불가능이라 하기
						return;
					}
				}
			}
		}
	}
}
