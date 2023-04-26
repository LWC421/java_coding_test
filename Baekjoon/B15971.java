import java.io.*;
import java.util.*;

public class B15971 {

	//N���� ���� �ִ�
	// edge�� N-1���̴�
	// ����Ŭ�� ����
	// ��� ������ ����Ǿ� �ִ�
	
	// ���� ���� �Ǵ� �ϳ��� ������ �糡�� �־�߸� ����� �����ϴ�
	
	static List<Edge>[] edges;		//���� ���� ����
	static int[] prevs;			//���� ������ ���µ� ������ �湮�� ������ ���� ����
	static int[] distances;		//�Ÿ� ���� ����
	static Map<FromTo, Integer> fromTo;
	
	static final int LIMIT = 100_000 * 1_000 + 1;
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		//������ 100_000��
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numVertex = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		edges = new List[numVertex+1];		//0���� ������� �ʴ´�
		prevs = new int[numVertex+1];		//0���� ������� �ʴ´�
		distances = new int[numVertex+1];	//0���� ������� �ʴ´�
		fromTo = new HashMap<>();
		
		//��� ��� �Ÿ� ���� �ʱ�ȭ
		for(int i = 1; i <= numVertex; i++) {
			distances[i] = LIMIT;
		}
		
		
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int from, to, weight;
		for(int i = 1; i < numVertex; i++) {
			st = new StringTokenizer(in.readLine());
			
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));
			edges[to].add(new Edge(from, weight));
			fromTo.put(new FromTo(from, to), weight);
			fromTo.put(new FromTo(to, from), weight);
		}
		//�Է� ����
		
		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(start, 0));	//start�������� ��������
		distances[start] = 0;	//ù ��ġ �Ÿ� 0�̴�
		
		
		//���ͽ�Ʈ�� ������
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge edge: edges[curr.vertex]) {
				//����� ����� ������ ����
				nWeight = curr.weight + edge.weight;
				if(distances[edge.vertex] > nWeight) {
					//���°� �̵��̸�
					prevs[edge.vertex] = curr.vertex;	//���� ��ġ�� ������ �����̶�� ǥ���س���
					distances[edge.vertex] = nWeight;	//�Ÿ����� �ֱ�
					pq.add(new Data(edge.vertex, nWeight));
					
				}
			}
		}
		
		int currVertex = end;
		int prev = end;
		
		int maxWeight = 0;
		while(prev != start) {
			prev = prevs[currVertex];
			
			int currWeight = fromTo.get(new FromTo(prev, currVertex));
			
			if(currWeight > maxWeight) {
				maxWeight = currWeight;
			}
			
			currVertex = prev;
		}
		
		System.out.println(distances[end] - maxWeight);
	}
	
	static class FromTo{
		int from;
		int to;
		
		public FromTo(int from, int to) {
			this.from = from;
			this.to = to;
		}
	
		@Override
		public int hashCode() {
			return from*100_001 + to;
		}
		
		@Override
		public boolean equals(Object o) {
			FromTo other = (FromTo)o;
			return this.from == other.from && this.to == other.to;
		}
	}
	
	static class Data implements Comparable<Data>{
		int vertex;
		int weight;
		
		public Data(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Data other) {
			return this.weight - other.weight;
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
