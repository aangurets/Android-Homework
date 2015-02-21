package by.minsk.angurets.calculator;

public class HistoryItem {
    public double mOperand1;
    public double mOperand2;
    public char mOperator;
    public double mResult;

    public HistoryItem(double operand1, char operator, double operand2, double result) {
        mOperand1 = operand1;
        mOperator = operator;
        mOperand2 = operand2;
        mResult = result;
    }

    @Override
    public String toString() {
        return "Operation: " + mOperand1 + " " +
                mOperator + " " +
                mOperand2 +
                " = " + mResult;
    }
}
