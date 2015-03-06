package by.minsk.angurets.calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class OperatorNotSelectFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";

    public static OperatorNotSelectFragment newInstance(CharSequence message) {
        Bundle args = new Bundle(1);
        args.putCharSequence(ARG_MESSAGE, message);

        OperatorNotSelectFragment fragment = new OperatorNotSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        builder.setTitle(R.string.error_operator_not_select)
                .setMessage(getArguments().getCharSequence(ARG_MESSAGE));
        return builder.create();
    }
}
