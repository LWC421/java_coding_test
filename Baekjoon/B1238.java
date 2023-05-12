import java.io.*;
import java.util.*;

public class B1238 {

	static final int LIMIT = 1000 * 100 + 1;
	static List<Edge>[] edges;
	static int[][] weights;		//[from][to]���� ���� ����� ����

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		int numVertex = Integer.parseInt(st.nextToken());	//������ ����
		int numEdge = Integer.parseInt(st.nextToken());		//������ ����
		int finish = Integer.parseInt(st.nextToken());		//������

		edges = new LinkedList[numVertex+1];	//0���� ����
		weights = new int[numVertex+1][numVertex+1];
		
		for(int y = 1; y <= numVertex; y++) {
			edges[y] = new LinkedList<>();
			for(int x = 1; x <= numVertex; x++) {
				// �Ÿ���� �ִ������� �ʱ�ȭ
				weights[y][x] = LIMIT;
			}
		}

		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			//���� ���� �ޱ�
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());

			edges[from].add(new Edge(to, weight));	//���� �ֱ�
		}
		//��� �Է� ����

		PriorityQueue<Data> pq = new PriorityQueue<>();
		Data curr = null;
		int nWeight;
		for(int start = 1; start <= numVertex; start++) {
			//start���� �����ؼ� ���ͽ�Ʈ�� �ϱ�
			pq.add(new Data(start, 0));	//start���� �����ϴ� �Ÿ� �ϳ� ���
			weights[start][start] = 0;	//�ڱ��ڽ����� ���� ����� 0�̴�
			
			while( !pq.isEmpty() ) {
				curr = pq.poll();
				if(curr.vertex == finish) {
					//�������� ���� ������
					pq.clear();
					break;
				}
				
				for(Edge connected: edges[curr.vertex]) {
					//����� ����� ������ ����
					nWeight = curr.weight + connected.weight;	//�������� ���� ��� ���
					if(nWeight < weights[start][connected.vertex]) {
						//�� ����Ÿ�� ���°� �̵��̸�
						 weights[start][connected.vertex] = nWeight;
						 pq.add(new Data(connected.vertex, nWeight));
					}
				}
			}
		}
		
		//�����⵵ �ѹ� ���������
		pq.add(new Data(finish, 0));
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				//����� ����� ������ ����
				nWeight = curr.weight + connected.weight;	//�������� ���� ��� ���
				if(nWeight < weights[finish][connected.vertex]) {
					//�� ����Ÿ�� ���°� �̵��̸�
					 weights[finish][connected.vertex] = nWeight;
					 pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
		
		int result = 0;
		for(int i = 1; i <= numVertex; i++) {
			int targetWeight =  weights[i][finish] + weights[finish][i];
			result = Math.max(result, targetWeight);
		}
		
		System.out.println(result);
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

		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
	}

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
