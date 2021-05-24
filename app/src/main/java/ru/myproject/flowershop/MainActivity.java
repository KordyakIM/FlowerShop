package ru.myproject.flowershop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    ArrayList arrayListSpinner;
    ArrayAdapter arrayAdapterSpinner;
    HashMap goodsMap;
    String goodsName;
    double price;
    int quantity;
    TextView quantity_count,order_price_money;
    ImageView imageView_flower;
    EditText userNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity_count = findViewById(R.id.quantity_count);
        userNameEditText = findViewById(R.id.editText_input);
        createSpinner();
        createMap();
    }
    void createSpinner(){
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        arrayListSpinner = new ArrayList();
        arrayListSpinner.add("rose");
        arrayListSpinner.add("cactus");
        arrayListSpinner.add("sunflower");
        arrayAdapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayListSpinner);
        arrayAdapterSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpinner);
    }
    void createMap(){
        goodsMap = new HashMap();
        goodsMap.put("rose",100.0);
        goodsMap.put("cactus",150.0);
        goodsMap.put("sunflower",200.0);
    }
    // данный метод выполняется, когда какой-то элемент выбран, пришел метод из implements AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double)goodsMap.get(goodsName);
        order_price_money = findViewById(R.id.order_price_money);
        order_price_money.setText(""+quantity*price);

        imageView_flower = findViewById(R.id.imageView_flower);
        Log.d("My_log","tesyt");
        switch(goodsName){
            case "rose":
                imageView_flower.setImageResource(R.drawable.rose);
                break;
            case "cactus":
                imageView_flower.setImageResource(R.drawable.cactus);
                break;
            case "sunflower":
                imageView_flower.setImageResource(R.drawable.sunflower);
                break;
            default:
                imageView_flower.setImageResource(R.drawable.rose);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void decrease(View view) {
        quantity = quantity - 1;
        if(quantity <0){
            quantity = 0;
        }
        quantity_count.setText(""+quantity);
        onItemSelected(null,null,0,0);
    }

    public void increase(View view) {
        quantity = quantity + 1;
        quantity_count.setText(""+quantity);
        onItemSelected(null,null,0,0);
    }

    public void addToCart(View view) {
        Order order = new Order();
        order.userName = userNameEditText.getText().toString();
        order.goodsName = goodsName;
        order.quantity = quantity;
        order.orderPrice = quantity * price;
        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameForIntent",order.userName);
        orderIntent.putExtra("goodsName",order.goodsName);
        orderIntent.putExtra("quantity",order.quantity);
        orderIntent.putExtra("price",price);
        orderIntent.putExtra("orderPrice",order.orderPrice);

        startActivity(orderIntent);

    }
}