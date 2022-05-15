package main;

public class GameLogic
{
    private int Row ;
    private int Col ;
    private int[][] isAlive;
    public GameLogic(int row,int col)
    {
        Row = row;
        Col = col;
        isAlive = new int[row + 2][col + 2];
    }


    public int[][] getIsAlive()
    {
        return isAlive;
    }

    public void randomInit()
    {
        for (int i = 1; i <= Row; i++)
        {
            for (int j = 1; j <= Col; j++)
            {
                isAlive[i][j] = Math.random() < 0.1? 1 : 0;
            }
        }
    }
    private int getAliveNum(int row, int col)
    {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++)
        {
            for (int j = col - 1; j <= col + 1; j++)
            {
                if(i == row && j == col)
                {
                    continue;
                }
                if(isAlive[i][j]==1)
                {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean judgeIsAllDead(){
        for(int r = 1; r <= Row; r++)
        {
            for (int c = 1; c <= Col; c++)
            {
                if (isAlive[r][c] == 1)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void update()
    {
        // 创建比界面大一圈的数组
        int[][] newArray = new int[Row + 2][Col + 2];

        // 判断每一个细胞的死活
        for (int i = 1; i <= Row; i++)
        {
            for (int j = 1; j <= Col; j++)
            {
                switch (getAliveNum(i, j)) {
                    case 2 ->
                        // 周围有两个细胞，细胞状态保持不变
                            newArray[i][j] = isAlive[i][j];
                    case 3 ->
                        // 周围有三个细胞，死细胞变为活细胞
                            newArray[i][j] = 1;
                    default ->
                        // 其它情况细胞为死
                            newArray[i][j] = 0;
                }
            }
        }
        // 保存新的细胞状态
        for (int i = 1; i <= Row; i++)
        {
            for (int j = 1; j <= Col; j++)
            {
                isAlive[i][j] = newArray[i][j];
            }
        }
    }

}


