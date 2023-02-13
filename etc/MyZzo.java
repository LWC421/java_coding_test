import java.util.ArrayDeque;
import java.util.Queue;

public class MyZzo {
	public static void main(String[] args) {
		int N = 5;	//마이쭈의 개수
		
		
		//[누구인지, 몇개 받아야 하는지]
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {1, 1});
		
		//마이쭈가 다 사라질때까지
		int[] target = null;
		int who = 0;
		int count = 0;
		int numHuman = 1;
		
		while(true) {	//계속 돈다
			target = q.poll();
			who = target[0];
			count = target[1];
			
			N -= count;
			if(N <= 0) {	//끝났다
				System.out.println(who);
				return;
			}

			numHuman++;	//최대 사람 늘리기
			q.add(new int[] {who, count+1});	//현재 사람 한개 더 받기
			q.add(new int[] {numHuman, 1});	//새로운 사람 만들기
		}
	}
}
