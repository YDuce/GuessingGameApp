package com.example.guessinggameapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guessinggameapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private GuessingGame mGame;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("GAME", mGame.getJSONStringFromThis());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        setSupportActionBar(binding.toolbar);
        setupButtonListeners();
        binding.fab.setOnClickListener(view -> mGame.startNewGame());
        mGame = savedInstanceState != null
                ? GuessingGame.getGuessingGameObjectFromJSONString(
                savedInstanceState.getString("GAME"))
                : new GuessingGame();
    }

    private void setContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupButtonListeners() {

        Button[] buttons = {
                findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)};

        for (Button currentButton : buttons) {
            currentButton.setOnClickListener(this::handleButton123Click);
        }

    }

    public void handleButton123Click(View view) {
        Button currentButton = (Button) view;
        String currentButtonText = currentButton.getText().toString();
        String resultsText = currentButtonText.equals(String.valueOf(mGame.getWinningNumber()))
                ? String.format(Locale.US, "Yes! %s is the right number.", currentButtonText)
                : String.format(Locale.US, "Sorry. %s is not the right number.", currentButtonText);

        Snackbar.make(view, resultsText, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}