package model;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import view.GridType;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19


        initSets();
        initGrid();
        initPieces();
    }

    private void initSets() {
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));

        densCell.add(new ChessboardPoint(0, 3));
        densCell.add(new ChessboardPoint(8, 3));
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (riverCell.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell(GridType.RIVER);
                } else if (trapCell.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell(GridType.TRAP);
                    if (i < 2) {
                        grid[i][j].setOwner(PlayerColor.RED);
                    } else {
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                } else if (densCell.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell(GridType.DENS);
                    if (i < 2) {
                        grid[i][j].setOwner(PlayerColor.RED);
                    } else {
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                } else {
                    grid[i][j] = new Cell(GridType.LAND);
                }
            }
        }
    }

    public void initPieces() {
        // 清空棋盘
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));

        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
        else {
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        //老鼠可以走所有地方
        if (getChessPieceAt(src).getName() == "Rat") {
            return calculateDistance(src, dest) == 1;
        }
        //对于狮子和老虎，如果dest是河流，不行。不是河流要考虑跨河流的问题，跨河流的时候考虑row和col和中间是否有river,是否有棋子（老鼠是否在jump的路线里面）
        if (getChessPieceAt(src).getName() == "Lion" || getChessPieceAt(src).getName() == "Tiger") {
            if (getGridAt(dest).getType() != GridType.RIVER) {
                if (calculateDistance(src, dest) == 1) {
                    return true;
                }
                if (calculateDistance(src, dest) > 1) {
                    if (src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) {
                        return false;
                    }
                    if (src.getCol() == dest.getCol() && src.getRow() != dest.getRow()) {
                        int step = src.getRow() < dest.getRow() ? 1 : -1;
                        int row = src.getRow() + step;
                        while (row != dest.getRow()) {
                            if (getGridAt(new ChessboardPoint(row, src.getCol())).getType() != GridType.RIVER) {
                                return false;
                            }
                            if (getGridAt(new ChessboardPoint(row, src.getCol())) == null) {
                                return false;
                            }
                            row += step;
                        }
                        return true;
                    }
                    if (src.getRow() == dest.getRow() && src.getCol() != dest.getCol()) {
                        int step = src.getCol() < dest.getCol() ? 1 : -1;
                        int col = src.getCol() + step;
                        while (col != dest.getCol()) {
                            if (getGridAt(new ChessboardPoint(src.getRow(), col)).getType() != GridType.RIVER) {
                                return false;
                            }
                            if (getGridAt(new ChessboardPoint(src.getRow(), col)) == null) {
                                return false;
                            }
                            col += step;
                        }
                        return true;
                    }
                }

            }
        }
        //对于除了老鼠和狮子和老虎之外的所有动物除了河流之外都能走
        if (getChessPieceAt(src).getName() != "Lion" && getChessPieceAt(src).getName() != "Tiger" && getChessPieceAt(src).getName() != "Rat") {
            if (getGridAt(dest).getType() == GridType.RIVER) {
                return false;
            }
            if (getGridAt(dest).getType() != GridType.RIVER) {
                return calculateDistance(src, dest) == 1;
            }
        }
        //不能进自己的巢穴

        return false;

    }

    public boolean decidedens(ChessboardPoint point, ChessboardPoint selectedpoint) {
        if (getGridAt(point).getType() == GridType.DENS && !getGridAt(point).getOwner().equals(getGridAt(selectedpoint).getOwner())) {
            return true;
        } else return false;
    }

    public void decidetraps(ChessboardPoint point, ChessboardPoint selectedpoint) {
        if (getGridAt(point).getType() == GridType.TRAP && !getGridAt(point).getOwner().equals(getGridAt(selectedpoint).getOwner())) {
            intraps(selectedpoint);
        } else if (getGridAt(selectedpoint).getType().equals(GridType.TRAP) && getGridAt(point).getType() != GridType.TRAP && !getGridAt(point).getOwner().equals(getGridAt(selectedpoint).getOwner())) {
            exittraps(selectedpoint);
        }
    }

    public void intraps(ChessboardPoint point) {
        getGridAt(point).getPiece().setRank(0);
    }
    public void exittraps(ChessboardPoint point){
        if(getChessPieceAt(point).getName().equals("Elephant")){
            getGridAt(point).getPiece().setRank(8);
        }
        if(getChessPieceAt(point).getName().equals("Lion")){
            getGridAt(point).getPiece().setRank(7);
        }
        if(getChessPieceAt(point).getName().equals("Tiger")){
            getGridAt(point).getPiece().setRank(6);
        }
        if(getChessPieceAt(point).getName().equals("Rat")){
            getGridAt(point).getPiece().setRank(2);
        }
        if(getChessPieceAt(point).getName().equals("Cat")){
            getGridAt(point).getPiece().setRank(1);
        }
        if(getChessPieceAt(point).getName().equals("Dog")){
            getGridAt(point).getPiece().setRank(3);
        }
        if(getChessPieceAt(point).getName().equals("Leopard")){
            getGridAt(point).getPiece().setRank(5);
        }
        if(getChessPieceAt(point).getName().equals("Wolf")){
            getGridAt(point).getPiece().setRank(4);
        }
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (srcPiece == null || destPiece == null) {
            return false;
        }
        if (srcPiece.getOwner() == destPiece.getOwner()) {
            return false;
        }
        // 在河里的老鼠不能被捕获
        if (getGridAt(dest).getType() == GridType.RIVER) {
            return false;
        }
        // 在河里的老鼠不能捕获大象
        if (getGridAt(src).getType() == GridType.RIVER) {
            return false;
        }
        // 狮子和虎可以跳过河来捕获
        if (calculateDistance(src, dest) > 1
                && (srcPiece.getName() == "Lion" || srcPiece.getName() == "Tiger")) {
            // 检查两个格子是否在同一行或者同一列
            if (src.getRow() != dest.getRow() && src.getCol() != dest.getCol()) {
                return false;
            }
            // 检查两个格子之间是否全为RIVER，如果是，则可以移动，否则不可以移动
            if (src.getRow() == dest.getRow()) {
                int step = src.getCol() < dest.getCol() ? 1 : -1;
                int col = src.getCol() + step;
                while (col != dest.getCol()) {
                    if (getGridAt(new ChessboardPoint(src.getRow(), col)).getType() != GridType.RIVER) {
                        return false;
                    }
                    // 检查河流格子上是否有棋子
                    if (getChessPieceAt(new ChessboardPoint(src.getRow(), col)) != null) {
                        return false;
                    }
                    col += step;
                }
                return srcPiece.canCapture(destPiece);
            }
            if (src.getCol() == dest.getCol()) {
                int step = src.getRow() < dest.getRow() ? 1 : -1;
                int row = src.getRow() + step;
                while (row != dest.getRow()) {
                    if (getGridAt(new ChessboardPoint(row, src.getCol())).getType() != GridType.RIVER) {
                        return false;
                    }
                    // 检查河流格子上是否有棋子
                    if (getChessPieceAt(new ChessboardPoint(row, src.getCol())) != null) {
                        return false;
                    }
                    row += step;
                }
                return srcPiece.canCapture(destPiece);
            }
        }
        return calculateDistance(src, dest) == 1 && srcPiece.canCapture(destPiece);
    }
}
