import java.io.*;
import java.util.*;

public class B11725 {
	
	static List<Integer>[] edges;	//���� ���� ����
	static int[] parents;			//�θ� ��� ���� ����
	static boolean[] visited;		//�湮 üũ��

	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int numNode = Integer.parseInt(in.readLine());	//����� ����
		
		edges = new LinkedList[numNode+1];	//���� ���� ����
		parents = new int[numNode+1];		//�θ� ��� ����
		visited = new boolean[numNode+1];	//�湮 üũ��

		for(int i = 1; i <= numNode; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int from, to;
		for(int i = 1; i < numNode; i++) {
			//���� ��� �ޱ�
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			edges[from].add(to);
			edges[to].add(from);
		}
		//��� �Է� ����
		
		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);		//1�� ��忡�� ����
		int curr;
		while( !q.isEmpty() ) {
			curr = q.poll();
			
			for(int connected: edges[curr]) {
				//�ش� ��忡�� ����� �����鿡 ����
				if(visited[connected]) continue;	//�̹� �湮�ѰŸ� pass
				parents[connected] = curr;	//�θ� ��� �����ְ�
				q.add(connected);
				visited[connected] = true;	//�湮 �������� ����
			}
		}
		
		for(int i = 2; i <= numNode; i++) {
			sb.append(parents[i]).append('\n');
		}
		
		System.out.println(sb.toString());
	}	
}
