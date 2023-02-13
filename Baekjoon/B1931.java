import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B1931 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(in.readLine());
		
		//����ð� �������� �����ϱ�
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b) ->  {
			return a[1] == b[1] ? a[0] - b[0] : a[1] - b[1];
		});
		
		//�Է� �ޱ�
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			
			q.add(new int[] { Integer.parseInt(st.nextToken()), 
					Integer.parseInt(st.nextToken())}
			);
		}
		
		int[] tmp = q.poll();
		
		int prevEnd = tmp[1];
		int currentStart;
		int currentEnd;
		int result = 1;
		
		//�켱���� ť�� ���������� �ݺ��ϱ�
		while( !q.isEmpty() ) {
			tmp = q.poll();
			currentStart = tmp[0];
			currentEnd = tmp[1];
			if(prevEnd <= currentStart) {
				prevEnd = currentEnd;
				result++;
			}
		}
		System.out.println(result);
	}
}
