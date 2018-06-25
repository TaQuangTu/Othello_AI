package helloworld.com.taquangtu132gmail.taquangtu.ai.aiCalculator;

import java.util.ArrayList;

public class LookupTable
{
    public static ArrayList<Integer> scoreBoard;
    public static void generateScoreBoard(int row, int column)
    {
        scoreBoard = new ArrayList<>(row*column);

        //positive value
        for(int i=0;i<column*row;i++) scoreBoard.add(1);
        for(int i=0;i<column;i++)
        {
            scoreBoard.set(i,row*column/8);
            scoreBoard.set((row-1)*column+i,row*column/8);
        }
        for(int i=0;i<row;i++)
        {
            scoreBoard.set(i*column,row*column/8);
            scoreBoard.set(i*column+column-1,row*column/8);
        }
        scoreBoard.set(0,row*column/2);
        scoreBoard.set(column-1,row*column/2);
        scoreBoard.set((row-1)*column,row*column/2);
        scoreBoard.set((row-1)*column+column-1,row*column/2);
        //negative value
        scoreBoard.set(1,-row*column/2);
        scoreBoard.set(column-2,-row*column/2);
        scoreBoard.set(column+1,-row*column/2);
        scoreBoard.set(2*column-1,-row*column/2);
        scoreBoard.set((row-2)*column,-row*column/2);
        scoreBoard.set((row-2)*column+column-1,-row*column/2);
        scoreBoard.set((row-1)*column+1,-row*column/2);
        scoreBoard.set(row*column-2,-row*column/2);
    }
}
