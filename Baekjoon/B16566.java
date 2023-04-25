import java.io.*;
import java.util.*;

public class B16566 {
	
	final static int MAX_POW = 22;	//2 ^ 22 = 4,194,304
	
	// N logN -> 88,000,000

	public static void main(String[] args) throws Exception{
		//N���� ���� ī��, 1 ~ N�� �����ִ�
		// �� �� M�� ����
		
		//N���� �Ķ� ī��, 1 ~ N��
		// ���������� �� ��ȣ�� ������ M���� ����
		
		//ö���� ������
		//�μ��� �Ķ���
		
		// K�� ���� ī�带 ���� �� ū ���� ����� �̱�� �����̴� (�� �� �� ī��� ���X)
		
		//ö���� �� ī�尡 �־�����
		// �μ��� � ī�带 ���� ���
		
		// N�ڷᱸ�� ����
		// 10

		
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numMax = Integer.parseInt(st.nextToken());		// 1 ~ numMax����
		int numCandi = Integer.parseInt(st.nextToken());	// �� ���� ī�带 ����
		int numRound = Integer.parseInt(st.nextToken());	// �� ���� ���带 ������ ��
		
		int[] inputs = new int[numCandi];
		
		st = new StringTokenizer(in.readLine());
		TreeSet<Integer> set = new TreeSet<>();
		
		//�μ��� ����� ī��� �ޱ�
		
		for(int i = 0; i < numCandi; i++) {
//			set.add(Integer.parseInt(st.nextToken()));
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(inputs);	//����� �� �ִ� ī����� ��������
		
		Card[] cards = new Card[numMax+1];	//1 ~ N������ ī����� ������ �����ؾ��Ѵ�
		
		for(int i = 1; i <= numMax; i++) {
			cards[i] = new Card();
		}
		
		int prev = 1;
		List<Integer> prevs = new LinkedList<>();
		for(int i = 0; i < numCandi; i++) {
			//�Է¹��� ī��鿡 ����
			int curr = inputs[i];
			for(int j = prev; j < curr; j++) {
				//������� �����ֱ�
				cards[j].next = curr;
			}
			cards[curr].alived = true;	//�� ī��� �Է����� ���� ī���� ǥ��
			
			prev = curr;	//������ �־��ֱ�	
		}
		
//		for(int i = 1; i <= numMax; i++) {
//			System.out.println(cards[i]);
//		}
		

		StringBuilder sb = new StringBuilder("");
		
		st = new StringTokenizer(in.readLine());
		
		//�ϳ��ϳ��� ���带 ��������
		int target;
		for(int i = 0; i < numRound; i++) {
			target = Integer.parseInt(st.nextToken());
			
			Card curr = cards[target];
			int currValue = curr.next;
			curr = cards[curr.next];	//���� ī���� ���� ī����� �����ؼ�
			while(curr.alived == false) {
				//���� �̹� ����� ī���̸�
				currValue = curr.next;
				curr = cards[curr.next];
			}
			sb.append(currValue).append('\n');
			curr.alived = false;	//����� ī���� ǥ��
		}
		
		System.out.println(sb.toString());
	}
	
	static class Card{
		boolean alived;		//true�� ��� ���� �ִ� ī���� �Ҹ���
		int next;
		
		@Override
		public String toString() {
			return String.format("%s -> %d", alived == true ? "alive" : "dead", next);
		}
	}
}
