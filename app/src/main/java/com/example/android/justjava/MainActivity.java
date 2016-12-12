package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    int quantity = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_LONG).show();
            return;
          }
        quantity = quantity + 1;
        display(quantity);
         }

    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this, "You cannot have less than 1 coffees",Toast.LENGTH_LONG).show();
            return;
             }
        quantity = quantity - 1;
        display(quantity);
        }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
         }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.editText);
        String value = text.getText().toString();

        CheckBox ch1 = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = ch1.isChecked();

        CheckBox ch2 = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = ch2.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String dMess = createorderSummary(price, hasWhippedCream, hasChocolate, value);
//        textMesse = displayMessage(dMess);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for "+ value);
        intent.putExtra(Intent.EXTRA_TEXT, dMess );

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
//    public void composeEmail(String[] addresses, String subject) {
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean x, boolean y) {
        int basePrice = 5;

        if (x) {
            basePrice = basePrice + 1;
        }

        if (y) {
            basePrice = basePrice + 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    private String createorderSummary(int number, boolean whip, boolean choc, String text) {
        String message = text + "\n" + "Add whipped cream? " + whip + "\nAdd chocolate? " + choc + "\nQuantity: " + quantity + "\nTotal: " + number + "\n"+getString(R.string.thank_you);
        return message;
    }
}
