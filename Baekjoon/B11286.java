import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class B11286 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int N = Integer.parseInt(in.readLine());	//������ ����
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
			return a[1] == b[1] ? Integer.compare(a[0], b[0]) : a[1] - b[1];
		});	//���ڸ� ������ ť�̴�, [�׳� ��, ���밪]���� �ֱ�
		
		int target;	//�Է� �޴� ��
		for(int i = 0; i < N; i++) {
			target = Integer.parseInt(in.readLine());
			if(target == 0) {	//�Է°��� 0�϶��� ���� -> �̾Ƽ� ���
				if(q.isEmpty()) {	//������� ���
					sb.append(0).append("\n");
				}
				else {	//���� ���� �� �̱�
					sb.append(q.poll()[0]).append("\n");
				}
			}
			else {	//�Է°��� 0�� �ƴҶ��� ���� -> �ֱ� 
				q.add(new int[] {target, Math.abs(target)});
			}
		}
		
		System.out.println(sb.toString());
	}
}
