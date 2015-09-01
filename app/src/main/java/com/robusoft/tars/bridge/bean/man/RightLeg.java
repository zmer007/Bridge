package com.robusoft.tars.bridge.bean.man;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.robusoft.tars.bridge.bean.GameDimens;

public class RightLeg extends Limbs {

	private Path path;

	public RightLeg(int armStartX, int armStartY, int armLength) {
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
		float theta = degrees;

		float ax = startX;
		float ay = startY;

		float bx = stopX;
		float by = stopY;

		float cx = (float) (ax + AC * Math.cos(Math.toRadians(theta)));
		float cy = ay;

		float px = (bx + cx) / 2;
		float py = (by + cy) / 2;

		return new float[] { px, py };
	}
}
