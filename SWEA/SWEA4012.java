import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA4012 {
	static int N;	    	//������� ����
	static int limit;		//����� �������� ������
	static int[] select;	//���õ� ���
	static int[] select2;	//�ݴ��� ���
	
	static int[][] map;		//�ó��� ��
	static int min;			//�ΰ� ���� ����
	
	public static void main(String[] args) throws Exception{
		//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			min = Integer.MAX_VALUE;
			
			N = Integer.parseInt(in.readLine());
			limit = N/2;
			select = new int[limit];
			select2 = new int[limit];
			
			map = new int[N][N];	//�� �ʱ�ȭ
			for(int y = 0; y < N; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 0; x < N; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			//�Է� �ޱ� ����
			
			//�� �ﰢ��ķ� ��ȯ -> �ó��� 2�� �̸� ���س���
			for(int y = 0; y < N; y++) {
				for(int x = y+1; x < N; x++) {
					map[y][x] += map[x][y];
				}
			}
			
			combi(0, 0);
			
			//��� �ֱ�
			sb.append("#").append(test_case).append(" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void combi(int count, int current) {
		if(count == limit) {
			int sum = 0;
			for(int i = 0; i < limit-1; i++) {
				for(int j = i+1; j < limit; j++) {
					sum += map[select[i]][select[j]];
				}
			}
			
			
			//�ݴ��� �丮�� ���� ���� �ȵ� �͵��̴�
			int index = 0;
			Loop: for(int i = 0; i < N; i++) {	//i�� �����ҷ� �ϴµ�
				for(int j : select) {	//���õǾ� �ִ� ���� Ȯ��
					if(i == j) {
						continue Loop;	//�ش� ��� ���þ��ϱ�
					}
				}
				select2[index++] = i;	//���� �ȵǾ� �����Ƿ� ����
			}
			
			int sum2 = 0;
			for(int i = 0; i < limit-1; i++) {
				for(int j = i+1; j < limit; j++) {
					sum2 += map[select2[i]][select2[j]];
				}
			}
			
			
			min = Math.min(min, Math.abs(sum - sum2));
			return;
		}
		
		for(int i = current; i < N; i++) {
			select[count] = i;
			combi(count+1, i+1);
		}
	}
}
