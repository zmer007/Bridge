package com.robusoft.tars.bridge.bean.bridge;

import com.robusoft.tars.bridge.bean.GameDimens;

public class Bridge {

	public static final int ROTATE_DEGREES = -90;
	private boolean isReverse;

	private int bridgeSite;
	private int bridgeLength;

	private int startX;
	private int startY;
	private int stopX;
	private int stopY;

	public Bridge(int site) {
		bridgeSite = site;
	}

	public Bridge(Bridge b) {
		bridgeSite = b.bridgeSite;
		bridgeLength = 0;
		startX = b.startX;
		startY = b.startY;
		stopX = b.stopX;
		stopY = b.stopY;
	}

	public void rebuildBridgeSite(int site) {
		bridgeSite = site;
		refreshHorizontalStartXYStopXY();
	}

	public void rebuildBridgeLength(int lengthInc) {
		if (isReverse) {
			descendBridge(lengthInc);
		} else {
			AscendBridge(lengthInc);
		}
		refreshVerticalStartXYStopXY();
	}

	private void descendBridge(int lengthInc) {
		bridgeLength -= lengthInc;
		if (bridgeLength < 0) {
			isReverse = !isReverse;
		}
	}

	private void AscendBridge(int lengthInc) {
		bridgeLength += lengthInc;
		if (bridgeLength >  GameDimens.PIER_HEIGHT * 2) {
			isReverse = !isReverse;
		}
	}

	private void refreshVerticalStartXYStopXY() {
		startX = bridgeSite;
		startY = GameDimens.PIER_HEIGHT * 2;
		stopX = bridgeSite;
		stopY = GameDimens.PIER_HEIGHT * 2 - bridgeLength;
	}

	private void refreshHorizontalStartXYStopXY() {
		startX = bridgeSite;
		startY = GameDimens.PIER_HEIGHT * 2;
		stopX = bridgeSite + bridgeLength;
		stopY = GameDimens.PIER_HEIGHT * 2;
	}

	public void rotateBridge(float degrees) {
		stopX = startX
				- (int) (bridgeLength * Math.sin(Math.toRadians(degrees)));
		stopY = startY
				- (int) (bridgeLength * Math.cos(Math.toRadians(degrees)));
	}

	public int getBridgeSite() {
		return bridgeSite;
	}

	public int getBridgeLength() {
		return bridgeLength;
	}

	public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}

	public float getStopX() {
		return stopX;
	}

	public float getStopY() {
		return stopY;
	}

}
