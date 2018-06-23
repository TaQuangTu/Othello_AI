package helloworld.com.taquangtu132gmail.taquangtu.ai.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import helloworld.com.taquangtu132gmail.taquangtu.ai.aiCalculator.LookupTable;
import helloworld.com.taquangtu132gmail.taquangtu.ai.aiCalculator.Minimax;
import helloworld.com.taquangtu132gmail.taquangtu.ai.view.ChessAdapter;
import helloworld.com.taquangtu132gmail.taquangtu.ai.view.MainActivity;

public class PlayGame
{
    private MainActivity mainActivity;
    private int row, column;
    private ArrayList<String> chessColorArray;
    private GridView gvBoard;
    private ArrayList<ArrayList<String>> chessColorMaxtrix;
    private ChessAdapter chessAdapter;

    public PlayGame(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.column = mainActivity.getColumn();
        this.row = mainActivity.getRow();
        this.chessColorArray = mainActivity.chessColorArray;
        this.gvBoard = mainActivity.getGvBoard();
        this.chessAdapter = mainActivity.getChessAdapter();
        this.chessColorMaxtrix = new ArrayList<>(row);
        LookupTable.generateScoreBoard(row, column);
        for (int i = 0; i < row; i++) {
            this.chessColorMaxtrix.add(new ArrayList<String>(column));
        }
        updateChessColorMatrix();
        mainActivity.getImbUndo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo();
            }
        });
        mainActivity.getImbRedo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redo();
            }
        });
        setOnHumanClick();
    }

    public void redo() {
        int size = mainActivity.getStorgedState().size();
        if (mainActivity.stateIndex < size) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    chessColorArray.set(i * column + j, mainActivity.getStorgedState().get(mainActivity.stateIndex).get(i * column + j));
                    chessColorMaxtrix.get(i).set(j, mainActivity.getStorgedState().get(mainActivity.stateIndex).get(i * column + j));
                }
            }
            ++mainActivity.stateIndex;
            this.gvBoard.setAdapter(this.chessAdapter);
            this.chessAdapter.notifyDataSetChanged();
        }
    }
    public void undo()
    {
        if (mainActivity.stateIndex > 0)
        {
            if (mainActivity.stateIndex == mainActivity.getStorgedState().size())
            {
                ArrayList<String> boardState = new ArrayList<>();
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < column; j++)
                        boardState.add(mainActivity.chessColorArray.get(i * column + j));
                }
                mainActivity.getStorgedState().add(boardState);
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < column; j++)
                    {
                        chessColorArray.set(i * column + j, mainActivity.getStorgedState().get(mainActivity.stateIndex - 1).get(i * column + j));
                        chessColorMaxtrix.get(i).set(j, mainActivity.getStorgedState().get(mainActivity.stateIndex - 1).get(i * column + j));
                    }
                }
            }
            else
             {
                 if(mainActivity.stateIndex>1)
                 {
                     for (int i = 0; i < row; i++)
                     {
                         for (int j = 0; j < column; j++)
                         {
                             chessColorArray.set(i * column + j, mainActivity.getStorgedState().get(mainActivity.stateIndex - 2).get(i * column + j));
                             chessColorMaxtrix.get(i).set(j, mainActivity.getStorgedState().get(mainActivity.stateIndex - 2).get(i * column + j));
                         }
                     }
                     --mainActivity.stateIndex;
                 }
             }
        }
         this.gvBoard.setAdapter(this.chessAdapter);
         this.chessAdapter.notifyDataSetChanged();
    }
    boolean isLegal(boolean isComputer, int position)
    {
        int x=position/column;
        int y=position%column;
        int dy[]={-1,0,1,1,1,0,-1,-1};
        int dx[]={-1,-1,-1,0,1,1,1,0};
        //if that position is not empty return false
        if(chessColorArray.get(position).equals("E")==false) return false;
        //
        if(isComputer==false)
        {
            for(int i=0;i<8;i++)
            {
                if(x+dx[i]>=0&&x+dx[i]<row&&y+dy[i]>=0&&y+dy[i]<column)
                {
                    if(chessColorMaxtrix.get(x+dx[i]).get(y+dy[i]).equals("W"))
                    {
                        int tempX=x+dx[i];
                        int tempY=y+dy[i];
                        while(tempX+dx[i]>=0&&tempX+dx[i]<row&&tempY+dy[i]>=0&&tempY+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("E")) break;
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("B")) return true;
                            tempX+=dx[i];
                            tempY+=dy[i];
                        }
                    }
                }
            }
            return false;
        }
        else
        {
            for(int i=0;i<8;i++)
            {
                if(x+dx[i]>=0&&x+dx[i]<row&&y+dy[i]>=0&&y+dy[i]<column)
                {
                    if(chessColorMaxtrix.get(x+dx[i]).get(y+dy[i]).equals("B"))
                    {
                        int tempX=x+dx[i];
                        int tempY=y+dy[i];
                        while(tempX+dx[i]>=0&&tempX+dx[i]<row&&tempY+dy[i]>=0&&tempY+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("E")) break;
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("W")) return true;
                            tempX+=dx[i];
                            tempY+=dy[i];
                        }
                    }
                }
            }
            return false;
        }
    }
    void setOnHumanClick()
    {
        gvBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isLegal(false, i))
                {
                    mainActivity.countDownTimer.cancel();
                    mainActivity.timeToThink = 15;
                    mainActivity.pbTime.setProgress(mainActivity.timeToThink);
                    //store current state for undo
                    ArrayList<String> boardState = new ArrayList<>();
                    for (int it = 0; it < row; it++) {
                        for (int j = 0; j < column; j++) {
                            boardState.add(chessColorArray.get(it * column + j));
                        }
                    }
                    mainActivity.addState();
                    put(false, i);
                    chessAdapter.notifyDataSetChanged();
                    //action if finish
                    if(isFinish()!=2) //if not over
                    {
                        actionForFinish();
                        return;
                    }
                    //check if CPU has more one way to chose
                    else
                    {
                        if(isMoveLefts(true)==false)
                        {
                            Toast.makeText(mainActivity, "CPU has no way to continue, your turn", Toast.LENGTH_SHORT).show();
                            mainActivity.countDownTimer.start();
                        }
                        else
                        {
                            //START: INSERT AI CODE HERE================================================
                            Minimax minimax = new Minimax(chessColorMaxtrix);
                            int position=minimax.findBestMove(mainActivity.level);
                            //END: INSERT AI CODE HERE==================================================
                            put(true, position);
                            chessAdapter.notifyDataSetChanged();
                            if(isFinish()!=2)
                            {
                                actionForFinish();
                                return;
                            }
                            else
                            {
                                while(isMoveLefts(false)==false)
                                {
                                    //START: INSERT AI CODE HERE==================================================
                                    Toast.makeText(mainActivity, "You have no way to continue, CPU turn", Toast.LENGTH_SHORT).show();
                                    minimax = new Minimax(chessColorMaxtrix);
                                    position=minimax.findBestMove(mainActivity.level);
                                    //END: INSERT AI CODE HERE==================================================
                                    put(true, position);
                                    chessAdapter.notifyDataSetChanged();
                                    if(isFinish()!=2)
                                    {
                                        actionForFinish();
                                        return;
                                    }
                                }
                                mainActivity.countDownTimer.start();
                            }
                        }
                    }
                }
                else
                    {
                        Toast.makeText(mainActivity,"Not a legal move, try again", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    void put(boolean isComputer,int position)
    {
        int x=position/column;
        int y=position%column;
        int dy[]={-1,0,1,1,1,0,-1,-1};
        int dx[]={-1,-1,-1,0,1,1,1,0};
        if(isComputer==false)
        {
            for(int i=0;i<8;i++)
            {
                if(x+dx[i]>=0&&x+dx[i]<row&&y+dy[i]>=0&&y+dy[i]<column)
                {
                    if(chessColorMaxtrix.get(x+dx[i]).get(y+dy[i]).equals("W"))
                    {

                        //check for legal
                        boolean isAnInstance=false;
                        int x1=x+dx[i], y1=y+dy[i];
                        while(x1+dx[i]>=0&&x1+dx[i]<row&&y1+dy[i]>=0&&y1+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(x1+dx[i]).get(y1+dy[i]).equals("E")) break;
                            if(chessColorMaxtrix.get(x1+dx[i]).get(y1+dy[i]).equals("B"))
                            {
                                isAnInstance=true;
                                break;
                            }
                            x1+=dx[i];
                            y1+=dy[i];
                        }
                        if(!isAnInstance) continue;
                        //
                        chessColorMaxtrix.get(x).set(y,"B");
                        chessColorArray.set(position,"B");
                        chessColorMaxtrix.get(x+dx[i]).set(y+dy[i],"B");
                        chessColorArray.set((x+dx[i])*column+(y+dy[i]),"B");
                        int tempX=x+dx[i];
                        int tempY=y+dy[i];
                        while(tempX+dx[i]>=0&&tempX+dx[i]<row&&tempY+dy[i]>=0&&tempY+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("B")) break;
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("E")) break;
                            chessColorMaxtrix.get(tempX+dx[i]).set(tempY+dy[i],"B");
                            chessColorArray.set((tempX+dx[i])*column+(tempY+dy[i]),"B");
                            tempX+=dx[i];
                            tempY+=dy[i];
                        }
                    }
                }
            }
        }
        else
        {
            for(int i=0;i<8;i++)
            {
                if(x+dx[i]>=0&&x+dx[i]<row&&y+dy[i]>=0&&y+dy[i]<column)
                {
                    if(chessColorMaxtrix.get(x+dx[i]).get(y+dy[i]).equals("B"))
                    {
                        //
                        boolean isAnInstance=false;
                        int x1=x+dx[i], y1=y+dy[i];
                        while(x1+dx[i]>=0&&x1+dx[i]<row&&y1+dy[i]>=0&&y1+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(x1+dx[i]).get(y1+dy[i]).equals("E")) break;
                            if(chessColorMaxtrix.get(x1+dx[i]).get(y1+dy[i]).equals("W"))
                            {
                                isAnInstance=true;
                                break;
                            }
                            x1+=dx[i];
                            y1+=dy[i];
                        }
                        if(!isAnInstance) continue;
                        //
                        chessColorMaxtrix.get(x).set(y,"W");
                        chessColorArray.set(position,"W");
                        chessColorMaxtrix.get(x+dx[i]).set(y+dy[i],"W");
                        chessColorArray.set((x+dx[i])*column+(y+dy[i]),"W");
                        int tempX=x+dx[i];
                        int tempY=y+dy[i];
                        while(tempX+dx[i]>=0&&tempX+dx[i]<row&&tempY+dy[i]>=0&&tempY+dy[i]<column)
                        {
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("W")) break;
                            if(chessColorMaxtrix.get(tempX+dx[i]).get(tempY+dy[i]).equals("E")) break;
                            chessColorMaxtrix.get(tempX+dx[i]).set(tempY+dy[i],"W");
                            chessColorArray.set((tempX+dx[i])*column+(tempY+dy[i]),"W");
                            tempX+=dx[i];
                            tempY+=dy[i];
                        }
                    }
                }
            }
        }
        Log.d("put", "position: "+position);
    }
    int isFinish()
    {
        //if both CPU and Human have no way to move, count number of white to determine winner
        if(!isMoveLefts(true)&&!isMoveLefts(false))
        {
            int numOfWhite = 0;
            int numOfBlack = 0;
            for(int i=0;i<row*column;i++)
            {
                if(chessColorArray.get(i).equals("W")) numOfWhite++;
                if (chessColorArray.get(i).equals("B")) numOfBlack++;
            }
            if (numOfWhite == numOfBlack) return 0; //draw
            if (numOfWhite < numOfBlack) return -31000;//human win
            else return 31000; //computer win
        }
        //if all is white return 1, it mean computer win
        //if all is black return -1, it mean human win
        //if board full slot, count number of each color and return 1 or -1 or 0(draw case) or 2( not over) depend on counting
        boolean whiteExists = false;
        boolean blackExists = false;
        //find white
        for(int i=0;i<row*column;i++)
        {
            if(chessColorArray.get(i).equals("W"))
            {
                whiteExists=true;
                break;
            }
        }
        //find black
        for (int i = 0; i < row * column; i++) {
            if (chessColorArray.get(i).equals("B")) {
                blackExists = true;
                break;
            }
        }
        if(!whiteExists) return -31000; //human win
        if(!blackExists) return 31000;  //computer win
        for(int i=0;i<row*column;i++)
        {
            if(chessColorArray.get(i).equals("E")) return 2; //not over
        }
        //in this case,the board has been full, then we count number of white and black to determine winner
        int numOfWhite = 0;
        for(int i=0;i<row*column;i++)
        {
            if(chessColorArray.get(i).equals("W")) numOfWhite++;
        }
        if(numOfWhite==row*column/2) return 0; //draw
        if(numOfWhite<row*column/2) return -31000;//human win
        else return 31000; //computer win
    }
    boolean isMoveLefts(boolean isComputer)
    {
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
                if(isLegal(isComputer,i*column+j)) return true;
        }
        return false;
    }
    void actionForFinish()
    {
        int res = isFinish();
        if(res==-31000)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
            alert.setTitle("Congratulation!!!");
            alert.setMessage("Winner winner chicken dinner");
            alert.setPositiveButton("OK, Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mainActivity.resetBoard(row,column);
                }
            });
            alert.setCancelable(false);
            alert.show();
        }
        if(res==0)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
            alert.setTitle("DRAW!!!");
            alert.setMessage("You and CPU draw");
            alert.setCancelable(false);
            alert.setPositiveButton("OK, Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mainActivity.resetBoard(row,column);
                }
            });
            alert.show();
        }
        if(res==31000)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
            alert.setTitle("YOU LOSE!!!");
            alert.setMessage("Miệt mài quay tay, vận may sẽ đến");
            alert.setCancelable(false);
            alert.setPositiveButton("OK, Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mainActivity.resetBoard(row,column);
                }
            });
            alert.show();
        }
    }
    public void updateChessColorMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                chessColorMaxtrix.get(i).add(chessColorArray.get(i * column + j));
        }
    }
}
