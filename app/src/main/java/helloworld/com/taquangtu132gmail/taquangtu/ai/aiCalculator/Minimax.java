package helloworld.com.taquangtu132gmail.taquangtu.ai.aiCalculator;

import java.util.ArrayList;

public class Minimax {
    private int row, column;
    private ArrayList<ArrayList<String>> chessColorMatrix;

    public Minimax(ArrayList<ArrayList<String>> chessColorMatrix) {
        //copy data from argument to this.chessColorMatrix
        //first, get size
        this.row = chessColorMatrix.size();
        this.column = chessColorMatrix.get(0).size();
        //finally,init this.chessColorMatrix and copy data
        this.chessColorMatrix = new ArrayList<>();
        for (int i = 0; i < row; i++) this.chessColorMatrix.add(new ArrayList<String>());
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                this.chessColorMatrix.get(i).add(chessColorMatrix.get(i).get(j));
    }

    //check for putting at x,y a isComputer's piece
    public boolean isLegal(int x, int y, boolean isComputer) {
        int dy[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int dx[] = {-1, -1, -1, 0, 1, 1, 1, 0};
        //if that position is not empty return false
        if (!chessColorMatrix.get(x).get(y).equals("E")) return false;
        //
        if (isComputer == false) {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] < row && y + dy[i] >= 0 && y + dy[i] < column) {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("W")) {
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i] < row && tempY + dy[i] >= 0 && tempY + dy[i] < column) {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E"))
                                break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("B"))
                                return true;
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
            return false;
        } else {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] < row && y + dy[i] >= 0 && y + dy[i] < column) {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("B")) {
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i] < row && tempY + dy[i] >= 0 && tempY + dy[i] < column) {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("E"))
                                break;
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("W"))
                                return true;
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
            return false;
        }
    }
    public boolean isMovesLeft(boolean isComputer) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (isLegal(i, j, isComputer)) return true;
            }
        }
        return false;
    }

    public boolean isFinishState() {
        if (!isMovesLeft(true) && !isMovesLeft(false)) return true;
        return false;
    }

    public int evaluate() //only called when game over
    {
        int sumOfWhite = 0;
        int sumOfBlack = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (chessColorMatrix.get(i).get(j).equals("B")) sumOfBlack++;
                if (chessColorMatrix.get(i).get(j).equals("W")) sumOfWhite++;
            }
        }
        if (sumOfBlack == sumOfWhite) return 0; //draw
        if (sumOfBlack > sumOfWhite) return -31000; //human win
        return 31000; //computer win
    }

    public int minimax(boolean isComputer, int depth, int maxDepth, int alpha, int beta)
    {
        //if finish
        if(isFinishState())
        {
            int score = evaluate();
            //if computer win
            if(score == 31000) return score - depth;
            //else, if human win
            if(score == -31000) return score + depth;
            return 0;
        }
        //if not finished but board state has reached maximum depth
        //count sum of each type and give it an estimate score
        //TODO: let's modify this heuristic evaluation function to get the best move
        if(depth==maxDepth)
        {
            return heuristicEvaluation();
        }
        if(isComputer==false)
        {
            int best = 666666;
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<column;j++)
                {
                    if(isLegal(i,j,isComputer))
                    {
                        //init an array to store board state before changing
                        ArrayList<ArrayList<String>> t = new ArrayList<>();
                        for(int ii=0;ii<row;ii++) t.add(new ArrayList<String>());
                        for (int ii = 0; ii<row; ii++)
                            for (int jj = 0; jj < column; jj++)
                                t.get(ii).add(chessColorMatrix.get(ii).get(jj));

                        put(false,i,j);
                        best = Math.min(best, minimax(!isComputer,depth+1,maxDepth, alpha, beta));
                        beta = Math.min(best, beta);
                        //undo
                        chessColorMatrix = t;
                        if (beta <= alpha)
                            return beta;
                    }
                }
            }
            return best;
        } else {
            int best = -666666;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (isLegal(i, j, isComputer)) {
                        //init an array to store board state before changing
                        ArrayList<ArrayList<String>> t = new ArrayList<>();
                        for (int ii = 0; ii < row; ii++) t.add(new ArrayList<String>());
                        for (int ii = 0; ii < row; ii++)
                            for (int jj = 0; jj < column; jj++)
                                t.get(ii).add(chessColorMatrix.get(ii).get(jj));
                        put(true, i, j);
                        best = Math.max(best, minimax(!isComputer, depth + 1, maxDepth, alpha, beta));
                        alpha = Math.max(best, alpha);
                        //undo
                        chessColorMatrix = t;
                        if (beta <= alpha)
                            return alpha;
                    }
                }
            }
            return best;
        }
    }

    //find best move function just use to find best move for computer
    public int findBestMove(int maxDepth) {
        int position = -1; //on 1-D array
        int best = -2100000000;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (isLegal(i, j, true)) {
                    //init an array to store board state before changing
                    ArrayList<ArrayList<String>> t = new ArrayList<>();
                    for (int ii = 0; ii < row; ii++) t.add(new ArrayList<String>());
                    for (int ii = 0; ii < row; ii++)
                        for (int jj = 0; jj < column; jj++)
                            t.get(ii).add(chessColorMatrix.get(ii).get(jj));
                    put(true, i, j);
                    int moveVal = minimax(true, 0, maxDepth, -9999999, 9999999);
                    if (best < moveVal) {
                        best = moveVal;
                        position = i * column + j;
                    }
                    //undo chessColorMatrix
                    chessColorMatrix = t;
                }
            }
        }
        return position;
    }

    public void put(boolean isComputer, int x, int y) {

        int dy[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int dx[] = {-1, -1, -1, 0, 1, 1, 1, 0};
        if (isComputer == false) {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] < row && y + dy[i] >= 0 && y + dy[i] < column) {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("W")) {

                        //check for legal
                        boolean isAnInstance = false;
                        int x1 = x + dx[i], y1 = y + dy[i];
                        while (x1 + dx[i] >= 0 && x1 + dx[i] < row && y1 + dy[i] >= 0 && y1 + dy[i] < column) {
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("B")) {
                                isAnInstance = true;
                                break;
                            }
                            x1 += dx[i];
                            y1 += dy[i];
                        }
                        if (isAnInstance == false) continue;
                        //
                        chessColorMatrix.get(x).set(y, "B");
                        chessColorMatrix.get(x + dx[i]).set(y + dy[i], "B");
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i] < row && tempY + dy[i] >= 0 && tempY + dy[i] < column) {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("B"))
                                break;
                            chessColorMatrix.get(tempX + dx[i]).set(tempY + dy[i], "B");
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] < row && y + dy[i] >= 0 && y + dy[i] < column) {
                    if (chessColorMatrix.get(x + dx[i]).get(y + dy[i]).equals("B")) {
                        //
                        boolean isAnInstance = false;
                        int x1 = x + dx[i], y1 = y + dy[i];
                        while (x1 + dx[i] >= 0 && x1 + dx[i] < row && y1 + dy[i] >= 0 && y1 + dy[i] < column) {
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("E")) break;
                            if (chessColorMatrix.get(x1 + dx[i]).get(y1 + dy[i]).equals("W")) {
                                isAnInstance = true;
                                break;
                            }
                            x1 += dx[i];
                            y1 += dy[i];
                        }
                        if (!isAnInstance) continue;
                        //
                        chessColorMatrix.get(x).set(y, "W");
                        chessColorMatrix.get(x + dx[i]).set(y + dy[i], "W");
                        int tempX = x + dx[i];
                        int tempY = y + dy[i];
                        while (tempX + dx[i] >= 0 && tempX + dx[i] < row && tempY + dy[i] >= 0 && tempY + dy[i] < column) {
                            if (chessColorMatrix.get(tempX + dx[i]).get(tempY + dy[i]).equals("W"))
                                break;
                            chessColorMatrix.get(tempX + dx[i]).set(tempY + dy[i], "W");
                            tempX += dx[i];
                            tempY += dy[i];
                        }
                    }
                }
            }
        }
    }
    int heuristicEvaluation()
    {
        int sumOfWhite = 0;
        int sumOfBlack = 0;
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
            {
                if(chessColorMatrix.get(i).get(j).equals("B")) sumOfBlack+=LookupTable.scoreBoard.get(i*column+j);
                if(chessColorMatrix.get(i).get(j).equals("W")) sumOfWhite+=LookupTable.scoreBoard.get(i*column+j);
            }
        }
        if(chessColorMatrix.get(0).get(1).equals("B"))
        {
            if(chessColorMatrix.get(0).get(0).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(1).get(0).equals("B"))
        {
            if(chessColorMatrix.get(0).get(0).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(0).get(column-2).equals("B"))
        {
            if(chessColorMatrix.get(0).get(column-1).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(1).get(column-1).equals("B"))
        {
            if(chessColorMatrix.get(0).get(column-1).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(row-2).get(0).equals("B"))
        {
            if(chessColorMatrix.get(row-1).get(0).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(row-2).get(column-1).equals("B"))
        {
            if(chessColorMatrix.get(row-1).get(column-1).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(row-1).get(1).equals("B"))
        {
            if(chessColorMatrix.get(row-1).get(0).equals("B")) sumOfBlack+=(row*column/2);
        }
        if(chessColorMatrix.get(row-1).get(column-2).equals("B"))
        {
            if(chessColorMatrix.get(row-1).get(column-1).equals("B")) sumOfBlack+=(row*column/2);
        }
        //
        if(chessColorMatrix.get(0).get(1).equals("W"))
        {
            if(chessColorMatrix.get(0).get(0).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(1).get(0).equals("W"))
        {
            if(chessColorMatrix.get(0).get(0).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(0).get(column-2).equals("W"))
        {
            if(chessColorMatrix.get(0).get(column-1).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(1).get(column-1).equals("W"))
        {
            if(chessColorMatrix.get(0).get(column-1).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(row-2).get(0).equals("W"))
        {
            if(chessColorMatrix.get(row-1).get(0).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(row-2).get(column-1).equals("W"))
        {
            if(chessColorMatrix.get(row-1).get(column-1).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(row-1).get(1).equals("W"))
        {
            if(chessColorMatrix.get(row-1).get(0).equals("W")) sumOfWhite+=(row*column/2);
        }
        if(chessColorMatrix.get(row-1).get(column-2).equals("W"))
        {
            if(chessColorMatrix.get(row-1).get(column-1).equals("W")) sumOfWhite+=(row*column/2);
        }
        return sumOfWhite - sumOfBlack;
    }
}
