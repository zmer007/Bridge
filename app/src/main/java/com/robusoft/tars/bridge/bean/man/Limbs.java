package com.robusoft.tars.bridge.bean.man;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Limbs {

	protected int startX;
	protected int startY;
	protected int stopX;
	protected int stopY;
	protected int length;
	protected float degrees;

	public Limbs() {
	}

	public Limbs(int limbLength) {
		length = limbLength;
	}

	public Limbs(int limbStartX, int limbStartY, int limbLength) {
		startX = limbStartX;
		startY = limbStartY;
		length = limbLength;
	}

	public void buildStopCoorBasisLengthAndAngle(int len, float degrees) {
		this.degrees = degrees;
		stopX = (int) (startX + len * Math.cos(Math.toRadians(degrees)));
		stopY = (int) (startY + len * Math.sin(Math.toRadians(degrees)));
	}

	public void buildStopCoorBasisAngle(float degrees) {
		buildStopCoorBasisLengthAndAngle(length, degrees);
	}

	protected void resetSite(int limbStartX, int limbStartY, int limbLength){
		startX = limbStartX;
		startY = limbStartY;
		length = limbLength;
	}
	
	public abstract void draw(Canvas canvas, Paint paint);
	public abstract void updateSite(int limbStartX, int limbStartY, int limbLength);

}
