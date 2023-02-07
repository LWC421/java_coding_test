import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class SWEA2805 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			int length = Integer.parseInt(in.readLine());
			
			int[][] map = new int[length][length];
			for(int i = 0; i < length; i++) {	//¸Ê ±¸¼ºÇÏ±â
				map[i] = Stream.of(in.readLine().split("")).mapToInt(Integer::parseInt).toArray();
			}
			
			int[] empty = new int[length];
			
			int index = 0;
			for(int i = length/2; i >= 0; i--) {
				empty[index++] = i;
			}
			for(int i = 1; i <= length/2; i++) {
				empty[index++] = i;
			}

			int sum = 0;
			
			for(int y = 0; y < length; y++) {
				for(int x = empty[y]; x < length-empty[y]; x++) {
					sum += map[y][x];
				}
			}
			System.out.printf("#%d %d\n", test_case, sum);
		}
	}
}
