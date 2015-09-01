package com.robusoft.tars.bridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.robusoft.tars.bridge.parts.GameView;

public class GameCanvasActivity extends AppCompatActivity {

    private GameView slidingGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas);


        slidingGame = (GameView) findViewById(R.id.m_sliding_game);
    }

    public void resetGame(View v) {
        slidingGame.resetGame();
    }
}
