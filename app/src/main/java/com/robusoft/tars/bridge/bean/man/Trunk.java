package com.robusoft.tars.bridge.bean.man;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.robusoft.tars.bridge.bean.GameDimens;

public class Trunk {

	private int startX;
	private int startY;
	private int stopX;
	private int stopY;
	private Path path;

	public Trunk(int startX, int startY, int stopX, int stopY) {
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
		path = new Path();
	}

	public void draw(Canvas canvas, Paint paint) {
		buidPath();
		canvas.drawPath(path, paint);
	}
	
	private void buidPath(){
		path.reset();
		path.moveTo(startX, startY);
		final float curveX = startX - GameDimens.TRUNK_LENGTH / 6;
		final float curveY = startY + GameDimens.TRUNK_LENGTH / 4;
		path.quadTo(curveX, curveY, stopX, stopY);
	}
	
	public void updateSite(int startX, int startY, int stopX, int stopY){
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
	}

}
