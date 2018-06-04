package helloworld.com.taquangtu132gmail.taquangtu.ai.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import helloworld.com.taquangtu132gmail.taquangtu.ai.R;

public class StartActivity extends AppCompatActivity {

    private Button imbNewGame, imbRules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mapView();
        setOnClick();
    }
    void mapView()
    {
        this.imbNewGame = findViewById(R.id.imbNewGame);
        this.imbRules = findViewById(R.id.imbRules);
    }
    void setOnClick()
    {
        imbNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(intent);
            }
        });
        imbRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, RuleActivity.class);
                StartActivity.this.startActivity(intent);
            }
        });
    }
}
