package ru.myproject.flowershop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    String[] addresses = {"kordyakim@gmail.com"};
    String subject = "order from flower shop";
    String emailText;
    AlertDialog.Builder dialog_main;
    AlertDialog dialog_package_list;
    ArrayAdapter<String> adapter_package;
    ArrayList<String> list_package;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        dialog_main = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        list_package = new ArrayList<>();
        Intent receivedOrderIntent = getIntent();
        String userName = receivedOrderIntent.getStringExtra("userNameForIntent");
        String goodsName = receivedOrderIntent.getStringExtra("goodsName");
        int quantity = receivedOrderIntent.getIntExtra("quantity",0);
        double orderPrice = receivedOrderIntent.getDoubleExtra("orderPrice",0);
        double price = receivedOrderIntent.getDoubleExtra("price",0);
        TextView orderTextView = findViewById(R.id.orderTextView);
        emailText = "Customer name: "+userName+"\n"+"Goods name: "+goodsName+"\n"+"Quantity: "+quantity+"\n"+"Price: "+price+"\n"+"Order price: "+orderPrice;
        orderTextView.setText(emailText);
    }

    @SuppressLint("ResourceAsColor")
    public void submitOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
            .setData(Uri.parse("mailto:"))
//                            .setType("*/*")
            .putExtra(Intent.EXTRA_EMAIL, addresses)
            .putExtra(Intent.EXTRA_SUBJECT, subject)
            .putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
        list_package.clear();
        Collections.addAll(list_package,intent.resolveActivity(getPackageManager()).getPackageName());
        adapter_package = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, list_package);
        dialog_main.setAdapter(adapter_package, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        });
        dialog_main.setNegativeButton(R.string.cancel, myClickListener);
        dialog_package_list = dialog_main.create();
        dialog_package_list.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog_package_list.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
            }
        });
        dialog_package_list.show();
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == Dialog.BUTTON_NEGATIVE) {
                dialog.cancel();
            }
        }
    };
}