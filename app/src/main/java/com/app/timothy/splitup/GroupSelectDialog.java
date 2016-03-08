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
public class GroupSelectDialog extends DialogFragment
{
    ArrayList groups;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        String[] groupNames = new String[groups.size()];

        int i = 0;

        for(Object o: groups)
        {
            Group g = (Group)o;
            groupNames[i] = g.getGroupName();
            i++;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Group")
                .setItems(groupNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onListSelection((Group)groups.get(which));
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    public interface GroupSelectDialogListener
    {
        public void onListSelection(Group g);
    }
    GroupSelectDialogListener listener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GroupSelectDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
