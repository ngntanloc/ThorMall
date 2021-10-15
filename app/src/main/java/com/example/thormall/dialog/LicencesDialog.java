package com.example.thormall.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.thormall.R;
import com.example.thormall.util.Utils;

public class LicencesDialog extends DialogFragment {
    @Nullable
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_licences, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Licences");

        TextView textView = view.findViewById(R.id.txtLicences);
        Button btnDismiss = view.findViewById(R.id.btnDismiss);

        textView.setText(Utils.getLicenses());
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }
}
