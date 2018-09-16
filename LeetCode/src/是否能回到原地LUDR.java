import java.util.Arrays;

import org.junit.Test;

public class 是否能回到原地LUDR {
	/*
	 * 
	 * 最初，机器人位于（0,0）处。 给定一系列动作，判断该机器人是否形成一个圆圈，这意味着它会移回原来的位置。

移动序列由字符串表示。 每个动作都由一个角色代表。 有效的机器人移动是R（右），L（左），U（上）和D（下）。 输出应该为true或false，表示机器人是否制作圆圈。
	 * */
	
    public boolean judgeCircle(String moves) {
        //RULD

        //R 0
        //L 0
        //U 0
        //D 0
        int[] position = new int[4];
        for (int i = 0; i < moves.length(); ++i) {
            char ch = moves.charAt(i);
            if (ch == 'R') {
                if (position[1] == 0)
                    ++position[0];
                else
                    --position[1];
            } else if (ch == 'L') {
                if (position[0] == 0)
                    ++position[1];
                else
                    --position[0];
            } else if (ch == 'U') {
                if (position[3] == 0)
                    ++position[2];
                else
                    --position[3];
            } else if (ch == 'D') {
                if (position[2] == 0)
                    ++position[3];
                else
                    --position[2];
            }
        }
        for (int i = 0; i < 4; ++i) {
            if (position[i] != 0)
                return false;
        }
        return true;
    }

    public boolean judgeCircle2(String moves) {
        int leftRight = 0;
        int topDown = 0;
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U':
                    topDown++;
                    break;
                case 'D':
                    topDown--;
                    break;
                case 'L':
                    leftRight--;
                    break;
                case 'R':
                    leftRight++;
            }
        }
        return leftRight == 0 && topDown == 0;
    }

    //上下左右
    @Test
    public void test() {
        System.out.println(judgeCircle("RLD"));
    }
}
