import java.io.*;
import java.util.*;

public class B1043 {
	
	static List<Integer>[] edges; 	//���� �����
	static boolean[] visited;		//�湮 üũ��
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numHuman = Integer.parseInt(st.nextToken());	//����� ��
		int numParty = Integer.parseInt(st.nextToken());	//��Ƽ�� ����
		
		edges = new LinkedList[numHuman+1];	//0���� ������ ���̹Ƿ� +1��
		for(int i = 1; i <= numHuman; i++) {
			edges[i] = new LinkedList<>();
		}
		visited = new boolean[numHuman+1];	//�湮 üũ��
		
		st = new StringTokenizer(in.readLine(), " ");
		
		//���� �ƴ»�� �Է� �ޱ�
		int numTrue = Integer.parseInt(st.nextToken());		//������ �ƴ� ���
		int[] trues = new int[numTrue];				//������ �ƴ� ������� ��ȣ ����
		
		for(int i = 0; i < numTrue; i++) {
			trues[i] = Integer.parseInt(st.nextToken());
		}
		
		//��Ƽ �Է� �ޱ�
		
		List<Integer>[] parties = new LinkedList[numParty];	//��Ƽ �Է¹ޱ�
		
		for(int i = 0; i < numParty; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			
			int numPatience = Integer.parseInt(st.nextToken());	//��Ƽ�� ������
			List<Integer> patience = new LinkedList<>();
			
			for(int j = 0; j < numPatience; j++) {
				//�ش� ��Ƽ�� �����ڵ� �־��ֱ�
				patience.add(Integer.parseInt(st.nextToken()));
			}
			
			//�� ��Ƽ �����ڵ鳢�� �������� �־��ֱ�
			for(int x = 0; x < numPatience; x++) {
				for(int y = x+1; y < numPatience; y++) {
					edges[patience.get(x)].add(patience.get(y));
					edges[patience.get(y)].add(patience.get(x));	//�����⵵ �ֱ�
				}
			}
			
			parties[i] = new LinkedList(patience);	//��Ƽ �ֱ�
		}
		//��� �Է� ����
		Queue<Integer> q = new ArrayDeque<>();
		int curr;
		for(int trueHuman: trues) {
			q.add(trueHuman);	//������ �ƴ� ����� �ְ�
			while( !q.isEmpty() ) {
				//BFS�� ����
				curr = q.poll();
				if(visited[curr]) continue;	//�̹� �湮 ������ �ѱ��
				visited[curr] = true;	//�湮 �������� �ֱ�
				
				for(int connected: edges[curr]) {
					//����� ������ ����
					q.add(connected);
				}
			}
		}
		
		int result = 0;
		loop: for(List<Integer> party: parties) {
			for(int human: party) {
				//������ ��Ƽ�� ������ �����ڵ鿡 ����
				if(visited[human]) {
					//������ �ƴ� ����̸�
					continue loop;
				}
			}
			result++;
		}
		
		System.out.println(result);
		
	}
}