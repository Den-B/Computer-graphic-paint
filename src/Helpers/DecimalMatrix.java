package Helpers;

class DecimalMatrix{

    private double[][] values;
    private int numberOfRows;
    private int numberOfColumns;

    public DecimalMatrix(int numberOfRows, int numberOfColumns){
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        values = new double[numberOfRows][numberOfColumns];
    }

    public DecimalMatrix(int numberOfRows, int numberOfColumns, double[] initializationArray) throws MatrixException {
        if(numberOfRows*numberOfColumns != initializationArray.length) throw new MatrixException("Wrong initialization array.");
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        values = new double[numberOfRows][numberOfColumns];
        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
                this.setValue(row,column,initializationArray[column + row * numberOfColumns]);
            }
        }

    }

    public DecimalMatrix(double[][] values) throws MatrixException {
        if(values.length == 0) throw new MatrixException("Wrong number of rows.");
        this.numberOfRows = values.length;
        if(values[0].length == 0) throw new MatrixException("Wrong number of columns.");
        numberOfColumns = values[0].length;
        for(double[] array: values){
            if(array.length != numberOfColumns) throw new MatrixException("Wrong input data for matrix initialization.");
        }
        this.values = values;
    }

    public double getValue(int row, int column){
        return values[row][column];
    }
    public double getVectorValue(int index) throws MatrixException {
        if(this.getNumberOfColumns() != 1 && this.getNumberOfRows() != 1) throw new MatrixException("This is not vector");
        if(this.getNumberOfRows() == 1){
            return getValue(0,index);
        }
        else return getValue(index,0);
    }

    public void setVectorValue(int index, double value) throws MatrixException {
        if(this.getNumberOfColumns() != 1 && this.getNumberOfRows() != 1) throw new MatrixException("This is not vector");
        if(this.getNumberOfRows() == 1){
            values[0][index] = value;
        }
        else values[index][0] = value;

    }


    public void setValue(int row, int column, double value){
        values[row][column] = value;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public DecimalMatrix multiplication(DecimalMatrix secondMatrix) throws MatrixException {
        if(this.getNumberOfColumns() != secondMatrix.getNumberOfRows()) throw new MatrixException("It's impossible to multiply these two matrix");
        DecimalMatrix resultMatrix = new DecimalMatrix(this.getNumberOfRows(),secondMatrix.getNumberOfColumns());
        for(int row = 0; row < resultMatrix.getNumberOfRows(); row++){
            for(int column = 0; column < resultMatrix.getNumberOfColumns(); column++) {
                double sum = 0;
                for (int offset = 0; offset < this.getNumberOfColumns(); offset++) {
                    sum += this.getValue(row, offset) * secondMatrix.getValue(offset, column);
                }
                resultMatrix.setValue(row, column, sum);
            }
        }
        return resultMatrix;
    }

}

class MatrixException extends Exception{

    public MatrixException(String reason){
        super(reason);
    }

}