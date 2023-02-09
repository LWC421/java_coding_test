import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA5215 {
	static int N;			//����� ��
	static int L;			//���� Į�θ�
	static int[][] zeryo;	//[����, Į�θ�]�� �̷���� �迭
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken()) + 1;	//����� ���� -> ���ϰ� �ϱ� ���� +1 �Ͽ���
			L = Integer.parseInt(st.nextToken()) + 1;	//���� Į�θ� -> ���ϰ� �ϱ� ���� +1 �Ͽ���
			
			//��� ���� �ʱ�ȭ
			//���ϰ� �ϱ����� 0���� �Ⱦ���
			zeryo = new int[N][2];
			for(int i = 1; i < N; i++) {
				st = new StringTokenizer(in.readLine());
				zeryo[i][0] = Integer.parseInt(st.nextToken());
				zeryo[i][1] = Integer.parseInt(st.nextToken());
			}
			
			//���� �� ��ῡ ���� 
			//���� ���� Į�θ��� ����
			//��, ���� �ش��ϴ� ���� �׶��� �ִ� ������ ��Ÿ����
			int[][] map = new int[N][L];			
			
			//���� ���õ� ����� ���� �� Į�θ�
			int curScore = 0;
			int curCal = 0;
			
			
			for(int y = 1; y < N; y++) {	//��ῡ ����
				for(int x = 1; x < L; x++) {	//Į�θ��� ����
					curScore = zeryo[y][0];
					curCal = zeryo[y][1];
					
					if( (x-curCal) > 0 && (map[y-1][x-curCal] + curScore) > map[y-1][x]) {	//���� ������ ��Ḧ �߰��ϴ� �� �� ���� ���
						int currentMax = map[y-1][x-curCal] + curScore;
						map[y][x] = currentMax;	//���� ����
					}
					else {	//�ش� ��Ḧ �߰����� �ʴ´�
						map[y][x] = map[y-1][x];
					}
				}
			}
			
			sb.append("#").append(test_case).append(" ").append(map[N-1][L-1]).append("\n");
		}
		
		System.out.println(sb);
	}
}
