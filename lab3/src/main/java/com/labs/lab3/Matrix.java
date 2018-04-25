package com.labs.lab3;

import java.util.Arrays;

public class Matrix {
	
    private double matrix[][];
    private int row;
    private int col;
    
    public Matrix(int row, int col) {
        if (row < 1)
            throw new IllegalArgumentException("Row number is out of range: "
                    + row + " expected range row >= 1");
        if (col < 1)
            throw new IllegalArgumentException("Col number is out of range: "
                    + col + " expected range col >= 1");
        this.row = row;
        this.col = col;
        matrix = new double[row][col];
    }
    
    public Matrix(double[][]array) {
        if (array == null)
            throw new NullPointerException();
        int len = array[0].length;
        if (len == 0)
            throw new RuntimeException("Illegal matrix dimensions.");
        if (len > 1 && array.length > 1)
	        for (int i = 1; i < array.length; i++)
	        	if (array[i].length != len)
	        		  throw new IllegalArgumentException("Jagged Array");
        this.row = array.length;
        this.col = array[0].length;
    
        this.matrix = new double[row][col];
        for (int i = 0; i < this.row; i++)
            System.arraycopy(array[i], 0, this.matrix[i], 0, this.col);
    }
    
    public Matrix transpose() {
        Matrix A = new Matrix(col, row);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                A.matrix[j][i] = this.matrix[i][j];
        return A;
    }
    
    public Matrix sum(Matrix B) {
        Matrix A = this;
        if (B == null)
            throw new NullPointerException();
        if (A.row != B.row || A.col != B.col)
            throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.row, A.col);
        for (int i = 0; i < C.row; i++)
            for (int j = 0; j < C.col; j++)
                C.matrix[i][j] = A.matrix[i][j] + B.matrix[i][j];
        return C;
    }
    
    public Matrix mult(Matrix B) {
        Matrix A = this;
        if (B == null)
            throw new NullPointerException();
        if (B.getMatrixData().length != A.getMatrixData()[0].length)
            throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.row, B.col);
        for (int i = 0; i < A.row; i++)
            for (int j = 0; j < B.col; j++)
                for (int k = 0; k < A.col; k++)
                    C.matrix[i][j] += (A.matrix[i][k] * B.matrix[k][j]);
        return C;
    }
    
    public Matrix mult(double k) {
        Matrix A =  new Matrix(this.matrix);
        for (int i = 0; i < A.row; i++)
            for (int j = 0; j < A.col; j++)
                A.matrix[i][j] *= k;
        return(A);
    }
    
    public void print() {
        System.out.println(this.toString());
    }
    
    public String toString(){
        StringBuilder matrixString = new StringBuilder();
        matrixString.append("[");
        for (int i = 0; i < this.row; i++) {
            matrixString.append("[");
            for (int j = 0; j < this.col; j++) {
                matrixString.append(Math.round(this.matrix[i][j] * 100.0) / 100.0);
                if (j < this.col - 1)
                    matrixString.append(", ");
            }
            matrixString.append("]");
            if (i < this.row - 1)
                matrixString.append(", ");
        }
        matrixString.append("]");
        return matrixString.toString();
    }
    
    public boolean equals(Object obj) {
    	if (obj == this)
    		return true;
    	if (!(obj instanceof Matrix))
    		return false;
        if (this.row != ((Matrix) obj).row || this.col != ((Matrix) obj).col)
            return false;
        double arr[][] = this.getMatrixData();
        double arr2[][] = ((Matrix)obj).getMatrixData();
        for (int i = 0; i < this.row; i++)
        	if (!Arrays.equals(arr[i], arr2[i]))
        		return (false);
        return true;        
    }
    
    public double[][] getMatrixData() {
    	return this.matrix;
    }
    
    public int getRow() {
    	return this.row;
    }
    
    public int getCol(){
    	return this.col;
    }
}
