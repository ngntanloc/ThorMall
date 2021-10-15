package com.example.thormall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thormall.R;
import com.example.thormall.model.CartItem;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.model.Order;
import com.example.thormall.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InformationOrderFragment extends Fragment {

    public static final String ORDER_KEY = "order";

    private EditText edtTxtAddress, edtTxtZipCode, edtTxtPhoneNumber, edtTxtEmail;
    private Button btnNext, btnBack;
    private TextView txtWarning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_order, container, false);

        initView(view);

        // receive order information if thirdFragment back
        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (jsonOrder != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (order != null) {
                    edtTxtAddress.setText(order.getAddress());
                    edtTxtPhoneNumber.setText(order.getPhoneNumber());
                    edtTxtZipCode.setText(order.getZipCode());
                    edtTxtEmail.setText(order.getEmail());
                }
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new ListCartFragment());
                transaction.commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateData()) {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtWarning.setText("Please Fill All The Blanks");
                } else {
                    txtWarning.setVisibility(View.GONE);
                    ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());
                    if (cartItems != null) {
                        Order order = new Order();
                        order.setItems(cartItems);
                        order.setAddress(edtTxtAddress.getText().toString());
                        order.setPhoneNumber(edtTxtPhoneNumber.getText().toString());
                        order.setZipCode(edtTxtZipCode.getText().toString());
                        order.setEmail(edtTxtEmail.getText().toString());
                        order.setTotalPrice(calculateTotalPrice(cartItems));

                        // give order information to thirdFragment (accept info and go to cash)
                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(order);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY, jsonOrder);

                        CheckoutFragment checkoutFragment = new CheckoutFragment();
                        checkoutFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, checkoutFragment);
                        transaction.commit();

                    }
                }
            }
        });

        return view;
    }

    private double calculateTotalPrice(ArrayList<GroceryItem> items) {
        double total = 0;
        for (GroceryItem g : items) {
            total += g.getPrice();
        }
        return total;
    }

    private boolean validateData() {
        if (edtTxtEmail.getText().toString().equals("") || edtTxtZipCode.getText().toString().equals("") || edtTxtAddress.getText().toString().equals("") || edtTxtPhoneNumber.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initView(View view) {
        edtTxtAddress = view.findViewById(R.id.edtTxtAddress);
        edtTxtEmail = view.findViewById(R.id.edtTxtEmail);
        edtTxtZipCode = view.findViewById(R.id.edtTxtZipcode);
        edtTxtPhoneNumber = view.findViewById(R.id.edtTxtPhoneNumber);
        btnNext = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
        txtWarning = view.findViewById(R.id.txtWarning);
    }

}
