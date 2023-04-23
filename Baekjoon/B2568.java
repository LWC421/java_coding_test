import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class B2568 {

	static int maxLine;			//전깃줄의 최대값 저장
	static int[] inputs;		//선에 대한 정보 저장
	static int[] lisIndex;		//LIS를 하였을 때 정보 저장
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		List<Line> lines = new LinkedList<>(); 	//입력 저장용
		
		//번호 압축용
		List<Integer> froms = new LinkedList<>();
		List<Integer> tos = new LinkedList<>();
		Map<Integer, Integer> fromToIndex = new HashMap<>();
		Map<Integer, Integer> toToIndex = new HashMap<>();
		Map<Integer, Integer> indexToFrom = new HashMap<>();
		Map<Integer, Integer> indexToTo = new HashMap<>();
		
		
		int numLine = Integer.parseInt(in.readLine());
		inputs = new int[numLine+1];
		lisIndex = new int[numLine+1];
		
		for(int i = 0; i < numLine; i++) {
			int from, to;
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			lines.add(new Line(from, to));
			froms.add(from);
			tos.add(to);
		}
		//입력 종료
		
		Collections.sort(froms);
		Collections.sort(tos);
		
		int index = 1;
		for(int from: froms) {
			fromToIndex.put(from, index);
			indexToFrom.put(index++, from);
		}
		index = 1;
		for(int to: tos) {
			toToIndex.put(to, index);
			indexToTo.put(index++, to);
		}
		
		//경로 압축된 수열을 넣어주자
		for(Line line: lines) {
			int fromIndex = fromToIndex.get(line.from);
			int toIndex = toToIndex.get(line.to);
			
			inputs[fromIndex] = toIndex;
		}
		
		int lisLength = LIS();
		int curr = lisLength;
		Stack<Integer> stack = new Stack<>();
		for(int i = numLine; i > 0; i--) {
			if(lisIndex[i] == curr) {
				curr--;
			} else {
				stack.add(i);
			}
		}

		sb.append(numLine - lisLength).append('\n');
		
		while( !stack.isEmpty() ) {
			index = stack.pop();
			sb.append(indexToFrom.get(index)).append('\n');
		}
		
		System.out.println(sb.toString());
	}
	
	public static int LIS() {
		int[] lis = new int[inputs.length];
		int lisLength = 1;
		lis[1] = inputs[1];
		lisIndex[1] = 1;
		
		for(int i = 2; i < inputs.length; i++) {
			if(lis[lisLength] < inputs[i]) {
				lisLength++;
				lis[lisLength] = inputs[i];
				lisIndex[i] = lisLength;
			} 
			else {
				int lower = lower(lis, 1, lisLength, inputs[i]);
				lisIndex[i] = lower;
				lis[lower] = inputs[i];
			}
		}
		
		
		return lisLength;
	}
	
	public static int lower(int[] lis, int left, int right, int target) {
		int mid;
		
		while(left < right) {
			mid = left + (right - left)/2;
			if(lis[mid] < target) {
				left = mid+1;
			}
			else {
				right = mid;
			}
		}
		
		return left;
	}
	
	static class Line{
		int from;
		int to;
		
		public Line(int from, int to) {
			this.from = from;
			this.to = to;
		}
	}
}
