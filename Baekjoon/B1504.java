import java.io.*;
import java.util.*;

public class B1504 {
	
	static final int LIMIT = 800*1_000 + 1;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine(), " ");
		
		int numVertex = Integer.parseInt(st.nextToken());	//���� ����
		int numEdge = Integer.parseInt(st.nextToken());		//���� ����
		
		List<Edge>[] edges = new LinkedList[numVertex+1];	//0�� ��� ����
		int[] startTo = new int[numVertex+1];	//start���� ���� ���� ����
		int[] mid1To = new int[numVertex+1];	//������1���� ���� ���� ����
		int[] mid2To = new int[numVertex+1];	//������2���� ���� ���� ����	
		
		//������ ������ �ʱ�ȭ
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
			startTo[i] = LIMIT;
			mid1To[i] = LIMIT;
			mid2To[i] = LIMIT;
		}
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			//���� ������ �ޱ�
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));
			edges[to].add(new Edge(from, weight));
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		int mid1 = Integer.parseInt(st.nextToken());
		int mid2 = Integer.parseInt(st.nextToken());
		//��� �Է� ����
		
		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(1, 0));	//1������ �����ϴµ� ���������� 0�̴�
		startTo[1] = 0;	//��� �ʱ�ȭ
		
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight;	//�������� ���� ��� ���
				if(nWeight < startTo[connected.vertex]) {
					//���� ����� �� ������
					startTo[connected.vertex] = nWeight;	//��� ������Ʈ
					pq.add(new Data(connected.vertex, nWeight));//pq�� �ֱ�
				}
			}
		}

		pq.add(new Data(mid1, 0));	//������1���� ���� ��� ���ϱ�
		mid1To[mid1] = 0;	//����ʱ�ȭ
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight; 	//�������� ���� ��� ���
				if(nWeight < mid1To[connected.vertex]) {
					//���� ����� �� ������
					mid1To[connected.vertex] = nWeight;	//��� ������Ʈ
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
		pq.add(new Data(mid2, 0));	//������1���� ���� ��� ���ϱ�
		mid2To[mid2] = 0;	//����ʱ�ȭ
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight; 	//�������� ���� ��� ���
				if(nWeight < mid2To[connected.vertex]) {
					//���� ����� �� ������
					mid2To[connected.vertex] = nWeight;	//��� ������Ʈ
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		//��� ��� ���ϱ� ��
		
		int path1 = 0;	//mid1�� ���� �����ϰ� ���� ��
		
		if(startTo[mid1] == LIMIT || mid1To[mid2] == LIMIT || mid2To[numVertex] == LIMIT) {
			//���� ���� �ȵȰ� ������
			path1 = LIMIT;
		}
		else {
			path1 += startTo[mid1];
			path1 += mid1To[mid2];
			path1 += mid2To[numVertex];
		}
		
		
		int path2 = 0;	//mid2�� ���� �����ϰ� ���� ��
		if(startTo[mid2] == LIMIT || mid2To[mid1] == LIMIT || mid1To[numVertex] == LIMIT) {
			path2 = LIMIT;
		}
		else {
			path2 += startTo[mid2];
			path2 += mid2To[mid1];
			path2 += mid1To[numVertex];
		}
		
		int min = Math.min(path1, path2);
		
		if(min == LIMIT) {
			//���� ���� ���� ������
			System.out.println(-1);
			return;
		}
		System.out.println(min);
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