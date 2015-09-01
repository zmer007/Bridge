package com.robusoft.tars.bridge.bean;
public class AgorithmUtils {

	public static final int EASY_LEVEL = 0;
	public static final int MODERATE_LEVEL = 1;
	public static final int HARD_LEVEL = 2;

	public static int[] getComingSiteAndWidth(int level) {
		double rand = Math.random();
		int width = GameDimens.TOTAL_PIER_WIDTH[level][(int) (rand * GameDimens.TOTAL_PIER_WIDTH[level].length)];
		int site = GameDimens.RIVER_WIDTH + width;
		return new int[] { site, width };
	}

	public static int getDeltaComing(int level) {
		int total = GameDimens.RIVER_WIDTH - GameDimens.ANCHOR_SITE;
		int distance = (int) (GameDimens.MINIMAL_DISTANCE * (level + 1) + GameDimens.DISTANCE_LEVEL[level]
				* Math.random());

		return total - distance;
	}

	public static int getDeltaGoing(int goingSite) {
		return goingSite - GameDimens.ANCHOR_SITE;
	}
}
