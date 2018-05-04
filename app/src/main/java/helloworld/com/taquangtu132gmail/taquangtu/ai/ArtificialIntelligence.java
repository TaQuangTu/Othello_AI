package helloworld.com.taquangtu132gmail.taquangtu.ai;

import java.util.ArrayList;

public class ArtificialIntelligence
{

    ArrayList<ArrayList<String>> chessColorMatrix;
    int row,col;
    public ArtificialIntelligence(ArrayList<ArrayList<String>> chessColorMatrix)
    {
        this.row=chessColorMatrix.size();
        this.col=chessColorMatrix.get(0).size();
        /*this.chessColorMatrix = new ArrayList<>(row);
        for(int ii=0;ii<row;ii++) this.chessColorMatrix.add(new ArrayList<String>(col));
        for (int ii = 0; ii<row; ii++)
            for (int jj = 0; jj < col; jj++)
                this.chessColorMatrix.get(ii).add(chessColorMatrix.get(ii).get(jj));*/
    }
    class Move
    {
        public int row, col;
    }
    boolean isLegal(boolean isHuman, int x, int y, ArrayList<ArrayList<String>> chessColorMatrix)
    {
        int dy[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
        int dx[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
        //if that position is not empty return false
        if (chessColorMatrix.get(x).get(y) != "E") return false;
        //
        if (isHuman)
        {
            for (int i = 0; i<8; i++)
            {
                if (x + dx[i] >= 0 && x + dx[i]<row&&y + dy[i] >= 0 && y + dy[i]<col)
                {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("W"))
                    {
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i]<row&&tempY + dy[i] >= 0 && tempY + dy[i]<col)
                        {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("B")) return true;
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
            return false;
        }
        else
        {
            for (int i = 0; i<8; i++)
            {
                if (x + dx[i] >= 0 && x + dx[i]<row&&y + dy[i] >= 0 && y + dy[i]<col)
                {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("B"))
                    {
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i]<row&&tempY + dy[i] >= 0 && tempY + dy[i]<col)
                        {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("W")) return true;
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
            return false;
        }

    }
    boolean isMovesLeft(boolean isComputer, ArrayList<ArrayList<String>> chessColorMatrix)
    {
        for (int i = 0; i<row; i++)
            for (int j = 0; j<col; j++)
                if (isLegal(isComputer, i, j, chessColorMatrix))
                    return true;
        return false;
    }

    int evaluate(ArrayList<ArrayList<String>> chessColorMatrix)
    {
        //if both CPU and Human have no way to move, count number of white to determine winner
        if (!isMovesLeft(true, chessColorMatrix) && !isMovesLeft(false,chessColorMatrix))
        {
            int numOfWhite = 0;
            for (int i = 0; i<row; i++)
            {
                for (int j = 0; j < col;j++)
                    if (chessColorMatrix.get(i).get(j).equals("W")) numOfWhite++;
            }
            if (numOfWhite == row*col / 2) return 0; //draw
            if (numOfWhite<row*col / 2) return -1;//human win
            else return 1; //computer win
        }
        //if all is white return 1, it mean computer win
        //if all is black return -1, it mean human win
        //if board full slot, count number of each color and return 1 or -1 or 0(draw case) or 2( not over) depend on counting
        boolean whiteExists = false;
        boolean blackExists = false;

        //find white
        for (int i = 0; i<row; i++)
        {
            if (whiteExists) break;
            for (int j = 0; j < col; j++)
                if (chessColorMatrix.get(i).get(j).equals("W"))
                {
                    whiteExists = true;
                    break;
                }
        }
        //find black
        for (int i = 0; i<row; i++)
        {
            if (blackExists) break;
            for (int j = 0; j < col; j++)
                if (chessColorMatrix.get(i).get(j).equals("B"))
                {
                    blackExists = true;
                    break;
                }
        }
        if (!whiteExists) return -1; //human win
        if (!blackExists) return 1;  //computer win
        for (int i = 0; i<row; i++)
        {
            if (whiteExists) break;
            for (int j = 0; j < col; j++)
                if (chessColorMatrix.get(i).get(j).equals("E"))
                {
                    return 2;
                }
        }
        //in this case,the board has been full, then we count number of white and black to determine winner
        int numOfWhite = 0;
        for (int i = 0; i<row; i++)
        {
            for (int j = 0; j < col; j++)
                if (chessColorMatrix.get(i).get(j).equals("W")) numOfWhite++;
        }
        if (numOfWhite == row*col / 2) return 0; //draw
        if (numOfWhite<row*col / 2) return -1;//human win
        else return 1; //computer win
    }

    // This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
    void put(boolean isComputer, int x, int y, ArrayList<ArrayList<String>> chessColorMatrix)
    {

        int dy[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
        int dx[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
        if (isComputer == false)
        {
            for (int i = 0; i<8; i++)
            {
                if (x + dx[i] >= 0 && x + dx[i]<row&&y + dy[i] >= 0 && y + dy[i]<col)
                {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("W"))
                    {

                        //check for legal
                        boolean isAnInstance = false;
                        int x1 = x + dx[i], y1 = y + dy[i];
                        while (x1 + dx[i] >= 0 && x1 + dx[i]<row&&y1 + dy[i] >= 0 && y1 + dy[i]<col)
                        {
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("B"))
                            {
                                isAnInstance = true;
                                break;
                            }
                            x1 += dx[i];
                            y1 += dy[i];
                        }
                        if (!isAnInstance) continue;
                        //
                        chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("B");
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i]<row&&tempY + dy[i] >= 0 && tempY + dy[i]<col)
                        {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("B")) break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E")) break;
                            chessColorMatrix.get(tempX + dx[i]).set(tempY + dy[i],"B");
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i<8; i++)
            {
                if (x + dx[i] >= 0 && x + dx[i]<row&&y + dy[i] >= 0 && y + dy[i]<col)
                {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("B"))
                    {
                        //
                        boolean isAnInstance = false;
                        int x1 = x + dx[i], y1 = y + dy[i];
                        while (x1 + dx[i] >= 0 && x1 + dx[i]<row&&y1 + dy[i] >= 0 && y1 + dy[i]<col)
                        {
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("W"))
                            {
                                isAnInstance = true;
                                break;
                            }
                            x1 += dx[i];
                            y1 += dy[i];
                        }
                        if (!isAnInstance) continue;
                        //
                        chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("W");
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i]<row&&tempY + dy[i] >= 0 && tempY + dy[i]<col)
                        {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("W")) break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E")) break;
                            chessColorMatrix.get(tempX + dx[i]).set(tempY + dy[i],"W");
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
        }
    }

    int minimax(ArrayList<ArrayList<String>> chessColorMatrix, boolean isComputer)
    {
        int score = evaluate(chessColorMatrix);

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score != 2) return score;

        // If this maximizer's move
        if (isComputer)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<row; i++)
            {
                for (int j = 0; j<col; j++)
                {
                    // Check if cell is empty

                    if (isLegal(isComputer, i, j, chessColorMatrix))
                    {
                        // Make the move
                        ArrayList<ArrayList<String>> t = new ArrayList<>(row);
                        for(int ii=0;ii<row;ii++) t.add(new ArrayList<String>(col));
                        for (int ii = 0; ii<row; ii++)
                            for (int jj = 0; jj < col; jj++)
                                t.get(ii).add(chessColorMatrix.get(ii).get(jj));
                        put(isComputer, i, j, chessColorMatrix);

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best,
                                minimax(chessColorMatrix, !isComputer));

                        // Undo the move
                        //undo ca nhung nut da bi lat
                        chessColorMatrix = t;
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<row; i++)
            {
                for (int j = 0; j<col; j++)
                {
                    // Check if cell is empty
                    if (isLegal(isComputer, i, j, chessColorMatrix))
                    {
                        // Make the move
                        ArrayList<ArrayList<String>> t = new ArrayList<>(row);
                        for(int ii=0;ii<row;ii++) t.add(new ArrayList<String>(col));
                        for (int ii = 0; ii<row; ii++)
                            for (int jj = 0; jj < col; jj++)
                                t.get(ii).add(chessColorMatrix.get(ii).get(jj));
                        put(isComputer, i, j, chessColorMatrix);

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best,
                                minimax(chessColorMatrix, !isComputer));

                        // Undo the move
                        //undo ca nhung nut da bi lat
                        chessColorMatrix = t;
                    }
                }
            }
            return best;
        }
    }

    // This will return the best possible move for the player
    int findBestMove(ArrayList<ArrayList<String>> chessColorMatrix, boolean isComputer)
    {
        int bestVal = -1000;
        Move bestMove =  new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i<row; i++)
        {
            for (int j = 0; j<col; j++)
            {
                // Check if celll is empty
                if (isLegal(isComputer, i, j, chessColorMatrix))
                {
                    // Make the move
                    ArrayList<ArrayList<String>> t = new ArrayList<>(row);
                    for(int ii=0;ii<row;ii++) t.add(new ArrayList<String>(col));
                    for (int ii = 0; ii<row; ii++)
                        for (int jj = 0; jj < col; jj++)
                            t.get(ii).add(chessColorMatrix.get(ii).get(jj));
                    put(isComputer, i, j, chessColorMatrix);
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(chessColorMatrix, !isComputer);

                    // Undo the move
                    //undo ca nhung nut bi lat
                    chessColorMatrix = t;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove.row*col+bestMove.col;
    }
}
