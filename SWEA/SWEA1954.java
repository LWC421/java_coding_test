import java.util.Scanner;

public class SWEA1954 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
			int N = sc.nextInt();

			int[][] map = new int[N][N];	//N * N �� ����

			//�����̴� �� ����
			int[] dy = { 0, 1, 0, -1 };
			int[] dx = { 1, 0, -1, 0 };
			int curD = 0;	//���� ��� �̵��Ұ���
			int cur = 1;

			//���� ��ġ
			int curY = 0;
			int curX = 0;

			int nextY = 0;
			int nextX = 0;

			while(true) {
				map[curY][curX] = cur++;	//���� ��ġ�� �� �ֱ�

				//���� ��ġ ����
				nextY = curY + dy[curD];
				nextX = curX + dx[curD];

				//���� �ٲ�� �Ǵ��� Ȯ�� �غ���
				if(nextY == N) {
					curD = (curD + 1) % 4;				//Y�� �ǹؿ� ������ ���
				}
				else if(nextX == N || nextX == -1) {	//X�� �¿�� ����� ���
					curD = (curD + 1) % 4;
				}
				else if(map[nextY][nextX] > 0) {		//���ڰ� �̹� �ִ� ��쿡��
					curD = (curD + 1) % 4;
				}

				//���� ��ġ�� ����
				curY += dy[curD];
				curX += dx[curD];


				if(cur == N*N + 1) {
					break;	//��ä���� -> Ż������
				}
			}

			StringBuilder sb = new StringBuilder("");
			for(int[] row: map) {
				for(int c: row) {
					sb.append(c).append(" ");
				}
				sb.append("\n");
			}

			System.out.printf("#%d\n", test_case);
			System.out.print(sb);
		}

	}
}
