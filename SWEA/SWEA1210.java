import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SWEA1210 {
	public static int[][] map = new int[100][100];
	public static int curY;
	public static int curX;
	
	public static void main(String[] args) throws IOException {
		
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		for(int test_case = 1; test_case <= 10; test_case++) {
			curY = 99;	//���� ��ġ �� ������ �ʱ�ȭ
			
			in.readLine();	//ù��° ������
			
			for(int i = 0; i < 100; i++) {	//�� �����ϱ�
				map[i] = Stream.of(in.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
			}
			
			for(int i = 0; i < 100; i++) {
				if(map[99][i] == 2) {	//���� x��ǥ ã��
					curX = i;
					break;
				}
			}
			
			for(curY = 99; curY >= 0; curY--) {
				boolean moved = false;
				while(curX >0 && map[curY][curX-1] == 1) {	//�������� ���� �Ǵ� ����̸�
					curX--;
					moved = true;
				}
				if( !moved ) {	//�������� ������ ���������δ� ���� �ȵȴ�
					while(curX < 99 && map[curY][curX+1] == 1) {	//���������� ���� �Ǵ� ����̸�
						curX++;
					}
				}
			}
			
			System.out.printf("#%d %d\n", test_case, curX);
			
		}
	}
}
