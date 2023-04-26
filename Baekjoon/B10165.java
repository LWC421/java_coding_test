import java.io.*;
import java.util.*;


public class B10165 {

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int numStop = Integer.parseInt(in.readLine());	//버스 정류장의 개수
		int numLine = Integer.parseInt(in.readLine());	//노선의 개수

		List<FromTo> lines = new LinkedList<>();

		//정류장은 0번부터 시작
		int from, to;
		for(int i = 1; i <= numLine; i++) {
			st = new StringTokenizer(in.readLine());

			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());

			if(from > to) {
				if(to != 0) {
					//0번 정류소를 지나치는 거이면 -> 
					int minusFrom = from - numStop;
					lines.add(new FromTo(i, minusFrom, to));
					
					int plusTo = numStop + to;
					lines.add(new FromTo(i, from, plusTo));
				}
				else {
					//0번 정류소에서 끝나는 거 이면
					int plusTo = numStop;	//0대신 마지막 정류장으로 표시
					lines.add(new FromTo(i, from, plusTo));
				}
			}else {
				lines.add(new FromTo(i, from, to));
			}
		}
		//입력 종료
		
		//from과 to를 이용해서 정렬 하자
		lines.sort((a, b) -> {
			if(a.from == b.from) {
				return b.to - a.to;
			}
			return a.from - b.from;
		});
		
//		for(FromTo line: lines) {
//			System.out.println(line);
//		}
		
		Stack<FromTo> stack = new Stack<FromTo>();
		
		FromTo top = null;
		for(FromTo line: lines) {
			//하나하나의 노선에 대해
			if(stack.empty()) {
				//그전에 없었으면 무조건 넣야아 한다
				stack.add(line);
				continue;
			}
			
			top = stack.peek();	//맨위에 꺼를 보고
			
			if(line.to <= top.to) {
				//만약 현재 노선이 그전라인과 겹치면
				continue;
			}
			
			stack.add(line);	//겹치지 않으므로 추가하자
		}
		
		TreeSet<Integer> remains = new TreeSet<>();
		
		while( !stack.isEmpty() ) {
			top = stack.pop();
			remains.add(top.id);	//남은 노선들의 id를 넣자
		}
		
		for(int id: remains) {
			sb.append(id).append(' ');
		}
		
		System.out.println(sb.toString());
	}
	
	static class FromTo{
		int id;
		int from;
		int to;
		
		public FromTo(int id, int from, int to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
		
		@Override
		public String toString() {
			return String.format("#%d : %d -> %d", id, from, to);
		}
	}
}

