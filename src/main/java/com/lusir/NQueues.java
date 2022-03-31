package com.lusir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueues {
    // idx 记录第几行，value记录第几列被放置了
    private int[] columns;
    private int N;
    private List<List<String>> res;


    public List<List<String>> solveNQueens(int n) {
        columns = new int[n];
        N = n;
        res = new ArrayList<>();
        // 这个for循环居然是错误的 - 因为backTrack是从本row开始遍历的
        // for (int row = 0; row < n; row++) {
        //     backTrack(row);
        // }
        backTrack(0);
        return res;
    }

    private void backTrack(int row) {
        if (row == N) {
            generaBoard();
            return;
        }

        for (int column = 0; column < N; column++) {
            this.columns[row] = column;
            if (canPlace(row)) {
                backTrack(row + 1);
            }
        }


    }

    private boolean canPlace(int row) {
        for (int i = 0; i < row; i++) {
            // 之前某行是否在，本行对应的列有过放置
            if (columns[i] == columns[row]) {
                return false;
            }
            // 判断斜线
            if (Math.abs(row - i) == Math.abs(columns[row] - columns[i])) {
                return false;
            }
        }
        return true;
    }

    private void generaBoard() {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            char[] rowBoard = new char[N];
            Arrays.fill(rowBoard, '.');
            rowBoard[columns[i]] = 'Q';
            board.add(new String(rowBoard));
        }
        res.add(board);
    }
}
