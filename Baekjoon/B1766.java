import java.io.*;
import java.util.*;

public class B1766 {
	
	static int[] degrees;		//���� ���
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{

//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int numQ = Integer.parseInt(st.nextToken());
		int numEdge = Integer.parseInt(st.nextToken());
		
		List<Integer>[] edges = new LinkedList[numQ+1];
		
		for(int i = 1; i <= numQ; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int[] inDegrees = new int[numQ +1 ];	//0���� ������� �ʴ´�
		
		int from, to;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			
			inDegrees[to]++;	//���� ���� ����
			
			edges[from].add(to);
		}
		
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
			return a - b;
		});
		boolean[] visited = new boolean[numQ+1];
		
		//���� �ذ���Ѱ� ������
		int curr;
		for(int i = 1; i <= numQ; i++) {
			if(!visited[i] && inDegrees[i] == 0) {
				//���������� 0�̸� �� �������� Ǯ��� �Ѵ�
				visited[i] = true;	//�� �������� Ǯ��� �Ѵ�
				pq.add(i);		//���� ������������ �����ؼ�
				
			}
			
			while( !pq.isEmpty() && pq.peek() <= i) {
				curr = pq.poll();	//�ϳ� ������
				sb.append(curr).append(' ');
				
				for(int next: edges[curr]) {
					inDegrees[next] = inDegrees[next] - 1;
					if(inDegrees[next] == 0) {
						//���� ������ 0�� �Ǹ�
						visited[next] = true;	//�湮 �����̴�
						pq.add(next);
					}
				}
			}
		}
		
//		while( !pq.isEmpty() ) {
//			sb.append(pq.poll()).append(' ');
//		}
		
		System.out.println(sb.toString());
	}
}
