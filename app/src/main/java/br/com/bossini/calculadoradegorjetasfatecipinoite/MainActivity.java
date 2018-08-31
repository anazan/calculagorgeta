package br.com.bossini.calculadoradegorjetasfatecipinoite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static NumberFormat percentFormat =
            NumberFormat.getPercentInstance();
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    private double billAmount = 0.0;
    private double percent =  0.15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        EditText amountEditText = (EditText)
                findViewById(R.id.amountEditText);
        ObservadorDoEditText observadorDoEditText =
                new ObservadorDoEditText();
        amountEditText.addTextChangedListener(observadorDoEditText);
        SeekBar percentSeekBar = (SeekBar)
                findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(new ObservadorDaSeekBar());
    }

    private class ObservadorDaSeekBar implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percentTextView.setText(percentFormat.format(progress / 100.0));
            percent = progress / 100.0;
            double tip = billAmount * percent;
            double total = billAmount + tip;
            tipTextView.setText(currencyFormat.format(tip));
            totalTextView.setText(currencyFormat.format(total));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
    private class ObservadorDoEditText implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                billAmount = Double.parseDouble(s.toString()) / 100;
                amountTextView.setText(currencyFormat.format(billAmount));
                double tip = billAmount * percent;
                double total = billAmount + tip;
                tipTextView.setText(currencyFormat.format(tip));
                totalTextView.setText(currencyFormat.format(total));
            }
            catch (NumberFormatException e){
                billAmount = 0;
                amountTextView.setText(currencyFormat.format(0));
                tipTextView.setText(currencyFormat.format(0));
                totalTextView.setText(currencyFormat.format(0));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
