package com.udemy.android.rouxacademy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        int cost = 5,discount;
        if(addWhippedCream==true) cost +=1;
        if(addChocolate==true) cost +=2;
        int price=cost*quantity;
        if(quantity>10 && quantity<=20) price*=.95;
        else if(quantity>20) price*=.90;
        return price;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox WhippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCreamChecked= WhippedCreamCheckBox.isChecked();
        CheckBox ChocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateChecked= ChocolateCheckBox.isChecked();
        EditText name= (EditText)findViewById(R.id.customer_name);
        String name_customer = name.getText().toString();
        EditText email= (EditText)findViewById(R.id.customer_email);
        String email_customer = email.getText().toString();
        EditText phone= (EditText)findViewById(R.id.customer_phone);
        String phone_customer = phone.getText().toString();
        int price=calculatePrice(hasWhippedCreamChecked,hasChocolateChecked);
        display(quantity);
        String priceMessage=createOrderSummary(name_customer,email_customer,phone_customer,price,hasWhippedCreamChecked,hasChocolateChecked);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+email_customer)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for"+name_customer);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    public void Increment(View view) {
        if(quantity>20) return;;
        quantity = 1 + quantity;
        display(quantity);
    }

    public void Decrement(View view) {
        if (quantity < 1) return;
        quantity = quantity - 1;
        display(quantity);
    }

    private String createOrderSummary(String name_customer,String email_customer,String phone_customer,int price,boolean addWhippedCream,boolean addChocolate){
        String priceMessage="Name: "+name_customer;
        priceMessage+="\nEmail: "+ email_customer;
        priceMessage+="\nPhone: "+ phone_customer;
        priceMessage+="\nQuantity: "+ quantity +"\n";
        if(addWhippedCream==true)priceMessage+="With Whipped Cream";
        if(addChocolate==true && addWhippedCream==true) priceMessage+="and";
        if(addChocolate==true) priceMessage+="With Chocolate";
        priceMessage += "\nTotal: $"+price;
        priceMessage+="\nThank You!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String priceMessage) {
        TextView priceTextView = (TextView) findViewById(R.id.order_text_view);
        priceTextView.setText(priceMessage);
    }
}