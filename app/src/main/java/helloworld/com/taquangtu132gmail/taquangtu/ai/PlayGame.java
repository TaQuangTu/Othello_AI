package helloworld.com.taquangtu132gmail.taquangtu.ai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayGame
{
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
    boolean isLegal(boolean isComputer, int position)
    {
        int x=position/column;
        int y=position%column;
        /*int dy[]={-1,0,1,1,1,0,-1,-1};
        int dx[]={1,1,1,0,-1,-1,-1,0};*/
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
                    chessColorArray.set(i, "B");
                    chessColorMaxtrix.get(i / column).set(i % column, "B");
                    put(false, i);
                    chessAdapter.notifyDataSetChanged();
                    //action if finish
                    if(isFinish()!=2) //if not over
                    {
                        actionForFinish();
                        return;
                    }
                    //check if CPU has more one way to chose
                    if(isFinish()==2)
                    {
                        if(isMoveLefts(true)==false)
                        {
                            Toast.makeText(mainActivity, "CPU have no way to continue, your turn", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //START: INSERT AI CODE HERE================================================
                            Random random = new Random();
                            int position = random.nextInt(row * column);
                            while (!isLegal(true, position)) {
                                position = random.nextInt(row * column);
                            }
                          /*  ArtificialIntelligence ai = new ArtificialIntelligence(PlayGame.this.chessColorMaxtrix);
                            int position = ai.findBestMove(chessColorMaxtrix, true);*/
                            //END: INSERT AI CODE HERE==================================================
                            chessColorArray.set(position, "W");
                            chessColorMaxtrix.get(position / column).set(position % column, "W");
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
                                    Toast.makeText(mainActivity, "You have no way to continue, CPU turn", Toast.LENGTH_SHORT).show();
                                    //START: INSERT AI CODE HERE================================================
                                    random = new Random();
                                    position = random.nextInt(row * column);
                                    while (!isLegal(true, position)) {
                                        position = random.nextInt(row * column);
                                    }
                                   /* ai = new ArtificialIntelligence(PlayGame.this.chessColorMaxtrix);
                                    position = ai.findBestMove(chessColorMaxtrix, true);*/
                                    //END: INSERT AI CODE HERE==================================================
                                    chessColorArray.set(position, "W");
                                    chessColorMaxtrix.get(position / column).set(position % column, "W");
                                    put(true, position);
                                    chessAdapter.notifyDataSetChanged();
                                    if(isFinish()!=2)
                                    {
                                        actionForFinish();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                 else
                    {
                       Toast.makeText(mainActivity, "Can't put here", Toast.LENGTH_SHORT).show();
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
    }
    int isFinish()
    {
        //if both CPU and Human have no way to move, count number of white to determine winner
        if(!isMoveLefts(true)&&!isMoveLefts(false))
        {
            int numOfWhite = 0;
            for(int i=0;i<row*column;i++)
            {
                if(chessColorArray.get(i).equals("W")) numOfWhite++;
            }
            if(numOfWhite==row*column/2) return 0; //draw
            if(numOfWhite<row*column/2) return -1;//human win
            else return 1; //computer win
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
    boolean isMoveLefts(boolean isComputer)
    {
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
                if(isLegal(isComputer,i*column+j)) return true;
        }
        return false;
    }
    public void play() {
    }
    void actionForFinish()
    {
        int res = isFinish();
        if(res==-1)
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
            alert.setTitle("Congratulation!!!");
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
        if(res==1)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
            alert.setTitle("condolatory!!!");
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
}
