import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1245 {
	
	static double[] coords = null;
	static double[] weights = null;

	public static void main(String[] args) throws Exception{
		// n개의 자성체
		// 자성체의 위치 (x, y, z)
		// y, z는 좌표가 동일하고 x의 좌표만 다르다
		// 어떤물체가 이 직선위 임의의 위치에 존래하면 인력 작용
		// F = G*m1*m2/(d*d)이 작용한다 -> m질량, d거리
		// 계산하였을때 인력이 더 큰쪽으로 물체 이동 -> 정지 가능
		
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			int numZasung = Integer.parseInt(in.readLine());
			
			st = new StringTokenizer(in.readLine());
			
			coords = new double[numZasung];
			weights = new double[numZasung];
			
			for(int i = 0; i < numZasung; i++) {
				coords[i] = Integer.parseInt(st.nextToken());
			}
			for(int i = 0; i < numZasung; i++) {
				weights[i] = Integer.parseInt(st.nextToken());
			}
			
			
			double[] results = new double[numZasung];
			
			for(int target = 1; target < numZasung; target++) {
				//target보다 작은 자성체들을 왼쪽
				//같거나 높은 자성체들을 오른쪽
			
				//바운더리 정하기
				double leftCoord = coords[target-1];
				double rightCoord = coords[target];
				double coord = -1;
				double diff = 1;
				
				
				while(Math.abs(rightCoord - leftCoord) > 0.000000000001) {
					coord = leftCoord + ((rightCoord-leftCoord) / 2);
					double leftPower = 0;
					double rightPower = 0;
					
					for(int l = 0; l < target; l++) {
						//왼쪽 자성 더하기
						double distance = coord-coords[l];	//자성체와의 거리 측정
						distance = distance * distance;
						leftPower += (weights[l] / distance);
					}
					
					for(int r = target; r < numZasung; r++) {
						//오른쪽 자성 더하기
						double distance = coords[r] - coord;	//자성체와의 거리 측정
						distance = distance * distance;
						rightPower += (weights[r] / distance);
					}
					
					diff = leftPower - rightPower;
					
					if(diff < 0) {
						//왼쪽으로 이동해야되는 경우
						rightCoord = rightCoord - (rightCoord - leftCoord)/2;
					}
					else{
						//오른쪽으로 이동해야 되는 경우
						leftCoord = leftCoord + (rightCoord-leftCoord)/2;
					}
				}
				results[target] = coord;	//해당 좌표가 가능하다고 표시
			}
			
			sb.append(String.format("#%d ", test_case));
			for(int i = 1; i < numZasung; i++) {
				sb.append(String.format("%.10f", results[i])).append(" ");
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
