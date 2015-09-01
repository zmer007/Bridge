package com.robusoft.tars.bridge.bean.man;

public class TheMan {

	private boolean quiet = true;
	private int manCoorX;
	private int manCoorY;
	private int manOldCoorX;
	private int manOldCoorY;

	public TheMan(int coorX, int coorY) {
		resetMan(coorX, coorY);
	}

	public void resetMan(int coorX, int coorY){
		this.manCoorX = coorX;
		this.manCoorY = coorY;
		this.manOldCoorX = manCoorX;
		this.manOldCoorY = manCoorY;
	}
	
	public void updateManSite(int diff) {
		this.manCoorX = manOldCoorX + diff;
	}

	public void updateDropSite(int diff) {
		this.manCoorY = manOldCoorY + diff;
	}

	public void setQuiet(boolean quiet) {
		this.quiet = quiet;
	}

	public boolean isQuiet() {
		return quiet;
	}

	public int getManCoorX() {
		return manCoorX;
	}

	public void setManCoorX(int manCoorX) {
		this.manCoorX = manCoorX;
	}

	public int getManCoorY() {
		return manCoorY;
	}

	public void setManCoorY(int manCoorY) {
		this.manCoorY = manCoorY;
	}

	public void refreshOldCoorX() {
		manOldCoorX = manCoorX;
	}

	public void refreshOldCoorY() {
		manOldCoorY = manCoorY;
	}
}
