package helloworld.com.taquangtu132gmail.taquangtu.ai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int row=15,column=15; //row and column of the board
    private GridView gvBoard;
    private ArrayList<String> chessColorArray;
    private ImageButton imbUndo, imbReUndo, imbNewgame;
    private ProgressBar pbTime;
    private ChessAdapter chessAdapter;
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public GridView getGvBoard() {
        return gvBoard;
    }

    public ArrayList<String> getChessColorArray() {
        return chessColorArray;
    }

    public ImageButton getImbUndo() {
        return imbUndo;
    }

    public ImageButton getImbReUndo() {
        return imbReUndo;
    }
    public ImageButton getImbNewgame() {
        return imbNewgame;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView();
        //set board size
        setBoardSize();
        //declare adapter, arrayList, setAdapter
        initAdapter();
        Toast.makeText(this,"YOU ARE BLACK, let's start", Toast.LENGTH_LONG).show();
    }
    void initAdapter() //and ArrayList, setAdapter
    {
        chessColorArray =new ArrayList<>();
        for(int i=0;i<row*column;i++)
        {
            chessColorArray.add("E");
        }
        //set four first piece
        chessColorArray.set((row/2-1)*column+column/2-1,"B");
        chessColorArray.set((row/2-1)*column+column/2,"W");
        chessColorArray.set((row/2)*column+column/2-1,"W");
        chessColorArray.set((row/2)*column+column/2,"B");
        chessAdapter = new ChessAdapter(MainActivity.this, R.layout.activity_chess_cell, chessColorArray);
        gvBoard.setAdapter(chessAdapter);

        //DEMO==================================================================
        PlayGame playGame = new PlayGame(this);
        playGame.play();
        //DEMO==================================================================
    }
    int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    void mapView()
    {
        pbTime      = (ProgressBar) findViewById(R.id.pbTime);
        gvBoard     = (GridView) findViewById(R.id.gvBoard);
        imbUndo     = (ImageButton) findViewById(R.id.imbUndo);
        imbReUndo   = (ImageButton) findViewById(R.id.imbReUndo);
        imbNewgame  = (ImageButton) findViewById(R.id.imbNewGame);
    }
    void setBoardSize()  //and column
    {
        int width = getDeviceWidth();
        gvBoard.getLayoutParams().height=width;
        if(column>row)
        {
            gvBoard.getLayoutParams().height=width*row/column;
        }
        else
            gvBoard.getLayoutParams().height=width;
        gvBoard.setNumColumns(column);
    }
    public ProgressBar getPBTime()
    {
        return this.pbTime;
    }
    public ChessAdapter getChessAdapter(){return this.chessAdapter;}
}
