import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA9229 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		int N;	//���� ���� ����
		int M;	//���� �� ����
		
		int[] numbers;	//���ڵ��� ����
		int max;		//������� �ִ� ����
		int sum;		//���� �� ���� ��
		
		for(int test_case = 1; test_case <= T; test_case++) {
			max = -1;
			
			//�Է� �ޱ�
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			numbers = new int[N];
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			//�Է� �ޱ� ����
			
			//������������ ����
			Arrays.sort(numbers);

			for(int left = 0; left < N-1; left++) {
				for(int right = left+1; right < N; right++) {
					sum = numbers[left] + numbers[right];
					if(sum <= M) {	//��� ���� �̸�
						max = Math.max(max, sum);
					}
					else {	//���̻� �� ���� ���� �����Ƿ� ���� left�� �Ѿ��
						break;
					}
				}
			}
			sb.append("#").append(test_case).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
