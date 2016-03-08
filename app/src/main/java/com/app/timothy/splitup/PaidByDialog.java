package com.app.timothy.splitup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Timothy on 3/6/2016.
 */
public class PaidByDialog extends DialogFragment
{
    ArrayList<Person> members ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] names = new String[members.size()];

        for (int i = 0; i < members.size(); i++)
        {
            names[i] = members.get(i).getName();
        }
        builder.setTitle("Who paid?")
                .setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onListSelection(members.get(which));
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    public interface PaidByDialogListener
    {
        public void onListSelection(Person p);
    }
    PaidByDialogListener listener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            listener = (PaidByDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
