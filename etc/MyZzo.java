import java.util.ArrayDeque;
import java.util.Queue;

public class MyZzo {
	public static void main(String[] args) {
		int N = 5;	//�������� ����
		
		
		//[��������, � �޾ƾ� �ϴ���]
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {1, 1});
		
		//�����ް� �� �����������
		int[] target = null;
		int who = 0;
		int count = 0;
		int numHuman = 1;
		
		while(true) {	//��� ����
			target = q.poll();
			who = target[0];
			count = target[1];
			
			N -= count;
			if(N <= 0) {	//������
				System.out.println(who);
				return;
			}

			numHuman++;	//�ִ� ��� �ø���
			q.add(new int[] {who, count+1});	//���� ��� �Ѱ� �� �ޱ�
			q.add(new int[] {numHuman, 1});	//���ο� ��� �����
		}
	}
}
