import java.io.*;
import java.util.*;

public class B16566 {
	
	final static int MAX_POW = 22;	//2 ^ 22 = 4,194,304
	
	// N logN -> 88,000,000

	public static void main(String[] args) throws Exception{
		//N개의 빨간 카드, 1 ~ N번 적혀있다
		// 그 중 M개 고른다
		
		//N개의 파란 카드, 1 ~ N번
		// 빨간색에서 고른 번호와 동일한 M개를 고른다
		
		//철수는 빨강색
		//민수는 파란색
		
		// K번 동안 카드를 내고 더 큰 수인 사람이 이기는 게임이다 (한 번 낸 카드는 사용X)
		
		//철수가 낼 카드가 주어지면
		// 민수는 어떤 카드를 낼지 출력
		
		// N자료구조 가능
		// 10

		
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numMax = Integer.parseInt(st.nextToken());		// 1 ~ numMax까지
		int numCandi = Integer.parseInt(st.nextToken());	// 몇 개의 카드를 고를지
		int numRound = Integer.parseInt(st.nextToken());	// 몇 번의 라운드를 진행할 지
		
		int[] inputs = new int[numCandi];
		
		st = new StringTokenizer(in.readLine());
		TreeSet<Integer> set = new TreeSet<>();
		
		//민수가 사용할 카드들 받기
		
		for(int i = 0; i < numCandi; i++) {
//			set.add(Integer.parseInt(st.nextToken()));
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(inputs);	//사용할 수 있는 카드들을 정렬하자
		
		Card[] cards = new Card[numMax+1];	//1 ~ N까지의 카드들의 정보를 저장해야한다
		
		for(int i = 1; i <= numMax; i++) {
			cards[i] = new Card();
		}
		
		int prev = 1;
		List<Integer> prevs = new LinkedList<>();
		for(int i = 0; i < numCandi; i++) {
			//입력받은 카드들에 대해
			int curr = inputs[i];
			for(int j = prev; j < curr; j++) {
				//연결관계 지어주기
				cards[j].next = curr;
			}
			cards[curr].alived = true;	//이 카드는 입력으로 들어온 카드라고 표시
			
			prev = curr;	//현재라고 넣어주기	
		}
		
//		for(int i = 1; i <= numMax; i++) {
//			System.out.println(cards[i]);
//		}
		

		StringBuilder sb = new StringBuilder("");
		
		st = new StringTokenizer(in.readLine());
		
		//하나하나의 라운드를 진행하자
		int target;
		for(int i = 0; i < numRound; i++) {
			target = Integer.parseInt(st.nextToken());
			
			Card curr = cards[target];
			int currValue = curr.next;
			curr = cards[curr.next];	//현재 카드의 다음 카드부터 시작해서
			while(curr.alived == false) {
				//만약 이미 사용한 카드이면
				currValue = curr.next;
				curr = cards[curr.next];
			}
			sb.append(currValue).append('\n');
			curr.alived = false;	//사용한 카드라고 표시
		}
		
		System.out.println(sb.toString());
	}
	
	static class Card{
		boolean alived;		//true일 경우 현재 있는 카드라는 소리다
		int next;
		
		@Override
		public String toString() {
			return String.format("%s -> %d", alived == true ? "alive" : "dead", next);
		}
	}
}
