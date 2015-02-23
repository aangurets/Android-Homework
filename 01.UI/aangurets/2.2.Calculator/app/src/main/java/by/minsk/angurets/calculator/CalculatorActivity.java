package by.minsk.angurets.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    EditText mNum1EditText;
    EditText mNum2EditText;
    TextView mResult;
    RadioGroup mRadioGroup;
    Button mHistoryButton;
    final static String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);

        mNum1EditText = (EditText) findViewById(R.id.num1);
        mNum2EditText = (EditText) findViewById(R.id.num2);
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
                new CalculatorAsyncTask().execute();
            }
        });
    }

    private static double getDouble(TextView textView) {
        CharSequence text = textView.getText();
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

    public void operatorNotSelect() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(CalculatorActivity.this);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        builder.setTitle(R.string.error_operator_not_select).setMessage(R.string.operator_not_select);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    ;

    public void incorrectOperand() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(CalculatorActivity.this);
        builder.setTitle(R.string.error_incorrect_operand)
                .setMessage(R.string.incorrect_operand);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    ;

    public void addToHistoryItemsStorage(char operator, double result) {
        HistoryItemsStorage.add(new HistoryItem(getDouble(mNum1EditText), operator,
                getDouble(mNum2EditText), result));
    }

    public double result(double doubleResult) {
        String mStringResult;
        mStringResult = getString(R.string.result_format, doubleResult);
        mResult.setText(mStringResult);
        return doubleResult;
    }

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

    public class CalculatorAsyncTask extends AsyncTask<Void, Integer, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                switch (mRadioGroup.getCheckedRadioButtonId()) {
                    case View.NO_ID:
                        operatorNotSelect();
                        return null;
                    case R.id.operator_sum:
                        addToHistoryItemsStorage('+', result(new Calculation(getDouble(mNum1EditText),
                                getDouble(mNum2EditText))
                                .sum()));
                        return null;
                    case R.id.operator_subtr:
                        addToHistoryItemsStorage('-', result(new Calculation(getDouble(mNum1EditText),
                                getDouble(mNum2EditText))
                                .subtraction()));
                        return null;
                    case R.id.operator_div:
                        addToHistoryItemsStorage('/', result(new Calculation(getDouble(mNum1EditText),
                                getDouble(mNum2EditText))
                                .division()));
                        return null;
                    case R.id.operator_mult:
                        addToHistoryItemsStorage('*', result(new Calculation(getDouble(mNum1EditText),
                                getDouble(mNum2EditText))
                                .multiplication()));
                        return null;
                    default:
                        operatorNotSelect();
                        return null;
                }
            } catch (IllegalArgumentException e) {
                if (TextUtils.isEmpty(mResult.getText())) {
                    incorrectOperand();
                }
            }
            return null;
        }
    }
}

