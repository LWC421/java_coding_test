import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA4014 {
	
	static int mapLength;
	static int gyeongLength;
	
	public static void main(String[] args) throws Exception{
		// N * N멥 
		// 각 칸마다 높이가 있다
		// 경사로 길이 X가 주어진다
		// 길이가 1만큼 차이날때 경사로를 타고 올라갈 수 있다
		// 활주로를 건설할 수 있는 경우
		
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			
			mapLength = Integer.parseInt(st.nextToken());		//N, 맵 크기
			gyeongLength = Integer.parseInt(st.nextToken());	//X, 경사로 길이
			
			int[][] map = new int[mapLength][mapLength];		//지형 정보
			for(int y = 0; y < mapLength; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 0; x < mapLength; x++) {
					map[y][x] = Integer.parseInt(st.nextToken()) * gyeongLength;
				}
			}
			//모든 입력 종료
			
			int result = 0;	//결과값 저장
			int[] line = new int[mapLength];
			
			for(int y = 0; y < mapLength; y++) {
				//가로 방향으로 보기
				
				//하나의 line을 만들어서
				for(int x = 0; x < mapLength; x++) {
					line[x] = map[y][x];
				}
				
				//경사로 짓기 해보자
				doGyeong(line);
				if(checkLine(line)) {
					result++;
				}
			}
			
			for(int x = 0; x < mapLength; x++) {
				//세로 방향으로 보기
				for(int y = 0; y < mapLength; y++) {
					line[y] = map[y][x];
				}
				
				doGyeong(line);
				if(checkLine(line)) {
					result++;
				}
			}
		
			sb.append(String.format("#%d %d\n", test_case, result));
		}
		
		System.out.println(sb.toString());
	}
	
	//하나의 라인을 주면 경사로 만들어서 주기
	public static void doGyeong(int[] line) {
		//길이-1까지만 보기
		
		boolean[] used = new boolean[mapLength];
		
		for(int i = 0, limit=mapLength-1; i < limit; i++) {
			if(line[i+1] - line[i] == gyeongLength) {
				//만약 올라가는 경우
				// 현재 칸을 포함하여 현재칸으로부터 경사로 길이만큼 전까지 경사로를 만들어야 한다
				
				//일단 지을 수 있는지부터 보자
				boolean check = true;
				for(int d = 0; d < gyeongLength; d++) {
					if(i - d < 0) {
						//경사로 짓는게 만약 맵 밖을 지나가면
						check = false;	//못 짓는다고 표기
						break;
					}
					if(line[i-d] != line[i]) {
						//만약 높이가 다를 경우
						check = false;	//못 짓는다
						break;
					}
					if(used[i-d]) {
						//이미 활주로를 지었을 경우
						check = false;
						break;
					}
				}
				if(check) {
					//만약 지을 수 있으면
					for(int d = 0; d < gyeongLength; d++) {
						line[i-d] += gyeongLength - d;	//경사로를 짓자
						used[i-d] = true;
					}
				}
			}
			else if(line[i+1] - line[i] == -gyeongLength) {
				//만약 내려가는 경우
				// 현재 칸의 다음칸부터 경사로의 길이만큼 경사로를 만들어야 한다
				
				//일단 지을 수 있는지부터 보자
				boolean check = true;
				for(int d = 1; d <= gyeongLength; d++) {
					if(i+d >= mapLength) {
						//맵 밖을 벗어날 경우
						check = false;	//못 짓는다고 표시
						break;
					}
					if(line[i+1] != line[i+d]) {
						//만약 중간에 높이가 다른게 있다면
						check = false;	//못 짓는다고 표시
						break;
					}
				}
				if(check) {
					//지을 수 있으면
					for(int d = 1; d <= gyeongLength; d++) {
						line[i + d] += (gyeongLength - d);
						used[i+d] = true;
					}
				}
			}
			
			//지형 높이가 같은 경우 -> 아무 작업 안해도 된다
			//지형 높이 차이가 커서 지을 수 없는 경우 -> 아무 작업도 할 수 없다
		}
	}

	public static boolean checkLine(int[] line) {
		int diff;
		for(int i = 0, limit=mapLength-1; i < limit; i++) {
			diff = line[i+1] - line[i];
			if(diff != 0 && diff != -1 && diff != 1) {
				return false;
			}
		}
		
		return true;
	}
}
