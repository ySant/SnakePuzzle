/* Solving Snake-in-a-Cube puzzle using backtracking
*  Yudi Santoso - Fall 2015
*/
import java.util.Scanner;

public class snakeCube {

	static int[][] x = new int[27][3];  // position of each cube
	static int solCount = 0;		// number of solution
	static boolean[][][] a = new boolean[3][3][3];  // is cell free?
	static boolean[] t = new boolean[27];  // cube straight/elbow?
	static int[] s = new int[27];  // cube direction +/-
	static int[] xyz = new int[27];  // cube orientation xyz


	static void nQ(int cube){
		int nx = pow(-1,s[cube-1]);
		int[] cell = new int[3];

		for (int i = 0; i<3; i++){
			cell[i] = x[cube-1][i];
			if (xyz[cube-1]==i) cell[i] += nx;
		}

		if (((cell[0] >= 0) && (cell[0] < 3))
			&& ((cell[1] >= 0) && (cell[1] < 3))
			&& ((cell[2] >= 0) && (cell[2] < 3))){
		if (!a[cell[0]][cell[1]][cell[2]]){		
			for (int i = 0; i<3; i++){
				x[cube][i] = cell[i];
			}
			a[cell[0]][cell[1]][cell[2]] = true;
			if (cube < 26){
				if (t[cube]) {		// straight
					s[cube] = s[cube-1];
					xyz[cube] = xyz[cube-1];
					nQ(cube+1);
				} else {				// elbow
					for (int dir = 0; dir <=1; dir++){
						s[cube] = dir;
						for (int ax = 0; ax <=2; ax++){
							if (ax != xyz[cube-1]){ 
								xyz[cube] = ax;
								nQ(cube+1);
							}
						}
					}
				}
			} else {
				printSoln();	
				solCount++;	
			}
			a[cell[0]][cell[1]][cell[2]] = false;
		}
		}
	}

	static void printSoln(){
		for (int i = 0; i<27; i++){ 
			for (int j = 0; j<3; j++){ 
				System.out.print(x[i][j]);
			}
			System.out.print("\t");
		}
		System.out.println("");
	} 

	static int pow(int a, int b){
		if (b == 0) return 1;
		return a*pow(a,--b);
	}

	public static void main(String[] args) {

		String[] Snake = new String[5];
		Snake[1] = "SSESESESEEEESESEEESEESEEESS";
		Snake[2] = "EEEEEEEEEEEEEEEEEEEEEEEEEEE"; // all elbow
		Snake[3] = "SEEEESESEEEEEEEEEEESESEEEES"; // another snake

		System.out.println("Snake-in-a-Cube");
		System.out.println("Snake 1: "+Snake[1]);
		System.out.println("Snake 2: "+Snake[2]);
		System.out.println("Snake 3: "+Snake[3]);

		Scanner reader = new Scanner(System.in);
		System.out.print("Choose snake (1/2/3): ");
		int sn = reader.nextInt(); // snake number
		
		if ((sn == 1) || (sn == 2) || (sn == 3)){
		String theSnake = Snake[sn];

		char[] ch = new char[27];
		for (int i=0; i<27; i++) {
			ch[i] = theSnake.charAt(i);
//			System.out.print(i + "\t" + ch[i] + "\t");
			if (ch[i] == 'S') t[i] = true;
			if (ch[i] == 'E') t[i] = false;
//			System.out.println(t[i]);
		}

// Placing the first cube:
		for (int cx = 0; cx < 3; cx++){
		for (int cy = 0; cy < 3; cy++){
		for (int cz = 0; cz < 3; cz++){
			x[0][0] = cx;
			x[0][1] = cy;
			x[0][2] = cz;
			a[cx][cy][cz] = true;
			for (int dir = 0; dir <=1; dir++){
				s[0] = dir;
				for (int ax = 0; ax <=2; ax++){
					xyz[0] = ax;
						nQ(1);
				}
			}
			a[cx][cy][cz] = false;
		}
		}
		}

		System.out.println("Number of solutions:"+solCount);

		}
	}
}
