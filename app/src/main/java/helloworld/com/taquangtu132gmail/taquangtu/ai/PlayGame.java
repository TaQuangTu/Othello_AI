package helloworld.com.taquangtu132gmail.taquangtu.ai;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayGame
{
    boolean humanTurn=true;
    int remainingTime = 90000;
   private MainActivity mainActivity;
   private int row, column;
   private ArrayList<String> chessColorArray;
   private GridView gvBoard;
   private ProgressBar pbTime;
   private ArrayList<ArrayList<String>> chessColorMaxtrix;
   private ChessAdapter chessAdapter;
    public PlayGame(MainActivity mainActivity) {
        this.mainActivity    = mainActivity;
        this.column          = mainActivity.getColumn();
        this.row             = mainActivity.getRow();
        this.chessColorArray = mainActivity.getChessColorArray();
        this.gvBoard         = mainActivity.getGvBoard();
        this.pbTime          = mainActivity.getPBTime();
        this.chessAdapter    = mainActivity.getChessAdapter();
        this.chessColorMaxtrix=new ArrayList<>(row);
        for(int i=0;i<row;i++)
        {
            this.chessColorMaxtrix.add(new ArrayList<String>(column));
        }
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
                chessColorMaxtrix.get(i).add(chessColorArray.get(i*column+j));
        }
        setOnHumanClick();
    }
    boolean isLegal(boolean turn, int position)
    {
        int x=position/column;
        int y=position%column;
        int dy[]={-1,0,1,1,1,0,-1,-1};
        int dx[]={1,1,1,0,-1,-1,-1,0};
        //if that position is not empty return false
        if(chessColorArray.get(position).equals("E")==false) return false;
        //
        if(turn == true)
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
                if(humanTurn==false)  //not human turn
                {
                    Toast.makeText(mainActivity,"CPU is calculating", Toast.LENGTH_SHORT).show();
                }
                else //check for legal position
                {
                    //if legal, set Black for this cell
                    if(isLegal(humanTurn, i))
                    {
                        chessColorArray.set(i,"B");
                        chessColorMaxtrix.get(i/column).set(i%column,"B");
                        chessAdapter.notifyDataSetChanged();
                        //find best move
                        Random random = new Random();
                        int position = random.nextInt(row*column);
                        humanTurn=false;
                        while(!isLegal(humanTurn,position))
                        {
                            position=random.nextInt(row*column);
                        }

                        chessColorArray.set(position,"W");
                        chessColorMaxtrix.get(position/column).set(position%column,"W");
                        chessAdapter.notifyDataSetChanged();
                        humanTurn=true;
                    }
                    //else make test
                    else
                        Toast.makeText(mainActivity, "Can't put here",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    int isFinish()
    {
        //if all is white return 1, it mean computer win
        //if all is black return -1, it mean human win
        //if board full slot, count number of each color and return 1 or -1 or 0(draw case) or 2( not over) depend on counting
        boolean whiteExists = false;
        boolean blackExists = false;
        boolean boardFull   = false;
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
        for(int i=0;i<row*column;i++)
        {
            if(chessColorArray.get(i).equals("B"))
            {
                blackExists=true;
                break;
            }
        }
        if(!whiteExists) return -1; //human win
        if(!blackExists) return 1;  //computer win
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
        if(numOfWhite<row*column/2) return -1;//human win
        else return 1; //computer win
    }
    public void humanTurn()
    {

    }
    public void play()
    {
        /*if(isFinish()==2) //not over
        {
            if(humanTurn)
            {
                //display a progress bar and wait for human calculating
                pbTime.setMax(90000);
                pbTime.setProgress(90000);
                remainingTime = 90000;
                final String time = "90000";
                CountDownTimer countDownTimer =new CountDownTimer(90000,100) {
                    @Override
                    public void onTick(long l) {
                        pbTime.setProgress(remainingTime-100);
                        remainingTime-=100;
                        //if human done while progress bar running then break immediately
                        if(humanTurn==false) pbTime.setProgress(0);
                        humanTurn=false; // assign computer for the next turn
                    }
                    @Override
                    public void onFinish() {
                        humanTurn=false;
                    }
                };
                countDownTimer.start();
            }
            else
            {
                //calculate the best move for computer
                Random random = new Random();
                int position = random.nextInt(row*column);
                while(!isLegal(humanTurn,position))
                {
                    position=random.nextInt(row*column);
                    chessColorArray.set(position,"W");
                    chessColorMaxtrix.get(position/column).set(position%column,"W");
                    chessAdapter.notifyDataSetChanged();
                }
                humanTurn=true;
            }
        }*/
    }
}
