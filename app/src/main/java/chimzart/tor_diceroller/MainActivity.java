package chimzart.tor_diceroller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

// componentes interface
    private SeekBar seekBar_dices;
    private Switch switch_disadvantage;
    private Switch switch_advantage;
    private Switch switch_feat;
    private Switch switch_weary;
    private Button button_roll;
    private TextView seekBar_progress;
    private int diceValue = 0;
    //private boolean advantage_Status = false;
    //private int disadvantage_Status = false;
    //private int weary_Status = false;

// cria a interface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Recuperar idenificadores dos objetos da interface

        seekBar_dices = (SeekBar) findViewById(R.id.seekBar_dices);
        seekBar_progress = (TextView) findViewById(R.id.seekBar_progress);
        switch_feat = (Switch) findViewById(R.id.switch_feat);
        switch_weary = (Switch) findViewById(R.id.switch_weary);
        switch_advantage = (Switch) findViewById(R.id.switch_advantage);
        switch_disadvantage = (Switch) findViewById(R.id.switch_disadvantage);
        button_roll = (Button) findViewById(R.id.button_roll);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//SeekBar
        seekBar_dices.setProgress(diceValue);
        seekBar_progress.setText(""+ diceValue);

        seekBar_dices.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar_dices, int i, boolean b) {
                diceValue = i;
                seekBar_progress.setText(""+ diceValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar_dices) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar_dices) {
            }
        });

//Switches

        switch_feat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_advantage.setEnabled(false);
                    switch_disadvantage.setEnabled(false);
                } else {
                    switch_advantage.setEnabled(true);
                    switch_disadvantage.setEnabled(true);
                }
            }
        });

        switch_advantage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_disadvantage.setEnabled(false);
                } else {
                    switch_disadvantage.setEnabled(true);
                }
            }
        });

        switch_disadvantage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_advantage.setEnabled(false);
                } else {
                    switch_advantage.setEnabled(true);
                }
            }
        });
//Button
        button_roll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String ResString = "";
                //verify how many sucess dices are; draw the results for each dice
                for(int i = diceValue; i>0; i--) {
                   int Roll = diceRoll(6);
                //check weary status and ignore the lessers results

                    ResString += " |Dice"+i;
                    if(switch_weary.isChecked() && Roll<= 3 ){
                        ResString += ": NULO";
                    }else{
                        ResString += ": " +Roll;}

                }
                //account for great and extraordinary sucess


                //roll the feat die
                if(!switch_feat.isChecked()) {

                    //roll again for vantage/disvantage plays
                    Integer[] RollAr = new Integer[] {0,0};
                    RollAr[0] = diceRoll(12);
                    RollAr[1] = diceRoll(12);

                    if(switch_advantage.isChecked()) {
                        Arrays.sort(RollAr, Collections.reverseOrder());
                    }
                    if(switch_disadvantage.isChecked()) {
                        Arrays.sort(RollAr);


                    }

                    //check weary status and ignore the lessers results
                    ResString += " |Feat Die:";
                    if (switch_weary.isChecked() && RollAr[0] <= 3) {
                        ResString += ": NULO";
                    } else {
                        ResString += ": " + RollAr[0];
                    }


                    //check for Gandalf's rune and Sauron's eye
                }

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setMessage(ResString);
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }
        });

//ActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "fernando.ferreira@ufrgs.br", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // random number
    private int diceRoll(int sides) {
      //  private static final int MIN_NUMBER = 1;
        return (1 + (int) (Math.random() * ((sides - 1) + 1)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
