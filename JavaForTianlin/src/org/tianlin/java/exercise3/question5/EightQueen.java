package org.tianlin.java.exercise3.question5;

public class EightQueen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] panel = new int[8][8];
		int _x = 0, _y = 0;
		panel[0][0]=-1;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (panel[x][y] != -1) {
					for (int k = 0; k < 8; k++) {
						panel[x][k] = -1;
						panel[k][y] = -1;
					}
					panel[x][y] = 1;
					// top-left
					_x = x - 1;
					_y = y - 1;
					while (_x >= 0 && _y >= 0) {
						panel[_x][_y] = -1;
						_x--;
						_y--;
					}
					// top-right
					_x = x + 1;
					_y = y - 1;
					while (_x < 8 && _y >= 0) {
						panel[_x][_y] = -1;
						_x++;
						_y--;
					}
					// bottom-left
					_x = x - 1;
					_y = y + 1;
					while (_x >= 0 && _y < 8) {
						panel[_x][_y] = -1;
						_x--;
						_y++;
					}
					// bottom-right
					_x = x + 1;
					_y = y + 1;
					while (_x < 8 && _y < 8) {
						panel[_x][_y] = -1;
						_x++;
						_y++;
					}
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (panel[i][j] == 1) {
					System.out.print("Q ");
				} else {
					System.out.print("X ");
				}
			}
			System.out.println();
		}
		
		
	}

	

}
