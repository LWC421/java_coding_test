import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2961 {
	static int N;
	static int[][] zeryo;
	static long ssun = 1;	//����
	static long sin = 0;	//�Ÿ�
	static long min = Integer.MAX_VALUE;	//���� ���̰�
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(in.readLine());
		zeryo = new int[N][2];	//[�Ÿ�, ����]�� ������ �迭
		
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			zeryo[i][0] = Integer.parseInt(st.nextToken());
			zeryo[i][1] = Integer.parseInt(st.nextToken());
		}
		
		subset(0);
		
		System.out.println(min);
	}
	
	public static void subset(int count) {
		if(count == N) {	//��� ��ῡ ���� Ȯ�� ������
			if(sin != 0) {	//�ƹ��͵� �� ���� ��츦 �����ϰ�
				min = Math.min(min, Math.abs(ssun - sin));
			}
			
			return;
		}
		
		//��Ḧ �ִ� ���
		ssun *= zeryo[count][0];
		sin += zeryo[count][1];
		
		subset(count+1);
		
		//��Ḧ �� �ִ� ���
		ssun /= zeryo[count][0];
		sin -= zeryo[count][1];
		subset(count+1);
	}
}
