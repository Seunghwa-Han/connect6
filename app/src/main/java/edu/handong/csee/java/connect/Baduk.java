package edu.handong.csee.java.connect;

public class Baduk {

	CheckerBoard board;

//	public Baduk(CheckerBoard board) {
//		this.board = board;
//	}

	public Baduk() {
		
	}
	
	public void detectMyHorizentalThreat(int[][] now) { // 가로
		int[][] threat = new int[19][19];
		/*
		 * 내거니까 1이 4개 0이 2개 또는 1이 5개 0이 1개 또는 1이 6개 0이 0개
		 */
		int threatNum = 0;
		for (int i = 0; i < 19; i++) {
			int leftWindow = 0;
			int rightWindow = 5;
			int count1 = 0;
			int count0 = 0;
			if (leftWindow == 0) { // 맨 처음일때 (0~5)
				for (int k = leftWindow; k <= rightWindow; k++) {
					if (now[k][i] == 2) {
						threat[k][i] = 2;
					} else if (now[k][i] == 1) {
						count1++;
						threat[k][i] = 1;
					} else if (now[k][i] == 0)
						count0++;
				}
				if ((count1 == 4 && count0 == 2) || (count1 == 5 && count0 == 1) || (count1 == 6 && count0 == 0)) {
					threatNum++;
					for (int k = leftWindow; k <= rightWindow; k++) {
						if (threat[k][i] <= 0) {
							threat[k][i] -= 1;
						}
					}
				}
			}
			while (leftWindow < 13) { // 그 다음부터
				if (now[leftWindow][i] == 0)
					count0--;
				else if (now[leftWindow][i] == 1)
					count1--;

				if (now[rightWindow + 1][i] == 1) { // 그 다음 오른쪽
					threat[rightWindow + 1][i] = 1;
					count1++;
				} else if (now[rightWindow + 1][i] == 2) {
					threat[rightWindow + 1][i] = 2;
				} else if (now[rightWindow + 1][i] == 0) {
					count0++;
				}

				if ((count1 == 4 && count0 == 2) || (count1 == 5 && count0 == 1) || (count1 == 6 && count0 == 0)) {
					threatNum++;
					for (int k = leftWindow + 1; k <= rightWindow + 1; k++) {
						if (threat[k][i] <= 0) {
							threat[k][i] -= 1;
						}
					}
				}
				leftWindow++;
				rightWindow++;
			}

		}
		System.out.println(threatNum);
		for (int i = 0; i < threat.length; i++) {
			for (int j = 0; j < threat[i].length; j++) {
				System.out.print(threat[j][i] + " ");
			}
			System.out.println();
		}

		detectMyVerticalThreat2(threat, threatNum);
	}

	public void detectMyVerticalThreat2(int[][] hori, int horiThreatNum) {
		int[][] verticalResult = new int[19][19];

		int vhThreatNum = 0;
		int threatNum = 0;
		for (int i = 0; i < 19; i++) {
			int leftWindow = 0;
			int rightWindow = 5;
			int count1 = 0;
			int count0 = 0;
			if (leftWindow == 0) { // 맨 처음일때 (0~5)
				for (int k = leftWindow; k <= rightWindow; k++) {
					if (hori[i][k] == 2) {
						verticalResult[i][k] = 2;
					} else if (hori[i][k] == 1) {
						count1++;
						verticalResult[i][k] = 1;
					} else if (hori[i][k] <= 0)
						count0++;
				}
				if ((count1 == 4 && count0 == 2) || (count1 == 5 && count0 == 1) || (count1 == 6 && count0 == 0)) {
					threatNum++;
					for (int k = leftWindow; k <= rightWindow; k++) {
						if (verticalResult[i][k] == 0) {
							hori[i][k] -= 10;
						}
					}
				}
			}
			while (leftWindow < 13) { // 그 다음부터
				if (hori[i][leftWindow] <= 0)
					count0--;
				else if (hori[i][leftWindow] == 1)
					count1--;

				if (hori[i][rightWindow + 1] == 1) { // 그 다음 오른쪽
					verticalResult[i][rightWindow + 1] = 1;
					count1++;
				} else if (hori[i][rightWindow + 1] == 2) {
					verticalResult[rightWindow + 1][i] = 2;
				} else if (hori[i][rightWindow + 1] == 0) {
					count0++;
				}
				if ((count1 == 4 && count0 == 2) || (count1 == 5 && count0 == 1) || (count1 == 6 && count0 == 0)) {
					threatNum++;
					for (int k = leftWindow + 1; k <= rightWindow + 1; k++) {
						if (verticalResult[i][k] == 0) {
							hori[i][k] -= 10;
						}
					}
				}
				leftWindow++;
				rightWindow++;
			}
		}
		vhThreatNum = threatNum + horiThreatNum;
		System.out.println(vhThreatNum);
		for (int i = 0; i < verticalResult.length; i++) {
			for (int j = 0; j < verticalResult[i].length; j++) {
				System.out.print(hori[j][i] + " ");
			}
			System.out.println();
		}
	}
	
	

}
