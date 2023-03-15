import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class B21608 {
	
	//4방위 탐색에 사용한다
	static final int[] dy = {-1, 1, 0, 0};
	static final int[] dx = {0, 0, 1, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int length = Integer.parseInt(in.readLine());
		Map<Integer, Set<Integer>> likes = new HashMap<>();
		int[] students = new int[length*length];	//학생들의 배치 순서를 기억하기 위해

		StringTokenizer st = null;
		int num;
		for(int i = 0, limit=length*length; i < limit; i++) {
			st = new StringTokenizer(in.readLine());
			num = Integer.parseInt(st.nextToken());
			students[i] = num;	//이 학생부터 배치해야한다
			Set<Integer> set = new HashSet<>();	//set에 좋아하는 학생들 넣기
			for(int j = 0; j < 4; j++) {	//4명의 학생을 좋아한다
				set.add(Integer.parseInt(st.nextToken()));	//해당 학생을 좋아한다고 표시
			}
			likes.put(num, set);	//좋아하는 학생 관계에 넣어주기, num이 set에 있는 학생들을 좋아한다
		}
		//입력 종료
		
		int[][] map = new int[length+2][length+2];	//+2로해서 벽을 치자
		for(int i = 0, limit=length+1; i <= limit; i++) {
			map[i][0] = -1;
			map[i][limit] = -1;
			map[0][i] = -1;
			map[limit][i] = -1;
		}
		//-1이면 벽이라는 뜻이다
		
		int[][] counts = new int[length+2][length+2];
		Set<Integer> like = null;	//좋아하는 학생을 추후 꺼내쓰자
		Coord[] coords = new Coord[length*length+1];	//학생 각자의 위치를 기억해두자, 0번 학생은 없으므로 +1
		Coord cur = null;
		int nY, nX;	//계산된 좌표
		
		int maxY, maxX;
		int curEmpty, maxEmpty;	//4방위로 빈좌석의 개수를 세자
		int maxCount;	//최종적으로 어느자리에 앉고싶은지 확인해보자
		for(int from: students) {
			//순서대로 배치하자
			like = likes.get(from);	//like에 좋아하는 학생 4명이 담겨있다
			for(int l: like) {
				//좋아하는 학생을 한명씩 보자
				cur = coords[l];	//해당 학생의 좌표를 보자
				if(cur == null) continue;	//null이면 아직 배치가 안되었다는 뜻이다 -> 해당 좋아하는 학생은 고려할 필요가 없다
				
				//null이 아닌 경우에만 작업을 해주자
				for(int d = 0; d < 4; d++) {
					//4방위 탐색을 하자
					nY = cur.y + dy[d];
					nX = cur.x + dx[d];	//좋아하는 학생의 4방위를 확인해야한다
					if(map[nY][nX] != 0) {
						//0이 아니라는 뜻은 누군가가 앉아있거나 벽이라는 소리다 -> 넘기자
						continue;
					}
					counts[nY][nX] += 1;	//해당 자리에 앉고싶어한다고 표시하자
				}
			}
			
			boolean searched = false;
			maxY = maxX = 1;	//일단 제일 좌측최상단으로 하자
			maxCount = 0;	//선호하는 좌석에 대한 최대치를 0으로
			maxEmpty = 0;	//선호 좌석의 4방위의 빈좌석을 0으로
			for(int y = 1; y <= length; y++){
				for(int x = 1; x <= length; x++) {
					if(map[y][x] != 0) {
						//이미 누군가가 앉아있으면
						continue;
					}
					if(map[y][x] == 0) {
						if(!searched) {
							//만약 아직 자리를 못 정했으면
							maxY = y;
							maxX = x;
							searched = true;	//일단 정하긴 했다고 표시
						}
					}
					if( maxCount < counts[y][x] ) {
						maxCount = counts[y][x];	//선호하는 좌석이 더 큰 것이 나오면 -> 해당 좌표로 바꾸자
						maxY = y;
						maxX = x;
						curEmpty = 0;
						for(int d = 0; d < 4; d++) {
							nY = y + dy[d];
							nX = x + dx[d];
							if(map[nY][nX] == 0) {
								//주변 빈 좌석의 개수를 세보자
								curEmpty++;
							}
						}
						maxEmpty = curEmpty;	//현재까지 나온 선호 좌석의 4방위 빈좌석의 최대값을 넣어놓자
						searched = true;	//일단 정하긴 했다고 표시
					}
					else if(maxCount == counts[y][x]) {
						//만약 동등한 수준으로 좋아하는 좌석이면 -> 비어있는 칸이 가장 많은 칸으로 자리를 정하자
						curEmpty = 0;
						for(int d = 0; d < 4; d++) {
							nY = y + dy[d];
							nX = x + dx[d];
							if(map[nY][nX] == 0) {
								//주변 빈 좌석의 개수를 세보자
								curEmpty++;
							}
						}
						if(maxEmpty < curEmpty) {
							//현재 좌표의 4방위 빈좌석이 더 많으면 -> 여기로 바꾸자
							maxY = y;
							maxX = x;
							maxEmpty = curEmpty;
							searched = true;	//일단 정하긴 했다고 표시
						}
					}
				}
			}
				
			map[maxY][maxX] = from;					//최종적으로 여기에 배치하자
			coords[from] = new Coord(maxY, maxX);	//이 학생은 여기에 앉아있다고 표시
			Reset(counts);							//학생이 선호하는 좌석에 대한 가중치는 초기화시키자
		}
		//학생 배치는 끝났다
//		for(int[] row: map) {
//			System.out.println(Arrays.toString(row));
//		}
		
		int from;
		int result = 0;
		int count;	//4방위에 좋아하는 학생이 몇 명 있는지
		for(int y = 1; y <= length; y++) {
			for(int x = 1; x <= length; x++) {
				from = map[y][x];	//해당 학생에 대해 점수를 계산해보자
//				System.out.printf("%d : [%d, %d]\n", from, y, x);
				like = likes.get(from);	//좋아하는 학생 정보 가져오기
				count = 0;	//4방위에 좋아하는 학생이 없다고 표시
				for(int d = 0; d < 4; d++) {
					//4방위로 확인
					nY = y + dy[d];
					nX = x + dx[d];
					if(like.contains(map[nY][nX])) {
						//좋아하는 학생이 4방위에 있으면
						count++;	//count를 늘리자
					}
				}
				//4방위로 좋아하는 학생 여부 체크완료하였으면 -> 점수를 올려주자
				if(count == 1) result += 1;
				else if(count == 2) result += 10;
				else if(count == 3) result += 100;
				else if(count == 4) result += 1000;
			}
		}
		
		System.out.println(result);

	}
	
	//배열을 0으로 초기화
	public static void Reset(int[][] counts) {
		int lY = counts.length;
		int lX = counts[0].length;
		for(int y = 0; y < lY; y++) {
			for(int x = 0; x < lX; x++) {
				counts[y][x] = 0;
			}
		}
	}
	
	//좌표를 나타내자
	static class Coord{
		int y;
		int x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}
