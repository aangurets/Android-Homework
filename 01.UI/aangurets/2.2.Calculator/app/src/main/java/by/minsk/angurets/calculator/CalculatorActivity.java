package by.minsk.angurets.calculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends ActionBarActivity {

    EditText mOperand1EditText;
    EditText mOperand2EditText;
    TextView mResult;
    RadioGroup mRadioGroup;
    Button mHistoryButton;
    final static String RESULT = "setResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);

        mOperand1EditText = (EditText) findViewById(R.id.operand1);
        mOperand2EditText = (EditText) findViewById(R.id.operand2);
        mResult = (TextView) findViewById(R.id.result);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mHistoryButton = (Button) findViewById(R.id.history_button);
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.compute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compute operator = getOperator();
                if (operator == null) {
                    Toast.makeText(CalculatorActivity.this, R.string.incorrect_operator, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    new CalculatorAsyncTask(mOperand1EditText.getText(), mOperand2EditText.getText())
                            .execute(operator);
                }
            }
        });
    }

    private Compute getOperator() {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case View.NO_ID:
                operatorNotSelect();
                return null;
            case R.id.operator_sum:
                return Calculation.SUM;
            case R.id.operator_subtr:
                return Calculation.SUBTRACTION;
            case R.id.operator_div:
                return Calculation.DIVISION;
            case R.id.operator_mult:
                return Calculation.MULTIPLICATION;
            default:
                operatorNotSelect();
                return null;
        }
    }

    public void setResult(double doubleResult) {
        mResult.setText(getString(R.string.result_format, doubleResult));
    }

    public void operatorNotSelect() {
        OperatorNotSelectFragment.newInstance("Operator is not selected")
                .show(getSupportFragmentManager(), "OPER_NOT_SELECT");
    }

    ;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(RESULT, mResult.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mResult.setText(savedInstanceState.getCharSequence(RESULT));
    }

    private class CalculatorAsyncTask extends AsyncTask<Compute, Float, Double> {
        private final CharSequence mOperand1;
        private final CharSequence mOperand2;

        private CalculatorAsyncTask(CharSequence operand1, CharSequence operand2) {
            mOperand1 = operand1;
            mOperand2 = operand2;
        }

        private double getDouble(CharSequence text) {
            if (TextUtils.isEmpty(text)) {
                throw new IllegalArgumentException();
            } else {
                try {
                    return Double.parseDouble(text.toString());
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }
        }

        @Override
        protected Double doInBackground(Compute... params) {
            try {
                final double result =
                        params[0].compute(getDouble(mOperand1), getDouble(mOperand2));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Double result) {
            if (result != null) {
                setResult(result);
            } else {
                if (!TextUtils.isEmpty(mResult.getText())) {
                }
                Toast.makeText(
                        CalculatorActivity.this, R.string.incorrect_operand, Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}



