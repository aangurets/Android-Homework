package by.minsk.angurets.calculator;

public enum Calculation implements Compute {


    SUM(new Compute() {
        @Override
        public double compute(double operand1, double operand2) {
            addToHistoryItemsStorage(operand1, '+', operand2, operand1 + operand2);
            return operand1 + operand2;
        }
    }),

    SUBTRACTION(new Compute() {
        @Override
        public double compute(double operand1, double operand2) {
            addToHistoryItemsStorage(operand1, '-', operand2, operand1 - operand2);
            return operand1 - operand2;
        }
    }),

    MULTIPLICATION(new Compute() {
        @Override
        public double compute(double operand1, double operand2) {
            addToHistoryItemsStorage(operand1, '*', operand2, operand1 * operand2);
            return operand1 * operand2;
        }
    }),

    DIVISION(new Compute() {
        @Override
        public double compute(double operand1, double operand2) {
            if (operand2 != 0) {
                addToHistoryItemsStorage(operand1, '/', operand2, operand1 / operand2);
                return operand1 / operand2;
            } else {
                throw new IllegalArgumentException("Operator is incorrect");
            }
        }
    });

    private final Compute mCompute;

    Calculation(Compute compute) {
        mCompute = compute;
    }

    @Override
    public double compute(double operand1, double operand2) {
        return mCompute.compute(operand1, operand2);
    }

    public static void addToHistoryItemsStorage(double operand1, char operator, double operand2, double result) {
        HistoryItemsStorage.add(new HistoryItem(operand1, operator,
                operand2, result));
    }
//    public Calculation(double operand1, double operand2) {
//        this.mOperand1 = operand1;
//        this.mOperand2 = operand2;
//    }
//
//    public double sum() {
//        return mOperand1 + mOperand2;
//    }
//
//    public double subtraction() {
//        return mOperand1 - mOperand2;
//    }
//
//    public double division() {
//        if (mOperand2 == 0) {
//            throw new IllegalArgumentException();
//        } else return mOperand1 / mOperand2;
//    }
//
//    public double multiplication() {
//        return mOperand1 * mOperand2;
//    }
}
