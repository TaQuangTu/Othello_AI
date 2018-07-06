package helloworld.com.taquangtu132gmail.taquangtu.ai.aiCalculator;

import java.util.ArrayList;

public class LookupTable
{
    public static ArrayList<Integer> scoreBoard;
    public static void generateScoreBoard(int row, int column)
    {
        scoreBoard = new ArrayList<>(row*column);
        int a = row*column*5/3; //4 corners
        int b = -1; // adjacent to 4 corner( on edges)
        int c = -10; // adjacent to 4 corner( on diagonal lines)
        int d = row*column/(row+column-4);
        int e = (row*column)/(2*(column+row)); //4 edges
        int f = 1; //others cell

        if(row<6||column<6) //small board
        {
            for(int i=0;i<row*column;i++)
            {
                scoreBoard.add(f);
            }
            scoreBoard.set(0,a);
            scoreBoard.set(column-1,a);
            scoreBoard.set((row-1)*column,a);
            scoreBoard.set((row-1)*column+column-1,a);
        }
        else //row >=6 && column >=6
            {
                // f
                for(int i=0;i<column*row;i++) scoreBoard.add(f);
                // e
                for(int i=0;i<column;i++)
                {
                    scoreBoard.set(i,e);
                    scoreBoard.set((row-1)*column+i,e);
                }
                for(int i=0;i<row;i++)
                {
                    scoreBoard.set(i*column,e);
                    scoreBoard.set(i*column+column-1,e);
                }
                //b
                scoreBoard.set(1, b);
                scoreBoard.set(column-2,b);
                scoreBoard.set(column,b);
                scoreBoard.set(2*column-1,b);
                scoreBoard.set((row-2)*column, b);
                scoreBoard.set((row-1)*column-1, b);
                scoreBoard.set((row-1)*column+1, b);
                scoreBoard.set(row*column-2, b);
                // c
                scoreBoard.set(column+1, c);
                scoreBoard.set(2*column-2, c);
                scoreBoard.set((row-2)*column+1, c);
                scoreBoard.set((row-1)*column-2, c);
                //d
                scoreBoard.set(2, d);
                scoreBoard.set(column-3, d);
                scoreBoard.set(2*column, d);
                scoreBoard.set(3*column-1, d);
                scoreBoard.set((row-3)*column, d);
                scoreBoard.set((row-2)*column-1, d);
                scoreBoard.set((row-1)*column+2, d);
                scoreBoard.set(row*column-3,d);
                //a
                scoreBoard.set(0,a);
                scoreBoard.set(column-1,a);
                scoreBoard.set((row-1)*column,a);
                scoreBoard.set((row-1)*column+column-1,a);
            }
    }
}
