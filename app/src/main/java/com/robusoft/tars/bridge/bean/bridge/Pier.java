package com.robusoft.tars.bridge.bean.bridge;

import android.graphics.RectF;

import com.robusoft.tars.bridge.bean.GameDimens;

public class Pier {

	private int pierSite;
	private int pierWidth;

	private Bridge bridge;

	private int oldPierSite;

	public Pier(int pierSite, int pierWidth) {
		this.pierSite = pierSite;
		this.pierWidth = pierWidth;
		this.oldPierSite = pierSite;
		bridge = new Bridge(pierSite);
	}

	public void updatePierSite(int diff) {
		pierSite = oldPierSite + diff;
	}

	public RectF getRect() {
		return new RectF(pierSite - pierWidth, 0, pierSite, GameDimens.PIER_HEIGHT);
	}

	public void rebuildPier(int pierSite, int pierWidth) {
		this.pierSite = pierSite;
		this.pierWidth = pierWidth;
		this.oldPierSite = pierSite;
	}

	public Bridge getBridge() {
		return bridge;
	}

	public void setBridge(Bridge bridge) {
		this.bridge = new Bridge(bridge);
	}

	public int getPierSite() {
		return pierSite;
	}

	public int getPierWidth() {
		return pierWidth;
	}

	public int getOldPierSite() {
		return oldPierSite;
	}
}
