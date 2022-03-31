package com.lusir;

import java.util.ArrayList;
import java.util.List;

public class SudoKu {
    // 记录某行某个数是否被记录了
    private boolean[][] row = new boolean[9][9];
    // 记录某列某个数是否被记录了
    private boolean[][] column = new boolean[9][9];
    // 记录某块某数是否被记录了
    private boolean[][][] block = new boolean[3][3][9];
    // 记录空闲位置的坐标
    private List<int[]> spacesPos = new ArrayList<>();
    // 记录某次成功的终止标记
    private boolean valid = false;

    // 解数独
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spacesPos.add(new int[]{i, j});
                } else {
                    int digest = board[i][j] - '0' - 1;
                    row[i][digest] = column[j][digest] = block[i / 3][j / 3][digest] = true;
                }
            }
        }
        dfs(board, 0);
    }


    private void dfs(char[][] board, int n) {
        if (spacesPos.size() == n) {
            valid = true;
            return;
        }

        int[] spacePos = spacesPos.get(n);
        int x = spacePos[0], y = spacePos[1];
        for (int digest = 0; digest < 9; digest++) {
            if (!row[x][digest] && !column[y][digest] && !block[x / 3][y / 3][digest] && !valid) {

                row[x][digest] = column[y][digest] = block[x / 3][y / 3][digest] = true;

                board[x][y] = (char) (digest + '0' + 1);

                dfs(board, n + 1);

                row[x][digest] = column[y][digest] = block[x / 3][y / 3][digest] = false;
            }
        }
    }


    // 验证数独表是否合理
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spacesPos.add(new int[]{i, j});
                } else {
                    int digest = board[i][j] - '0' - 1;
                    row[i][digest] = column[j][digest] = block[i / 3][j / 3][digest] = true;
                }
            }
        }
        dfs(0);
        return valid;
    }

    private void dfs(int n) {
        if (n == spacesPos.size()) {
            valid = true;
            return;
        }

        int[] spacePos = spacesPos.get(n);
        int x = spacePos[0], y = spacePos[1];
        for (int digest = 0; digest < 9; digest++) {
            if (!row[x][digest] && !column[y][digest] && !block[x / 3][y / 3][digest] && !valid) {
                row[x][digest] = column[y][digest] = block[x / 3][y / 3][digest] = true;
                dfs(n + 1);
                row[x][digest] = column[y][digest] = block[x / 3][y / 3][digest] = false;
            }
        }
    }
}
