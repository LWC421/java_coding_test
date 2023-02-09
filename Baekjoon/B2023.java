import java.util.Scanner;

public class B2023 {
	static StringBuilder sb = null;
	static int N;
	static int current;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		sb = new StringBuilder("");

		//� ���� �ؾߵǴ��� Ȯ��
		N = sc.nextInt();
		current = 0;

		//1�ڸ����̸� �������ϱ� �ٷ� ����ֱ�
		if(N == 1) {
			sb.append(2).append("\n");
			sb.append(3).append("\n");
			sb.append(5).append("\n");
			sb.append(7).append("\n");

			System.out.println(sb);
			return;
		}
		
		perm(0);
		System.out.println(sb.toString());
	}

	//���� ���� ã��
	public static void perm(int count) {
		//1���� Ŭ ���� �Ҽ� �˻� �ǽ�
		if(current > 1) {
			for(int i = 2; i <= Math.sqrt(current); i++) {
				if( (current % i) == 0) {	//�ռ����̸�
					return;	//�׸�
				}
			}
		}
		
		if( count == N) {	//N�ڸ����� ������
			sb.append(current).append("\n");	//�Ҽ��̸鼭 N�ڸ����̴�
			return;
		}
		
		for(int i = 0; i < 10; i++) { // 0 ~ 9�� �ٿ�������
			current = current * 10 + i;	//�̹� ���� �����ϱ�
			
			if(current > 1) {	//�Ҽ��� ���ɼ��� �������� �˻�
				perm(count + 1);	//���� �ڸ����� Ž��
			}
			current = (current - i) / 10;	//���� ���ڷ� ������
		}
	}
}
