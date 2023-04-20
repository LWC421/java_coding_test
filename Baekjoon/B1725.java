import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class B1725 {

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int numHisto = Integer.parseInt(in.readLine());
		long[] histos = new long[numHisto]; 
		
		for(int i = 0; i < numHisto; i++) {
			histos[i] = Integer.parseInt(in.readLine());
		}
		//입력 종료
		

		//각 히스토그램의 왼쪽 선분을 기준으로 하자
		Line[] lines = new Line[numHisto];
				
		for(int i = 0; i < numHisto; i++) {
			lines[i] = new Line(i, histos[i]);
		}
		
		//결과값 저장
		long result = 0;
		
		PriorityQueue<Line> stack = new PriorityQueue<>();
		List<Line> tmp = new LinkedList<>();

		//모든 선들을 보면서
		for(int i = 0; i < numHisto; i++) {
			tmp.clear();
			Line current = lines[i];
			
			if(stack.isEmpty()) {
				stack.add(current);	//없으면 넣자
				continue;
			}
			//스택에 무언가가 있었으면
			
			
			Line top = stack.poll();	//꺼내보기
			boolean empty = false;	//진짜로 빈거인지 체크용
			while(top.height > current.height) {
				//만약 이전 선분의 길이가 더 길었으면 -> 더이상 직사각형을 그을 수 없다
				//stack에 존재하는 더 짧은 선분들에 대해 모두 작업해주어야 한다
				long area = top.height*(i - top.start);
				result = Math.max(result, area);		//면적 잰 것을 기준으로 더 큰 값으로 변경해주기
				
				
				Line tmpLine = new Line(top.start, current.height);
				tmp.add(tmpLine);	//이전 좌표를 기준으로 더 낮은 높이의 큰 직사각형이 만들어 질 수도 있다		
				
				if(stack.isEmpty()) {
					//이로 인해 다 비었으면 그만 뽑기
					empty = true;		//진짜로 비었다고 해주기
					break;
				}
				top = stack.poll();
			}
			stack.addAll(tmp);	//낮은 높이의 직사각형이 생길수도 있으므로 stack에 넣어주자
			
			if( !empty ) {
				//마지막에 뽑은 거는 다시 넣어줘야 하는 경우
				stack.add(top);
			}
			
			stack.add(current);	//현재꺼도 넣어주자
		}
		
		//남은 선분에 대해서도 작업해주자
		while( !stack.isEmpty() ) {
			Line top = stack.poll();
			long area = top.height*(numHisto - top.start);
			result = Math.max(result, area);		//면적 잰 것을 기준으로 더 큰 값으로 변경해주기
		}
		
		System.out.println(result);
	}
	
	static class Line implements Comparable<Line>{
		int start;
		long height;
		
		public Line(int start, long height) {
			this.start = start;
			this.height = height;
		}

		@Override
		public String toString() {
			return String.format("%d :%dh", start, height);
		}

		@Override
		public int compareTo(Line o) {
			return o.start - this.start;
		}
	}
}
