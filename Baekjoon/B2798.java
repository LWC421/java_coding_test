import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2798 {
	
	static int N;		//N���� ī�� �� 3���� �̴´�
	static int M;		//�� ī���� ���� M�� ������ �ȵȴ�
	static int[] cards;	//���ڰ� ������ ī���
	static int sum;
	static int max;
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		cards = new int[N];
		
		//ī�� �Է±��� �ޱ�
		st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < N; i++) {
			cards[i] = Integer.parseInt(st.nextToken());
		}
		
		combi(0, 0);
		System.out.println(max);
	}
	
	public static void combi(int count, int start) {
		if(sum > M) {	//���� ���� ���� ��� pass�ؾ��Ѵ�
			return;
		}
		if(count == 3) {	//3�� �� ���� ���
			max = Math.max(max, sum);	//���� ���� ����  ���� max�� ����� �� ū��
			return;
		}
		
		//N�� �߿� �̱�
		for(int i = start; i < N; i++) {
			sum += cards[i];
			
			combi(count + 1, i + 1);
			
			sum -= cards[i];	//�ǵ�����
		}
	}
}
