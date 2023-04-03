import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1263 {
	
	final static int INF = 1000 * 1000 * 2;

	public static void main(String[] args) throws Exception{
		// i -> j�� ������ �����Ѵ�
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder("");
		int T = Integer.parseInt(in.readLine());
		
		StringTokenizer st = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			//�׽�Ʈ ���̽� ����
			st = new StringTokenizer(in.readLine());
			
			int numPerson = Integer.parseInt(st.nextToken());	//N, ����� ��
			int[][] map = new int[numPerson][numPerson];		//�������
			int[][] dp = new int[numPerson][numPerson];			//�÷��̵�-���� ���
			
			int num;
			for(int y = 0; y < numPerson; y++) {
				for(int x = 0; x < numPerson; x++) {
					num = Integer.parseInt(st.nextToken());
					if(num == 0) num = INF;	//������ �ȵǾ� ���� ���
					if(y == x) num = 0;
					map[y][x] = num;
					dp[y][x] = num;
				}
			}
			//�Է� ����
			
			int goAround;	//�����ϴ� ���
			for(int mid = 0; mid < numPerson; mid++) {
				for(int start = 0; start < numPerson; start++) {
					for(int end = 0; end < numPerson; end++) {
						goAround = dp[start][mid] +  dp[mid][end];
						if(goAround < dp[start][end]) {
							//���� �����ϴ°� �� ������
							dp[start][end] = goAround;
						}
					}
				}
			}
			
			int minCC = INF;		//���� ���� CC��
			int rowSum;			//�� ����� CC�� �����
			for(int y = 0; y < numPerson; y++) {
				rowSum = 0;
				for(int x = 0; x < numPerson; x++) {
					rowSum += dp[y][x];
				}
				if(minCC > rowSum) minCC = rowSum;
			}
			
			sb.append(String.format("#%d %d\n", test_case, minCC));
		}
		System.out.println(sb.toString());
	}
}
