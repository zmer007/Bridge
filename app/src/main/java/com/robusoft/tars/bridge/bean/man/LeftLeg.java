package com.robusoft.tars.bridge.bean.man;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.robusoft.tars.bridge.bean.GameDimens;

public class LeftLeg extends Limbs {

	private Path path;

	public LeftLeg(int armStartX, int armStartY, int armLength) {
		super(armStartX, armStartY, armLength);
		path = new Path();
	}

	@Override
	public void updateSite(int armStartX, int armStartY, int armLength) {
		resetSite(armStartX, armStartY, armLength);
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		buildPath();
		canvas.drawPath(path, paint);
	}

	private void buildPath() {
		path.reset();
		path.moveTo(startX, startY);
		float[] controlCoor = getControlCoor();
		path.quadTo(controlCoor[0], controlCoor[1], stopX, stopY);
	}

	private float[] getControlCoor() {

		final float ARCS_FACTOR = 1f; 
		float AC = GameDimens.LEG_LENGTH / ARCS_FACTOR;
		float theta = degrees - WaveDegrees.RIGHT_ANGLE_RADIANS;

		float ax = startX;
		float ay = startY;
		
		float bx = stopX;
		float by = stopY;
		
		float cx = ax;
		float cy = (float) (ay+AC*Math.cos(Math.toRadians(theta)));
		
		float px = (bx + cx) / 2;
		float py = (by + cy) / 2;

		return new float[] { px, py };
	}
}
