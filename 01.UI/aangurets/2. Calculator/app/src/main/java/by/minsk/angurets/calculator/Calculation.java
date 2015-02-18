package by.minsk.angurets.calculator;

public class Calculation {
    public double mOperand1;
    public double mOperand2;

    public Calculation(double operand1, double operand2) {
        this.mOperand1 = operand1;
        this.mOperand2 = operand2;
    }

    public double sum() {
        return mOperand1 + mOperand2;
    }

    public double subtraction() {
        return mOperand1 - mOperand2;
    }

    public double division() {
        if (mOperand2 == 0) {
            throw new IllegalArgumentException();
        } else return mOperand1 / mOperand2;
    }

    public double multiplication() {
        return mOperand1 * mOperand2;
    }
}
