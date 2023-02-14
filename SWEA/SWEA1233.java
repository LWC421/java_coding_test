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
		
		int T = 10;	//�׽�Ʈ ���̽�
		int N;		//������ ��
		StringTokenizer st = null;
		Tree tree = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(in.readLine());
			tree = new Tree(N);

			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(in.readLine(), " ");
				st.nextToken();	//����� ��ȣ�� ���´�
				tree.add(st.nextToken());	//�ش� �����Ͱ� ���´�
//				st.nextToken();	//���� �ڽ� ��ȣ
//				st.nextToken();	//���� �ڽ� ��ȣ
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
		int result;	//��� ����

		public Tree(int size) {
			this.size = size;
			nodes = new Object[size+1];
			lastIndex = 0;
			result = 1;	//�ϴ� �´ٰ� �ϱ�
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

				//�ڽ� ��尡 �׻� �ΰ��� �ִ� -> ������尡 �ƴϴ� -> �������̾�� �Ѵ�
				if(current * 2 + 1 <= lastIndex) {
					//�����ڰ� �ƴ� ���
					if( !nodes[current].equals("*") &&
							!nodes[current].equals("/") && 
							!nodes[current].equals("+") && 
							!nodes[current].equals("-") ) {
						result = 0;	//��� �Ұ����̶� �ϱ�
						return;
					}
					q.add(current*2);
					q.add(current*2 + 1);
				}
				else {	//���� ����� ����̴�
					//�������� ���
					if( nodes[current].equals("*") ||
						nodes[current].equals("/") || 
						nodes[current].equals("+") || 
						nodes[current].equals("-") ) {
						result = 0;	//��� �Ұ����̶� �ϱ�
						return;
					}
				}
			}
		}
	}
}
