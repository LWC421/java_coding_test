import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B2887{
	
	static int[] set;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int numStar = Integer.parseInt(in.readLine());
		Star[] stars = new Star[numStar];
		
		for(int i = 0; i < numStar; i++) {
			st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			stars[i] = new Star(i, x, y, z);
		}
		//입력 종료
		
		Edge[] edges = new Edge[(numStar-1) * 3];
		
		
		// X기준으로 넣기
		Arrays.sort(stars, (a, b) -> {
			return a.x - b.x;
		});
		int index = 0;
		for(int i = 1; i < numStar; i++) {
			int weight = stars[i].x - stars[i-1].x;
			edges[index++] = new Edge(stars[i].num, stars[i-1].num, weight);
		}
		
		// Y기준으로 넣기
		Arrays.sort(stars, (a, b) -> {
			return a.y -b.y;
		});
		for(int i = 1; i < numStar; i++) {
			int weight = stars[i].y - stars[i-1].y;
			edges[index++] = new Edge(stars[i].num, stars[i-1].num, weight);
		}
		
		// Z기준으로 넣기
		Arrays.sort(stars, (a, b) -> {
			return a.z - b.z;
		});
		for(int i = 1; i < numStar; i++) {
			int weight = stars[i].z - stars[i-1].z;
			edges[index++] = new Edge(stars[i].num, stars[i-1].num, weight);
		}
		
		//간선 배열을 weight가 작은 순으로 정렬
		Arrays.sort(edges, (a, b) -> {
			return a.weight - b.weight;
		});
		
		// Union-Find를 하자
		set = new int[numStar];
		
		for(int i = 0; i < numStar; i++) {
			set[i] = i;
		}
		
		long result = 0;
		index = 0;
		for(int i = 1; i < numStar; i++) {
			//MST를 돌리자
			Edge edge = edges[index++];
			while( !union(edge.from, edge.to) ) {
				edge = edges[index++];
			}
			result += edge.weight;
		}
		System.out.println(result);
	}
	
	static int find(int target) {
		if(set[target] == target) {
			return target;
		}
		
		return set[target] = find(set[target]);
	}
	
	static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		
		if(a == b) {
			//이미 같은 집합이면
			return false;
		}
		
		set[a] = b;
		
		return true;
	}
	
	static class Star{
		int num;
		int x;
		int y;
		int z;
		
		public Star(int num, int x, int y, int z) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public String toString() {
			return String.format("%d : [%d, %d, %d]", num, x, y, z);
		}
	}
	
	static class Edge{
		int from;
		int to;
		int weight;
		
		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return String.format("%d->%d : %d", from, to, weight);
		}
	}
}