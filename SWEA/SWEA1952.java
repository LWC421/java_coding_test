import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1952 {
	static int[] prices = new int[4];		//�̿���� ���� ����
	static int[] numbers = new int[12];		//�ش� �޸��� �� �� �̿��� ����
	static int min;
	static int price;
	
	public static void main(String[] args) throws Exception {
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			price = 0;
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < 4; i++) {	//���� ���� �޾ƿ���
				prices[i] = Integer.parseInt(st.nextToken());
			}
			
			//�ϴ� �� �̿���� �ּҰ����� ���
			min = prices[3];
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < 12; i++) {	//�̿��ȹ �޾ƿ���
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			dfs(0);
			sb.append("#").append(test_case).append(" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void dfs(int count) {
		if(count >= 12) {	//��������
			min = Math.min(min, price);
			return;
		}
		
		//1��
		price += numbers[count] * prices[0];
		dfs(count+1);
		price -= numbers[count] * prices[0];
		
		//1����
		price += prices[1];
		dfs(count+1);
		price -= prices[1];
		
		//3����
		price += prices[2];
		dfs(count+3);
		price -= prices[2];	
	}
}
