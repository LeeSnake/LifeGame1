package main;
public class GameService
{
    private int Row = 40;
    private int Col = 40;
    private int[][] isAlive;

    // 细胞是否死完
    private boolean isAllDead;
    // 是否正在繁衍
    private boolean isRunning;
    public GameService()
    {
        isAlive = new int[Row + 2][Col + 2];
        // 开局全死为0
        for (int i = 0; i <= Row + 1; i++)
        {
            for (int j = 0; j <= Col + 1; j++)
            {
                isAlive[i][j] = 0;
            }
        }
    }

    public int[][] getIsAlive() {
        return isAlive;
    }
    public void setIsAlive(int[][] isAlive)
    {
        this.isAlive = isAlive;
    }
    // 获取细胞行数
    public int getRow()
    {
        return Row;
    }

    // 获取细胞列数
    public int getCol()
    {
        return Col;
    }

    // 获取细胞是否死完
    public boolean getIsAllDead()
    {
        return isAllDead;
    }

    // 设置细胞是否死完
    public void setIsAllDead(boolean isDead)
    {
        this.isAllDead = isDead;
    }

    // 获取是否正在繁衍状态
    public boolean getIsRunning()
    {
        return isRunning;
    }

    // 设置是否正在繁衍状态
    public void setIsRunning(boolean isRunning)
    {
        this.isRunning = isRunning;
    }
}

