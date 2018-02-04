package com.example.andrei.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox cb = findViewById(R.id.whipped_cream_checkbox);
        boolean creamTopping = cb.isChecked();

        cb = findViewById(R.id.chocolate_checkbox);
        boolean chocolateTopping = cb.isChecked();

        EditText et = findViewById(R.id.field);
        String name = et.getText().toString();


        int price = calculatePrice(creamTopping, chocolateTopping);
        String priceMessage = createOrderSummary(price, name, creamTopping, chocolateTopping);

        //displayMessage(priceMessage);
        orderToEmail(name, priceMessage);
    }

    public void orderToEmail(String name, String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_title,name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Create summary of the order.
     *
     * @param name is the name of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */

    public String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name) + "\n";
        priceMessage += getString(R.string.add_wc) +" "+ addWhippedCream + "\n" ;
        priceMessage += getString(R.string.add_c) + " "+ addChocolate + "\n" ;
        priceMessage += getString(R.string.quantity)+": " + quantity + "\n";
        priceMessage += getString(R.string.total, price) + "\n";
        priceMessage += getString(R.string.thank_you);
        return priceMessage;
    }

    public int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int price = quantity * pricePerCup;
        if(addWhippedCream) price++;
        if(addChocolate) price += 2;
        return price;
    }

    public void decrement(View v){
        if(quantity == 1) return;

        quantity--;
        display(quantity);
    }

    public void increment(View v) {
        if(quantity == 100) return;

        quantity++;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayPrice(int number){
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}
