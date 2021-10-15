package com.example.thormall.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.thormall.R;

public class AddQuantityCartItem extends DialogFragment {

    public interface AddQuantity {
        void ResultAddQuantity(int quantity);
    }

    private AddQuantity addQuantity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_quantity_cart_item, null);

        EditText edtQuantity = view.findViewById(R.id.edtQuantity);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        TextView txtWarning = view.findViewById(R.id.txtWarning);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Input quantity that you want to buy")
                .setView(view);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = edtQuantity.getText().toString();
                if (quantity == null || quantity.isEmpty()) {
                    txtWarning.setText("Please input quantity items you want to buy");
                    txtWarning.setVisibility(View.VISIBLE);
                } else {
                    txtWarning.setVisibility(View.GONE);
                    try {
                        int quantityItem = Integer.parseInt(quantity);
                        addQuantity = (AddQuantity) getActivity();
                        addQuantity.ResultAddQuantity(quantityItem);
                        dismiss();
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return builder.create();
    }
}
