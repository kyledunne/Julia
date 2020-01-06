package gui.layoutManagers;

import gui.*;

/**
 * Created by Kyle on 4/20/2016.
 */
public class GridLayout extends LayoutManager {
    protected Rectangle[][] components;
    protected int rows, columns;
    protected float leftBorder = 0, rightBorder = 0, topBorder = 0, bottomBorder = 0;
    protected float horizontalGap = 0, verticalGap = 0;
    protected float cellWidth, cellHeight;
    protected boolean layoutFinalized = false;
    protected boolean usesCellAspectRatio = false;
    protected float cellAspectRatio;

    private GridLayout() {}

    public static GridLayout new1(int rows, int columns, float leftBorder, float rightBorder,
                                  float topBorder, float bottomBorder, float horizontalGap, float verticalGap) {
        GridLayout g = new GridLayout();
        g.components = new Rectangle[rows][columns];
        g.rows = rows;
        g.columns = columns;
        g.leftBorder = leftBorder;
        g.rightBorder = rightBorder;
        g.topBorder = topBorder;
        g.bottomBorder = bottomBorder;
        g.horizontalGap = horizontalGap;
        g.verticalGap = verticalGap;
        return g;
    }

    public static GridLayout new2(int rows, int columns, float leftBorder, float rightBorder,
                                  float topBorder, float bottomBorder, float cellWidth, float cellHeight) {
        GridLayout g = new GridLayout();
        g.components = new Rectangle[rows][columns];
        g.rows = rows;
        g.columns = columns;
        g.leftBorder = leftBorder;
        g.rightBorder = rightBorder;
        g.topBorder = topBorder;
        g.bottomBorder = bottomBorder;
        g.cellWidth = cellWidth;
        g.cellHeight = cellHeight;
        return g;
    }

    public static GridLayout new3(int rows, int columns, float leftBorder, float rightBorder,
                                  float topBorder, float horizontalGap, float verticalGap, float cellAspectRatio) {
        GridLayout g = new GridLayout();
        g.components = new Rectangle[rows][columns];
        g.rows = rows;
        g.columns = columns;
        g.leftBorder = leftBorder;
        g.rightBorder = rightBorder;
        g.topBorder = topBorder;
        g.horizontalGap = horizontalGap;
        g.verticalGap = verticalGap;
        g.usesCellAspectRatio = true;
        g.cellAspectRatio = cellAspectRatio;
        return g;
    }

    public static GridLayout new4(int rows, int columns, float leftBorder, float topBorder,
                                  float horizontalGap, float verticalGap, float cellWidth, float cellAspectRatio) {
        GridLayout g = new GridLayout();
        g.components = new Rectangle[rows][columns];
        g.rows = rows;
        g.columns = columns;
        g.leftBorder = leftBorder;
        g.topBorder = topBorder;
        g.horizontalGap = horizontalGap;
        g.verticalGap = verticalGap;
        g.cellWidth = cellWidth;
        g.usesCellAspectRatio = true;
        g.cellAspectRatio = cellAspectRatio;
        return g;
    }

    /** call after the client's parent has been set (and the coordinates of the parent have been set) */
    public void finalize1() {
        layoutFinalized = true;
        cellWidth = getClient().getWidth();
        cellWidth = cellWidth - rightBorder - leftBorder;
        float emptySpace = Math.max(0, columns - 1) * horizontalGap;
        cellWidth = cellWidth - emptySpace;
        cellWidth = cellWidth / columns;

        cellHeight = getClient().getHeight();
        cellHeight = cellHeight - bottomBorder - topBorder;
        emptySpace = Math.max(0, rows - 1) * verticalGap;
        cellHeight = cellHeight - emptySpace;
        cellHeight = cellHeight / rows;
    }

    /** call after the client's parent has been set (and the coordinates of the parent have been set) */
    public void finalize2() {
        layoutFinalized = true;
        float emptyHorizontalSpace = getClient().getWidth() - leftBorder - rightBorder - (cellWidth * columns);
        horizontalGap = emptyHorizontalSpace / Math.max(1, columns - 1);
        float emptyVerticalSpace = getClient().getHeight() - topBorder - bottomBorder - (cellHeight * rows);
        verticalGap = emptyVerticalSpace / Math.max(1, rows - 1);
    }

    /** call after the client's parent has been set (and the coordinates of the parent have been set) */
    public void finalize3() {
        layoutFinalized = true;
        cellWidth = getClient().getWidth();
        cellWidth = cellWidth - rightBorder - leftBorder;
        float emptySpace = Math.max(0, columns - 1) * horizontalGap;
        cellWidth = cellWidth - emptySpace;
        cellWidth = cellWidth / columns;
        cellHeight = cellWidth * cellAspectRatio;
        //set bottomBorder if needed
    }

    /** call after the client's parent has been set (and the coordinates of the parent have been set) */
    public void finalize4() {
        layoutFinalized = true;
        cellHeight = cellWidth * cellAspectRatio;
        //set rightBorder and bottomBorder if needed
    }

    @Override
    public void setClient(Container client) {
        super.setClient(client);
    }

    @Override
    public void add(Rectangle component, LayoutConstraints constraints) {
        if (!layoutFinalized) {
            throw new RuntimeException("this instance of GridLayout has not been finalized");
        }
        Constraints c = (Constraints) constraints;
        components[c.getRow()][c.getColumn()] = component;
        float x = leftBorder + (cellWidth * c.getColumn()) + (horizontalGap * c.getColumn());
        float y = topBorder + (cellHeight * c.getRow()) + (verticalGap * c.getRow());
        component.setX(x);
        component.setY(y);
        component.setWidth(cellWidth);
        component.setHeight(cellHeight);
    }

    @Override
    public void remove(Rectangle component) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (component.equals(components[i][j])) {
                    components[i][j] = null;
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].deactivate();
                    components[i][j].setParent(null);
                    components[i][j] = null;
                }

            }
        }
    }

    @Override
    public void clientWidthChanged() {
        System.out.println("gui.layoutManagers.GridLayout does not yet support width changes");
    }

    @Override
    public void clientHeightChanged() {
        System.out.println("gui.layoutManagers.GridLayout does not yet support height changes");
    }

    @Override
    public void clientXChanged() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].setAbsoluteX(components[i][j].getX()
                            + getClient().getAbsoluteX());
                }
            }
        }
    }

    @Override
    public void clientYChanged() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].setAbsoluteY(components[i][j].getY()
                            + getClient().getAbsoluteY());
                }
            }
        }
    }

    @Override
    public void drawComponents() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].draw();
                }
            }
        }
    }

    @Override
    public void activateComponents() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].activate();
                }
            }
        }
    }

    @Override
    public void deactivateComponents() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (components[i][j] != null) {
                    components[i][j].deactivate();
                }
            }
        }
    }

    public Rectangle getComponentAt(int row, int column) {
        return components[row][column];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public float getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(float topBorder) {
        this.topBorder = topBorder;
    }

    public float getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(float bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public float getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(float rightBorder) {
        this.rightBorder = rightBorder;
    }

    public float getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(float leftBorder) {
        this.leftBorder = leftBorder;
    }

    public float getHorizontalGap() {
        return horizontalGap;
    }

    public void setHorizontalGap(float horizontalGap) {
        this.horizontalGap = horizontalGap;
    }

    public float getVerticalGap() {
        return verticalGap;
    }

    public void setVerticalGap(float verticalGap) {
        this.verticalGap = verticalGap;
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(float cellWidth) {
        this.cellWidth = cellWidth;
    }

    public float getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }

    public static class Constraints implements LayoutConstraints {
        private int row, column;

        public Constraints(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
