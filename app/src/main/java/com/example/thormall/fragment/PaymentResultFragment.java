package com.example.thormall.fragment;

import static com.example.thormall.fragment.InformationOrderFragment.ORDER_KEY;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.thormall.R;
import com.example.thormall.activity.HomeActivity;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.model.Order;
import com.example.thormall.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PaymentResultFragment extends Fragment {

    private ImageView imageView;
    private TextView textView;
    private Button btnContinueShopping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_result, container, false);

        initViews(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (jsonOrder != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);

                if (order.isSuccess()) {
                    textView.setText("Payment was successful\nYour Order will arrive in 3 days");
                    setNotification();
                    Utils.clearCartItem(getActivity());
                    for (GroceryItem item : order.getItems()) {
                        Utils.increasePopularityPoint(getActivity(), item, 1);
                        Utils.changeUserPoint(getActivity(), item, 4);
                    }
                } else {
                    textView.setText("Payment failed,\nPlease try another payment method");
                }

            } else {
                textView.setText("Payment failed,\nPlease try another payment method");
            }
        }

        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initViews(View view) {
        imageView = view.findViewById(R.id.image);
        textView = view.findViewById(R.id.txtMessage);
        btnContinueShopping = view.findViewById(R.id.btnHome);
    }

    private void setNotification() {

        Bitmap thor = BitmapFactory.decodeResource(getResources(), R.mipmap.thor);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Success Payment")
                .setContentText("Thank you for buying")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(thor)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .setBigContentTitle("Success Payment")
                        .bigPicture(thor)
                        .bigLargeIcon(null));

        NotificationManagerCompat manager = NotificationManagerCompat.from(getActivity());
        manager.notify(1, builder.build());
    }
}
