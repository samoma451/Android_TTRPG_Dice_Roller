package sam.oma.ttrpgdiceroller;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ttrpgdiceroller.R;

import java.util.Arrays;
import java.util.Random;

public class rollDiceActivity extends AppCompatActivity {

    private ImageView[] diceCollection;//stores each dice ImageView
    private TextView rollResult;//used to display results of dice rolls and sum of results
    private int numOfDice = 1;//tracks the number of dices requested to be rolled
    private final int maxNumOfDice = 8;//setting the max number of dice. Have as class variable to make easier to change in future if needed

    //arrays holding images to be used for different types of dice and values of dice faces
    private final int[] d4Images = new int[]{R.drawable.d4_1, R.drawable.d4_2, R.drawable.d4_3, R.drawable.d4_4};

    private final int[] d6Images = new int[]{R.drawable.d6_1, R.drawable.d6_2, R.drawable.d6_3, R.drawable.d6_4, R.drawable.d6_5, R.drawable.d6_6};

    private final int[] d8Images = new int[]{R.drawable.d8_1, R.drawable.d8_2, R.drawable.d8_3, R.drawable.d8_4,
            R.drawable.d8_5, R.drawable.d8_6, R.drawable.d8_7, R.drawable.d8_8};

    private final int[] d10Images = new int[]{R.drawable.d10_1, R.drawable.d10_2, R.drawable.d10_3, R.drawable.d10_4,
            R.drawable.d10_5, R.drawable.d10_6, R.drawable.d10_7, R.drawable.d10_8,
            R.drawable.d10_9, R.drawable.d10_10};

    private final int[] d12Images = new int[]{R.drawable.d12_1, R.drawable.d12_2, R.drawable.d12_3, R.drawable.d12_4,
            R.drawable.d12_5, R.drawable.d12_6, R.drawable.d12_7, R.drawable.d12_8,
            R.drawable.d12_9, R.drawable.d12_10, R.drawable.d12_11, R.drawable.d12_12,};

    private final int[] d20Images = new int[]{R.drawable.d20_1, R.drawable.d20_2, R.drawable.d20_3, R.drawable.d20_4,
            R.drawable.d20_5, R.drawable.d20_6, R.drawable.d20_7, R.drawable.d20_8,
            R.drawable.d20_9, R.drawable.d20_10, R.drawable.d20_11, R.drawable.d20_12,
            R.drawable.d20_13, R.drawable.d20_14, R.drawable.d20_15, R.drawable.d20_16,
            R.drawable.d20_17, R.drawable.d20_18, R.drawable.d20_19, R.drawable.d20_20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice);

        //inform user to click dice to roll when starting this activity
        Toast.makeText(this, "Tap the Dice to Roll!", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        int diceType = intent.getIntExtra("diceType", 20);//dice type passed based on button clicked by user in main activity

        //calculating and rounding now as is frequently used and too tedious to round on every occurence
        final int halfMaxNumOfDice = (int) Math.ceil(maxNumOfDice/2);
        //assigning each button for later use
        Button increaseBtn = findViewById(R.id.increaseDice);
        Button decreaseBtn = findViewById(R.id.decreaseDice);

        Button backbtn = findViewById(R.id.backbtn);

        //linear layout (horizontal) containing dice ImageViews
        LinearLayout diceSpace = findViewById(R.id.diceSpace);

        //assigning each dice ImageView and adding to diceCollection Array
        ImageView diceA = findViewById(R.id.diceA);
        ImageView diceB = findViewById(R.id.diceB);
        ImageView diceC = findViewById(R.id.diceC);
        ImageView diceD = findViewById(R.id.diceD);
        ImageView diceE = findViewById(R.id.diceE);
        ImageView diceF = findViewById(R.id.diceF);
        ImageView diceG = findViewById(R.id.diceG);
        ImageView diceH = findViewById(R.id.diceH);

        diceCollection = new ImageView[]{diceA, diceB, diceC, diceD, diceE, diceF, diceG, diceH};

        //assigning TextView that displays roll results
        rollResult = findViewById(R.id.rollResult);

        //initialising diceImages
        int[] diceImages = new int[0];
        //assigning the images to be used depending on user selection of dice type
        switch (diceType){
            case 4:
                diceImages = d4Images;
                break;

            case 6:
                diceImages = d6Images;
                break;

            case 8:
                diceImages = d8Images;
                break;

            case 10:
                diceImages = d10Images;
                break;

            case 12:
                diceImages = d12Images;
                break;

            case 20:
                diceImages = d20Images;
                break;
        }

        //setting ImageViews appropriately now that we have our array of images assigned
        for(int i = 0;i<maxNumOfDice;i++){
            diceCollection[i].setImageResource(diceImages[0]);
        }

        //used to set size of dice ImageViews. Is there a better way to do this? getMetrics() deprecated :(
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


        //bringing user back to main activity
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(rollDiceActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dice_sound);//setting up sound of dice rolling with MediaPlayer object

        int[] finalDiceImages = diceImages;//android studio suggests copying to "effectively temp final variable" - need to understand this better

        //onClickListener for rolling dice when diceSpace (the layout containing the dice) is clicked
        diceSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randGen = new Random();//will be used to generate dice rolls

                int[] randNums = new int[maxNumOfDice];
                //StringBuilder more efficient than String for concatenating in loop. See https://pellegrino.link/2015/08/22/string-concatenation-with-java-8.html
                //tl;dr String -> O(n^2), StringBuilder -> O(n)
                StringBuilder rollResultText = new StringBuilder("Result: ");

                mp.start();//plays sound of dice rolling

                //uses shake_animation.xml which animates dice to show they have been rolled
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_animation);

