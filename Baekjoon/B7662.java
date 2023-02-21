import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B7662 {
	static DoubleQueue q = null;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));		
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		String s = null;
		int num = 0;
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			int numCommand = Integer.parseInt(in.readLine());
			q = new DoubleQueue(numCommand);
			
			for(int i = 0; i < numCommand; i++) {
				st = new StringTokenizer(in.readLine());
				s = st.nextToken();
				num = Integer.parseInt(st.nextToken());
				
				if(s.equals("I")) {
					//삽입 연산인 경우
					q.add(num);
				}
				else {
					//삭제 연산
					if(num == 1) {
						//최대값 빼기
						q.maxPop();
					}
					else {
						//최소값 빼기
						q.minPop();
					}
				}
			}
			
			Integer minValue = q.minPeek();
			Integer maxValue = q.maxPeek();
			if(minValue == null || maxValue == null) {
				//둘 중 하나라도 null이면
				sb.append("EMPTY").append("\n");
			}
			else {
				sb.append(maxValue).append(" ").append(minValue).append("\n");
			}
		}
		
		System.out.println(sb.toString());
	}
}

class DoubleQueue{
	PriorityQueue<Node> minQ = new PriorityQueue<>((a, b) -> {
		return Integer.compare(a.value, b.value);
	});
	
	PriorityQueue<Node> maxQ = new PriorityQueue<>((a, b) -> {
		return -Integer.compare(a.value, b.value);
	});
	
	//사용여부 체크
	boolean[] used = null;
	int curId = 0;
	
	public DoubleQueue(int size){
		used = new boolean[size];
	}
	
	public void add(int value) {
		minQ.add(new Node(value, curId));
		maxQ.add(new Node(value, curId));
		curId++;	//ID늘리기
	}
	
	public void minPop() {
		if( !minQ.isEmpty() ) {	//있으면 큐에서 가져오기
			Node n = minQ.poll();
			while( !minQ.isEmpty() && used[n.index] ) {	//만약 사용 했을 경우
				n = minQ.poll();	//더 뽑기
			}
			if( !used[n.index] ) {	//마지막까지 갔는데 -> 사용하지 않은 거면
				used[n.index] = true;	//마지막에 뽑은거 -> 진짜로 사용 안했던 거 -> 사용했다고 표시
			}
		}
	}
	
	public void maxPop() {
		if( !maxQ.isEmpty() ) {	//있으면 큐에서 가져오기
			Node n = maxQ.poll();
			while( !maxQ.isEmpty() && used[n.index] ) {	//만약 사용 했을 경우
				n = maxQ.poll();	//더 뽑기
			}
			if( !used[n.index] ) {	//마지막까지 갔는데 -> 사용하지 않은 거면
				used[n.index] = true;	//마지막에 뽑은거 -> 진짜로 사용 안했던 거 -> 사용했다고 표시
			}
		}
	}
	
	public Integer minPeek() {
		if( !minQ.isEmpty() ) {
			Node n = minQ.peek();
			while( !minQ.isEmpty() && used[n.index] ) {	//만약 사용 했을 경우
				n = minQ.poll();
			}
			if( !used[n.index] ) {
				return n.value;
			}
			else {
				return null;
			}
		}
		
		return null;	//비어있을 경우
	}
	
	public Integer maxPeek() {
		if( !maxQ.isEmpty() ) {
			Node n = maxQ.peek();
			while( !maxQ.isEmpty() && used[n.index] ) {	//만약 사용 했을 경우
				n = maxQ.poll();
			}
			if( !used[n.index] ) {
				return n.value;
			}
			else {
				return null;
			}
		}
		
		return null;	//비어있을 경우
	}
	
}

class Node{
	int value;
	int index;

	public Node(int value, int index) {
		this.value = value;
		this.index = index;
	}
}