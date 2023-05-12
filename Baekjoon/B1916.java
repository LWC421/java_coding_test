import java.io.*;
import java.util.*;

public class B1916 {
	
	static final int LIMIT = 1000*100_000 + 1;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int numCity = Integer.parseInt(in.readLine());
		int numEdge = Integer.parseInt(in.readLine());
		
		List<Edge>[] edges = new LinkedList[numCity+1];	//���� ���� ����, 0�� ���X
		int[] weights = new int[numCity+1];	//start���� ���µ� �ʿ��� ���� ����
		for(int i = 1; i <= numCity; i++) {
			edges[i] = new LinkedList<>();
			weights[i] = LIMIT;	//ó�� �Ÿ������� LIMIT�� �ϱ�
		}
		
		
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			edges[from].add(new Edge(to, weight));	//���� �־��ֱ�
		}
		//���� ���� �ޱ� ����
		
		//��� ������ �ޱ�
		st = new StringTokenizer(in.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int finish = Integer.parseInt(st.nextToken());
		//��� �Է� ����
		
		weights[start] = 0;	//�ϴ� �ڱ��ڽ��� 0�̴�
		
		PriorityQueue<Data> pq = new PriorityQueue<>(); 
		pq.add(new Data(start, 0));	//start���� �����ϸ� ������� ����� 0�̴�
		
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			if(curr.vertex == finish) {
				//����������
				System.out.println(curr.weight);
				return;
			}
			
			if(curr.weight > weights[curr.vertex]) {
				//���� �� ������ �������� -> ��������
				continue;
			}
			
			for(Edge connected: edges[curr.vertex]) {
				//���� ������ �����ϴ� �����鿡 ����
				nWeight = curr.weight + connected.weight;	//���� ��ġ���� ���� ���
				if(nWeight < weights[connected.vertex]) {
					//������ �� ��뺸�� ���� ����̸�
					weights[connected.vertex] = nWeight;	//��� �ٲ��ְ�
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
	}
	
	//���� ������ ���� �������� ���µ� �ʿ��� ���
	static class Data implements Comparable<Data>{
		int vertex;
		int weight;
		
		public Data(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
		
		@Override
		public int compareTo(Data other) {
			return this.weight - other.weight;
		}
	}
	
	//���� ����
	static class Edge{
		int vertex;
		int weight;
		
		public Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
	}
}