import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class B1948 {
	
	static int count;				//���� ������ �� ����
	static boolean[] visited;		//ī��Ʈ �ϴ°� �湮 üũ��
	static Prev[] prevs;
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int numVertex = Integer.parseInt(in.readLine());
		int numEdge = Integer.parseInt(in.readLine());
		
		int[] inDegrees = new int[numVertex+1];					//�������� ���
		List<Edge>[] edges = new LinkedList[numVertex+1];		//�� �������� ����� �������� ����
		prevs = new Prev[numVertex+1];							//���� ������ �� �� �ִ� ������ ����
		count = 0;
		visited = new boolean[numVertex+1];
		
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
			prevs[i] = new Prev();
		}
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));	//���� ���� ����
			inDegrees[to]++;	//���� ���� ����
		}
		
		st = new StringTokenizer(in.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());		//���� ����, ���� ���� �޾ƿ���
		
		Stack<Integer> moveables = new Stack<>();
		moveables.add(start);		//start�������� ��������
		
		int curr;
		while( inDegrees[end] != 0) {
			//��� ������ �� ����
			curr = moveables.pop();	//������ �� �ִ� ������ �ϳ� �����ͼ�
			List<Edge> connected = edges[curr];
			
			for(Edge next: connected) {
				inDegrees[next.vertex]--;	//�������� ���̱�
				if(inDegrees[next.vertex] == 0) {
					//���������� 0�� �Ǿ �� �� ������
					moveables.add(next.vertex);
				}
				
				//���� �������� ����� �������� �����ͼ�
				int nWeight = prevs[curr].weight + next.weight;
				if(prevs[next.vertex].weight < nWeight) {
					//���� ���� �������� ���� �������� ���� ���� �� weight�� ũ��
					prevs[next.vertex].prevs.clear();			//����� ���� �������� ����������
					prevs[next.vertex].prevs.add(curr);			//curr���� �Դٰ� �־�����
					prevs[next.vertex].weight = nWeight;		//����ġ ����
				} 
				else if(prevs[next.vertex].weight == nWeight) {
					//weight�� �����ϸ� -> �׳� �־�����
					prevs[next.vertex].prevs.add(curr);
				}
			}
		}
		
		//�Ÿ� ���� ���
		sb.append(prevs[end].weight).append('\n');
		calcEdge(end);	//���⼭���� �ڷ� ���鼭 ����� ������ ������ ��������
		sb.append(count).append('\n');
		
		System.out.println(sb.toString());
	}
	
	static void calcEdge(int curr) {
		//����� ���ε� ���� ����
		visited[curr] = true;	//�湮�ߴٰ� �ֱ�
		count += prevs[curr].prevs.size();	//����� ���ε� ���� �ֱ�
		for(int prev : prevs[curr].prevs) {
			if(visited[prev]) continue;	//�̹� �湮�� ���̸� ���� �ʴ´�
			calcEdge(prev);
		}
	}
	
	static class Prev{
		List<Integer> prevs;
		int weight;
		
		public Prev() {
			this.prevs = new LinkedList<>();
			this.weight = 0;
		}
	}
	
	static class Edge{
		int vertex;
		int weight;
		
		public Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
	}
}
