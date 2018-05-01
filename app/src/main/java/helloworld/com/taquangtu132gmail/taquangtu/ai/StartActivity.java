package helloworld.com.taquangtu132gmail.taquangtu.ai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private ImageButton imbNewGame, imbRules;
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
                Toast.makeText(StartActivity.this,"See more on Internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
