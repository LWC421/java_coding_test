import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA4008 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		int N;			//������ ����
		int numSum;		//���ϱ��� ����
		int numMinus;	//���̳ʽ��� ����
		int numMult;	//���ϱ��� ����
		int numDiv;		//�������� ����
		
		//���� �����ڸ� �� �� ����ߴ���
		int sum;
		int minus;
		int mult;
		int div;
		
		int[] numbers;	//���ڵ�
		
		int[] tmp;		//ť���� ���� �� �ӽ� ����
		Queue<int[]> q;	//ť ���, [������� ��, ���� ���ߵǴ� ���� �ε���, ����� ���ϱ�, ����� ���̳ʽ�, ����� ���ϱ�, ����� ������]
		int number;		//ť���� ���� ��
		int index;		//ť���� ���� �ε�����
		int max;		//�ִ밪
		int min;		//�ּҰ�
		
		for(int test_case = 1; test_case <= T; test_case++) {
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			
			// -----------�Է� �ޱ� ----------------
			N = Integer.parseInt(in.readLine());
			st  = new StringTokenizer(in.readLine());
			numSum = Integer.parseInt(st.nextToken());
			numMinus = Integer.parseInt(st.nextToken());
			numMult = Integer.parseInt(st.nextToken());
			numDiv = Integer.parseInt(st.nextToken());
			
			numbers = new int[N];
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			// -------------�Է� �ޱ� ��------------
			
			q = new ArrayDeque<>();
//			[������� ��, ���� ���ߵǴ� ���� �ε���, ����� ���ϱ�, ����� ���̳ʽ�, ����� ���ϱ�, ����� ������]
			q.add(new int[] {numbers[0], 1, 0, 0, 0, 0});
			
			
			while( !q.isEmpty() ) {
				//ť���� ������
				tmp = q.poll();
				
				number = tmp[0];
				index = tmp[1];
				sum = tmp[2];
				minus = tmp[3];
				mult = tmp[4];
				div = tmp[5];
				
				if(index == N) {	//�����ϱ�
					min = Math.min(min, number);
					max = Math.max(max, number);
					continue;
				}
				
				if(sum < numSum) {		//���ϱ⸦ ����� �� ������
					q.add(new int[] {number+numbers[index], index+1, sum+1, minus, mult, div});
				}
				if(minus < numMinus) {	//���⸦ ����� �� ������
					q.add(new int[] {number-numbers[index], index+1, sum, minus+1, mult, div});
				}
				if(mult < numMult) {	//���ϱ⸦ ����� �� ������
					q.add(new int[] {number*numbers[index], index+1, sum, minus, mult+1, div});
				}
				if(div < numDiv) {		//�����⸦ ����� �� ������
					q.add(new int[] {number/numbers[index], index+1, sum, minus, mult, div+1});
				}
				
			}
			
			
			// ��� �ϱ�
			sb.append("#").append(test_case).append(" ").append(max - min).append("\n");
		}
		System.out.println(sb.toString());
	}

}