                //generate a random roll for each dice, change ImageView to match, shake animation, and add to text for rollResult TextView
                for (int i = 0; i < numOfDice; i++) {
                    randNums[i] = randGen.nextInt(diceType)+1;
                    diceCollection[i].setImageResource(finalDiceImages[randNums[i]-1]);
                    diceCollection[i].startAnimation(shake);
                    rollResultText.append(randNums[i]).append(" + ");
                }
                
                
                rollResultText = new StringBuilder(rollResultText.substring(0, rollResultText.length() - 3));//removing excessive " + "
                rollResultText.append(" = ").append(Arrays.stream(randNums).sum());//add sum of rolls to end of text for rollResult TextView

                //Setting text on rollResult TextView. if statement checks if only one dice rolled as showing Result: 4 = 4 looks sloppy
                if (numOfDice == 1){
                    String numToShow = Arrays.toString(randNums);
                    if (randNums[0] <= 10) {//checking if single or double digit number rolled. Might be a more efficient way to do this
                        //also might be a better approach by declaring string formats?
                        rollResult.setText("Result: " + numToShow.charAt(1));//slightly faster than substring
                    }else{
                        rollResult.setText("Result: " + numToShow.substring(1, 3));
                    }
                }else{rollResult.setText(rollResultText.toString());}

            }
        });


        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numOfDice<maxNumOfDice) {
                    numOfDice++;

                    if(numOfDice > 1){
                        decreaseBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.blueButtonEnabled));
                    }

                    if(numOfDice == maxNumOfDice){
                        increaseBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.blueButtonDisabled));
                    }

                    if(numOfDice==halfMaxNumOfDice+1){
                        diceCollection[halfMaxNumOfDice].setVisibility(View.VISIBLE);
                    }

                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



                    if (numOfDice <= halfMaxNumOfDice){
                        params.width = screenWidth / numOfDice ;
                        for (int i = 0; i < numOfDice; i++) {
                            diceCollection[i].setLayoutParams(params);
                        }

                    }else{
                        params.width = screenWidth / (numOfDice - halfMaxNumOfDice);
                        for (int i = halfMaxNumOfDice; i < numOfDice; i++) {
                            diceCollection[i].setLayoutParams(params);
                        }
                    }

                }else{
                    Toast.makeText(rollDiceActivity.this, "Max number of dice reached", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numOfDice>1) {
                    if(numOfDice==halfMaxNumOfDice+1){
                        diceCollection[halfMaxNumOfDice].setVisibility(View.INVISIBLE);
                        }

                    numOfDice--;
                    if(numOfDice == 1){
                        decreaseBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.blueButtonDisabled));
                    }
                    if(numOfDice < maxNumOfDice){
                        increaseBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.blueButtonEnabled));
                    }

                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    if (numOfDice <= halfMaxNumOfDice){
                        params.width = screenWidth / numOfDice ;
                        for (int i = 0; i < numOfDice; i++) {
                            diceCollection[i].setLayoutParams(params);
                        }


                    }else{
                        params.width = screenWidth / (numOfDice - halfMaxNumOfDice);
                        for (int i = halfMaxNumOfDice; i < numOfDice; i++) {
                            diceCollection[i].setLayoutParams(params);
                        }
                    }
                }else{
                    Toast.makeText(rollDiceActivity.this, "Zero dice? Seriously?", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

}