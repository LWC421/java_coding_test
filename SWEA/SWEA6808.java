import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA6808 {
	static int[] cards = new int[9];				//�ο��̰� ���� ī���� ����
	static int[] remain = new int[9];				//�ο��̰� �����ϰ� ���� ī��
	static int[] select = new int[9];				//���� �� ī�� ����
	static boolean[] isSelected = new boolean[9];	//���� ����
	
	static int win;		//���� �� �� �̰����
	static int loose;	//���� �� �� ������
	
	public static void main(String[] args) throws Exception{
		BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in= new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			win = 0;
			loose = 0;
			
			//�Է� �ޱ�
			st = new StringTokenizer(in.readLine(), " ");
			for(int i = 0; i < 9; i++) {
				cards[i] = Integer.parseInt(st.nextToken());
			}
			//�Է� ����
			
			//�ο��̰� �������� ���� ī�� ����
			int index = 0;
			Loop : for(int i = 1; i <= 18; i++) {
				for(int j = 0; j < 9; j++) {
					if(i == cards[j]) continue Loop;	//�ش� ī��� �ο��̰� �����ѰŴ�
				}
				remain[index++] = i;	//�� ī��� �ο��̰� �������� ���� �Ŵ�
			}
			
			perm(0);
			
			sb.append("#").append(test_case).append(" ").append(loose).append(" ").append(win).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void perm(int count) {
		if(count == 9) {
			//�� ������ ���
			int myScore = 0;	//�� ����
			int yourScore = 0;	//�� ����
			
			int index = 0;
			for(int s: select) {	//���� ���� �ҷ�����
				if(remain[s] > cards[index]) {	//���� �����Ѱ� �� ũ��
					myScore += (remain[s] + cards[index++]);
				}
				else {
					yourScore += (remain[s] + cards[index++]);
				}
			}
			
			if(myScore > yourScore) {
				win++;
			}
			else if(myScore < yourScore) {
				loose++;
			}
			
			return;
		}
		
		for(int i = 0; i < 9; i++) {
			//ī�� �����ϱ�
			if(isSelected[i]) continue;	//���õ� ī��� ��ŵ
			select[count] = i;	//�ش� i��° ī�� �����ߴٰ� �ϱ�
			isSelected[i] = true;
			perm(count+1);
			isSelected[i] = false;
		}
	}
}
