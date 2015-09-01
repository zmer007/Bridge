package com.robusoft.tars.bridge.bean.man;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.robusoft.tars.bridge.bean.GameDimens;

public class RightArm extends Limbs {

	private Path path;

	public RightArm(int armStartX, int armStartY, int armLength) {
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

		float ARCS_FACTOR = 1.5f;
		float AC = GameDimens.ARM_LENGTH / ARCS_FACTOR;
		float theta = degrees;

		float ax = startX;
		float ay = startY;
		
		float bx = stopX;
		float by = stopY;
		
		float cx = ax;
		float cy = (float) (ay + AC*Math.sin(Math.toRadians(theta)));

		float px = (bx + cx) / 2;
		float py = (by + cy) / 2;

		return new float[] { px, py };
	}
}
