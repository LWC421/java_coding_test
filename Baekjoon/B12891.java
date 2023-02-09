import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B12891 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int S = Integer.parseInt(st.nextToken());	//���ڿ� ����
		int P = Integer.parseInt(st.nextToken());	//�κй��ڿ��� ����
		
		String dna = in.readLine();

		st = new StringTokenizer(in.readLine(), " ");
		//�� ���ڿ� ���� �ּ� ���� �޾ƿ���
		int A = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int G = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());

		//���� ���Ե� ������ ���� 
		int curA = 0;
		int curC = 0;
		int curG = 0;
		int curT = 0;
		
		//���� ��� ��й�ȣ�� ��������
		int count = 0;

		//���� ���ڰ� ��������
		char cur = '\u0000';
		//P���� ��ŭ �켱 A, C, G, T�� ��� �ִ��� Ȯ���غ���
		for(int i = 0; i < P; i++) {
			cur = dna.charAt(i);
			if(cur == 'A') curA++;
			else if(cur == 'C') curC++;
			else if(cur == 'G') curG++;
			else if(cur == 'T') curT++;
		}
		
		//��й�ȣ�� �����ϸ�
		if(curA >= A && curC >= C && curG >= G && curT >= T) {
			count++;
		}
		
		//���� ���ڰ� ��������
		char prev = '\u0000';
		//P���� ������ ���ڱ��� Ȯ��
		for(int i = P; i < S; i++) {
			prev = dna.charAt(i-P);
			cur = dna.charAt(i);

			//�����Ŵ� ���ֱ�
			if(prev == 'A') curA--;
			else if(prev == 'C') curC--;
			else if(prev == 'G') curG--;
			else if(prev == 'T') curT--;
			
			//�̹��Ŵ� �����ֱ�
			if(cur == 'A') curA++;
			else if(cur == 'C') curC++;
			else if(cur == 'G') curG++;
			else if(cur == 'T') curT++;
			
			//��й�ȣ�� �����ϸ�
			if(curA >= A && curC >= C && curG >= G && curT >= T) {
				count++;
			}
		}
		
		System.out.println(count);
	}

}


