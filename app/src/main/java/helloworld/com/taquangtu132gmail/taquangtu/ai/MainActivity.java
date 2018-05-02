package helloworld.com.taquangtu132gmail.taquangtu.ai;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int row=8,column=8; //row and column of the board
    private GridView gvBoard;
    private ArrayList<String> chessColorArray;
    private ImageButton imbUndo, imbReUndo, imbNewgame;
    private ProgressBar pbTime;
    private Button btResetBoard;
    private ChessAdapter chessAdapter;
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView();
        //set board size
        setBoardSize();
        //declare adapter, arrayList, setAdapter
        initAdapter();
        setOnClickForView();
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
        PlayGame playGame = new PlayGame(this);
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
        btResetBoard= (Button) findViewById(R.id.btResetBoard);
    }
    public void setOnClickForView()
    {
        imbNewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set board size
                setBoardSize();
                //declare adapter, arrayList, setAdapter
                initAdapter();
                Toast.makeText(MainActivity.this,"YOU ARE BLACK, let's start", Toast.LENGTH_LONG).show();
            }
        });
        btResetBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("NEW BOARD");
                dialog.setContentView(R.layout.activity_dialog);
                dialog.show();
                Button btOK = dialog.findViewById(R.id.btOK);
                btOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((EditText)dialog.findViewById(R.id.etNewRow)).getText().toString().trim().equals(""))
                        {
                            Toast.makeText(MainActivity.this,"Must be a number, try again!!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        if(((EditText)dialog.findViewById(R.id.etNewColumn)).getText().toString().trim().equals(""))
                            {
                                Toast.makeText(MainActivity.this,"Must be a number, try again!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                                {
                                    int newRow =Integer.parseInt(((EditText)dialog.findViewById(R.id.etNewRow)).getText().toString().trim());
                                    int newCol =Integer.parseInt(((EditText)dialog.findViewById(R.id.etNewColumn)).getText().toString().trim());
                                    if(newRow<=3||newCol<=3) Toast.makeText(MainActivity.this,"row and column must greater 3, try again!!!", Toast.LENGTH_SHORT).show();
                                    else
                                        {
                                            resetBoard(newRow, newCol);
                                            dialog.dismiss();
                                        }

                                }
                    }
                });
            }
        });
    }
    void setBoardSize()  //and column
    {
        int width = getDeviceWidth();
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
    public ChessAdapter getChessAdapter()
    {
        return this.chessAdapter;
    }
    public void resetBoard(int row, int column)
    {
        this.row=row;
        this.column=column;
        setBoardSize();
        initAdapter();
        Toast.makeText(this,"YOU ARE BLACK, let's start", Toast.LENGTH_LONG).show();
        PlayGame playGame = new PlayGame(this);
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Warning!!!");
        alert.setMessage("Do you want to exit?");
        alert.setCancelable(true);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              MainActivity.this.finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        alert.show();
    }
}
