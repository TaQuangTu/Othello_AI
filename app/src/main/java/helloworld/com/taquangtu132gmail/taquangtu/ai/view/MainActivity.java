package helloworld.com.taquangtu132gmail.taquangtu.ai.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import helloworld.com.taquangtu132gmail.taquangtu.ai.R;
import helloworld.com.taquangtu132gmail.taquangtu.ai.model.PlayGame;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ArrayList<String>> storgedState;
    private int row=8,column=8; //row and column of the board
    private Spinner spLevel;
    private GridView gvBoard;
    private ImageButton imbUndo,imbRedo, imbNewgame;
    private ChessAdapter chessAdapter;
    private Button btnResetBoard, btnRandom, btnChallenge;

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public GridView getGvBoard() {
        return gvBoard;
    }
    public ImageButton getImbUndo() {
        return imbUndo;
    }
    public ImageButton getImbRedo(){return imbRedo;}
    public int stateIndex = 0; //use for undo
    public int level = 1;
    public ArrayList<String> chessColorArray;

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
        //ProgressBar is played in CPU calculating time only
    }
    void initAdapter() //and ArrayList, setAdapter
    {
        chessColorArray =new ArrayList<>();
        for(int i=0;i<row*column;i++)
        {
            chessColorArray.add("E");
        }
        //set four first piece
        chessColorArray.set((row/2-1)*column+column/2-1,"W");
        chessColorArray.set((row/2-1)*column+column/2,"B");
        chessColorArray.set((row/2)*column+column/2-1,"B");
        chessColorArray.set((row/2)*column+column/2,"W");
        chessAdapter = new ChessAdapter(MainActivity.this, R.layout.activity_chess_cell, chessColorArray);
        gvBoard.setAdapter(chessAdapter);
        PlayGame playGame = new PlayGame(this);
    }

    public int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public void mapView() //and init something
    {
        btnChallenge = findViewById(R.id.btn_challenge);
        btnRandom    = findViewById(R.id.btn_random);
        imbRedo      = findViewById(R.id.imbRedo);
        imbUndo      = findViewById(R.id.imbUndo);
        spLevel      = findViewById(R.id.spinner);
        gvBoard      = findViewById(R.id.gvBoard);
        imbUndo      = findViewById(R.id.imbUndo);
        imbNewgame   = findViewById(R.id.imbNewGame);
        btnResetBoard = findViewById(R.id.btResetBoard);
        storgedState = new ArrayList<>();
    }
    public void addState()
    {
        //remove from size-1 to stateIndex-1
        for(int i=storgedState.size()-1;i>=stateIndex;i--)
        {
            storgedState.remove(i);
        }
        //then add new state
        ArrayList<String> boardState=new ArrayList<>();
        for(int i = 0 ;i<row;i++)
        {
            for(int j = 0; j <column; j++)
                boardState.add(chessColorArray.get(i*column+j));
        }
        storgedState.add(boardState);
        stateIndex++;
    }

    public void setOnClickForView()
    {
        //spiner setting
        String paths[]={"Noob","Chicken" ,"Profession", "Master"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLevel.setAdapter(adapter);
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if(i==0)//noob
                 {
                     if(row*column<=100)
                     {
                         level = 2;
                     }
                     else
                         {
                             level = 1;
                         }
                     //enable touching on btnRandom
                     btnRandom.setVisibility(View.VISIBLE);
                     btnChallenge.setVisibility(View.VISIBLE);
                 }
                 else
                 if(i==1)//chicken
                 {
                     if(row*column<=100)
                     {
                         level = 3;
                     }
                     else
                     {
                         level = 2;
                     }
                     //enable touching on btnRandom and btnChallenge
                     btnRandom.setVisibility(View.VISIBLE);
                     btnChallenge.setVisibility(View.VISIBLE);
                 }
                 else
                 if(i==2)//professional
                 {
                     if(row*column<=64)
                     {
                         level = 5;
                     }
                     else
                     {
                         level = 3;
                     }
                     //disable touching on btnRandom
                     btnRandom.setVisibility(View.GONE);
                     btnChallenge.setVisibility(View.GONE);
                 }
                 if(i==3)//master
                 {
                     if(row*column<=49)
                     {
                         level = 9;
                     }
                     else if(row*column<=100)
                     {
                         level = 6;
                     }
                     else
                         {
                             level = 4;
                         }
                     //disable touching on btnRandom
                     btnRandom.setVisibility(View.GONE);
                     btnChallenge.setVisibility(View.GONE);
                 }

                 MainActivity.this.resetBoard(row,column);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imbNewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set board size
                setBoardSize();
                //declare adapter, arrayList, setAdapter
                initAdapter();
                storgedState.clear();
                stateIndex=0;
            }
        });
        btnResetBoard.setOnClickListener(new View.OnClickListener() {
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
                                    if(newRow<=3||newCol<=3) Toast.makeText(MainActivity.this,"row and column must be greater 3, try again!!!", Toast.LENGTH_SHORT).show();
                                    else
                                        if(newRow>16||newCol>16)
                                        {
                                            Toast.makeText(MainActivity.this,"row and column must be less than 16, try again!!!", Toast.LENGTH_SHORT).show();
                                        }
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
    public ChessAdapter getChessAdapter()
    {
        return this.chessAdapter;
    }
    public void resetBoard(int row, int column)
    {
        this.stateIndex = 0;
        this.storgedState = new ArrayList<>();
        this.row=row;
        this.column=column;
        setBoardSize();
        initAdapter();
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

    public ArrayList<ArrayList<String>> getStorgedState() {
        return storgedState;
    }
    public Button getBtnRandom()
    {
        return this.btnRandom;
    }
    public Button getBtnChallenge()
    {
        return this.btnChallenge;
    }
}
