import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B1003 {
	static int N;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(in.readLine());
			
			if(N == 0) {	//�Ǻ� 0�ΰ�츸 ���� ó��
				sb.append(1).append(" ").append(0).append("\n");
				continue;
			}
			
			//N�Ǻ��� ���� 0, 1 ����ϱ�
			int[][] pibos = new int[N+1][2];	//0�� 0�� ��� �Ҹ�����, 1�� 1�� ����Ҹ�����
			pibos[0][0] = 1;
			pibos[1][1] = 1;	//0�Ǻ��� 1�Ǻ� ���� 1�� �ʱ�ȭ
			
			
			for(int i = 2; i <= N; i++) {
				pibos[i][0] = pibos[i-1][0] + pibos[i-2][0];
				pibos[i][1] = pibos[i-1][1] + pibos[i-2][1];
			}
			
			sb.append(pibos[N][0]).append(" ").append(pibos[N][1]).append("\n");
		}
		
		System.out.println(sb);
	}
}
