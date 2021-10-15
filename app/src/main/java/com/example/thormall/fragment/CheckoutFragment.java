package com.example.thormall.fragment;

import static com.example.thormall.fragment.InformationOrderFragment.ORDER_KEY;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thormall.R;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.model.Order;
import com.example.thormall.util.OrderEndPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutFragment extends Fragment {

    private TextView txtPhoneNumber, txtItemsName, txtAddress, txtPrice;

    private RadioGroup rgPayment;
    private RadioButton rbPaypal, rbVisa;

    private Button btnBack, btnCheckout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (jsonOrder != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (order != null) {
                    String items = "";
                    for (GroceryItem i : order.getItems()) {
                        items += "\n\t" + i.getName();
                    }

                    txtItemsName.setText(items);
                    txtPrice.setText(String.valueOf(order.getTotalPrice()) + "$");
                    txtAddress.setText(order.getAddress());
                    txtPhoneNumber.setText(order.getPhoneNumber());

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY, jsonOrder);
                            InformationOrderFragment informationOrderFragment = new InformationOrderFragment();
                            informationOrderFragment.setArguments(backBundle);

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, informationOrderFragment);
                            transaction.commit();
                        }
                    });

                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (rgPayment.getCheckedRadioButtonId()) {
                                case R.id.rbPaypal:
                                    order.setPaymentMethod("Paypal");
                                    break;
                                case R.id.rbVisaCard:
                                    order.setPaymentMethod("Visa Card");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknow");
                                    break;
                            }
                            order.setSuccess(true);

                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                            Call<Order> call = endPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    if (response.isSuccessful()) {
                                        Bundle resultBundle = new Bundle();
                                        resultBundle.putString(ORDER_KEY, gson.toJson(response.body()));

                                        PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
                                        paymentResultFragment.setArguments(resultBundle);

                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, paymentResultFragment);
                                        transaction.commit();

                                    } else {
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, new PaymentResultFragment());
                                        transaction.commit();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });


                        }
                    });
                }
            }
        }

        return view;
    }




    private void initViews(View view) {
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtItemsName = view.findViewById(R.id.txtItemsName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPrice = view.findViewById(R.id.txtPrice);

        rbPaypal = view.findViewById(R.id.rbPaypal);
        rbVisa = view.findViewById(R.id.rbVisaCard);
        rgPayment = view.findViewById(R.id.rgPayment);

        btnBack = view.findViewById(R.id.btnBack);
        btnCheckout = view.findViewById(R.id.btnCheckout);

    }
}
