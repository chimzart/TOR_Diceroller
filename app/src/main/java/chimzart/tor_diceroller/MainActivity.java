package chimzart.tor_diceroller;

import android.media.Image;
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
    private ImageView[] imageView_dice = new ImageView[8];
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

        imageView_dice[0] = (ImageView) findViewById (R.id.imageView_dice1);
        imageView_dice[1] = (ImageView) findViewById (R.id.imageView_dice2);
        imageView_dice[2] = (ImageView) findViewById (R.id.imageView_dice3);
        imageView_dice[3] = (ImageView) findViewById (R.id.imageView_dice4);
        imageView_dice[4] = (ImageView) findViewById (R.id.imageView_dice5);
        imageView_dice[5] = (ImageView) findViewById (R.id.imageView_dice6);
        imageView_dice[6] = (ImageView) findViewById (R.id.imageView_dice7);
        imageView_dice[7] = (ImageView) findViewById (R.id.imageView_dice8);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView_dice[0].setVisibility(View.INVISIBLE);
        imageView_dice[1].setVisibility(View.INVISIBLE);
        imageView_dice[2].setVisibility(View.INVISIBLE);
        imageView_dice[3].setVisibility(View.INVISIBLE);
        imageView_dice[4].setVisibility(View.INVISIBLE);
        imageView_dice[5].setVisibility(View.INVISIBLE);
        imageView_dice[6].setVisibility(View.INVISIBLE);
        imageView_dice[7].setVisibility(View.INVISIBLE);


        imageView_dice[0].setImageResource(R.mipmap.d12_g);
        imageView_dice[1].setImageResource(R.mipmap.d12_10);
        imageView_dice[2].setImageResource(R.mipmap.d6_1);
        imageView_dice[3].setImageResource(R.mipmap.d6_2);
        imageView_dice[4].setImageResource(R.mipmap.d6_3);
        imageView_dice[5].setImageResource(R.mipmap.d6_4);
        imageView_dice[6].setImageResource(R.mipmap.d6_5);
        imageView_dice[7].setImageResource(R.mipmap.d6_6);

       // imageView_dice2.setImageResource(@mipmap/d12_6);


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
                    switch_advantage.setChecked(false);
                    switch_disadvantage.setEnabled(false);
                    switch_disadvantage.setChecked(false);
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
                    switch_disadvantage.setChecked(false);
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
                    switch_advantage.setChecked(false);
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
                imageView_dice[0].setVisibility(View.INVISIBLE);
                imageView_dice[1].setVisibility(View.INVISIBLE);
                imageView_dice[2].setVisibility(View.INVISIBLE);
                imageView_dice[3].setVisibility(View.INVISIBLE);
                imageView_dice[4].setVisibility(View.INVISIBLE);
                imageView_dice[5].setVisibility(View.INVISIBLE);
                imageView_dice[6].setVisibility(View.INVISIBLE);
                imageView_dice[7].setVisibility(View.INVISIBLE);


                //verify how many sucess dices are; draw the results for each dice
                int[] Roll = new int[diceValue];
                Integer[] d6_img =  {R.mipmap.d6_1, R.mipmap.d6_2, R.mipmap.d6_3, R.mipmap.d6_4, R.mipmap.d6_5, R.mipmap.d6_6};

                for(int i = 0; i<diceValue; i++) {
                   imageView_dice[2+i].setVisibility(View.VISIBLE);
                   Roll[i] = diceRoll(6);
                   imageView_dice[i+2].setImageResource(d6_img[Roll[i]-1]);

                //check weary status and ignore the lessers results

                    ResString += " |Dice"+i;
                    if(switch_weary.isChecked() && Roll[i]<= 3 ){
                        ResString += ": NULO";
                    }else{
                        ResString += ": " +Roll[i];}

                }
                //account for great and extraordinary sucess


                //roll the feat die

                if(!switch_feat.isChecked()) {
                    imageView_dice[0].setVisibility(View.VISIBLE);
                    //roll again for vantage/disvantage plays
                    Integer[] d12_img =  {R.mipmap.d12_0, R.mipmap.d12_1, R.mipmap.d12_2, R.mipmap.d12_3, R.mipmap.d12_4, R.mipmap.d12_5, R.mipmap.d12_6, R.mipmap.d12_7, R.mipmap.d12_8, R.mipmap.d12_9, R.mipmap.d12_10, R.mipmap.d12_g};
                    Integer[] RollAr = new Integer[] {0,0};
                    RollAr[0] = diceRoll(12);
                    imageView_dice[0].setImageResource(d12_img[RollAr[0]-1]);
                    RollAr[1] = diceRoll(12);
                    imageView_dice[1].setImageResource(d12_img[RollAr[1]-1]);

                    if(switch_advantage.isChecked()) {
                        imageView_dice[1].setVisibility(View.VISIBLE);
                        Arrays.sort(RollAr, Collections.reverseOrder());
                    }
                    if(switch_disadvantage.isChecked()) {
                        imageView_dice[1].setVisibility(View.VISIBLE);
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

         /*     manual result presentatiom method
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setMessage(ResString);
                dlg.setNeutralButton("OK",null);
                dlg.show();
         */
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
