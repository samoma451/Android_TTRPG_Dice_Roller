package sam.oma.ttrpgdiceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttrpgdiceroller.R;

import java.util.Random;

public class rolld100Activity extends AppCompatActivity {

    private ImageView[] diceCollection;//stores each dice ImageView
    private TextView rollResult;//used to display results of dice rolls and sum of results
    private final int numOfDice = 2;//tracks the number of dices requested to be rolled

    //arrays holding images to be used for different types of dice and values of dice faces
    private final int[] d10Images = new int[]{R.drawable.d10_1, R.drawable.d10_2, R.drawable.d10_3, R.drawable.d10_4,
            R.drawable.d10_5, R.drawable.d10_6, R.drawable.d10_7, R.drawable.d10_8,
            R.drawable.d10_9, R.drawable.d10_10};

    private final int[] d100Images = new int[]{R.drawable.d100_00, R.drawable.d100_10, R.drawable.d100_20, R.drawable.d100_30,
            R.drawable.d100_40, R.drawable.d100_50, R.drawable.d100_60, R.drawable.d100_70,
            R.drawable.d100_80, R.drawable.d100_90};

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_d100);

        //inform user to click dice to roll when starting this activity
        Toast.makeText(this, "Tap the Dice to Roll!", Toast.LENGTH_SHORT).show();


        Button backbtn = findViewById(R.id.backbtn);

        //linear layout (horizontal) containing dice ImageViews
        LinearLayout diceSpace = findViewById(R.id.diceSpace);

        //assigning each dice ImageView and adding to diceCollection Array
        ImageView diced100A = findViewById(R.id.diceA);
        ImageView diced10A = findViewById(R.id.diceB);

        diceCollection = new ImageView[]{diced100A, diced10A};

        //assigning TextView that displays roll results
        rollResult = findViewById(R.id.rollResult);

        //used to set size of dice ImageViews. Is there a better way to do this? getMetrics() deprecated :(
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


        //bringing user back to main activity
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(rolld100Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dice_sound);//setting up sound of dice rolling with MediaPlayer object

        //onClickListener for rolling dice when diceSpace (the layout containing the dice) is clicked
        diceSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randGen = new Random();//will be used to generate dice rolls

                int[] randNums = new int[numOfDice];
                //StringBuilder more efficient than String for concatenating in loop. See https://pellegrino.link/2015/08/22/string-concatenation-with-java-8.html
                //tl;dr String -> O(n^2), StringBuilder -> O(n)
                StringBuilder rollResultText = new StringBuilder("Result: ");

                mp.start();//plays sound of dice rolling

                //uses shake_animation.xml which animates dice to show they have been rolled
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_animation);

                int sumOfRolls = 0;//needed for getting sum of d100s and d10s

                //generate a random roll for each dice, change ImageView to match, shake animation, and add to text for rollResult TextView
                for (int i = 0; i < numOfDice; i++) {
                    randNums[i] = randGen.nextInt(10)+1;
                }

                for (int i = 0; i < numOfDice; i++){

                    if (randNums[0] == 1 && randNums[1] == 10){
                        diceCollection[0].setImageResource(d100Images[0]);
                        diceCollection[1].setImageResource(d10Images[9]);

                        rollResultText.append("00 + 10   ");
                        sumOfRolls = 100;

                        diceCollection[0].startAnimation(shake);
                        diceCollection[1].startAnimation(shake);
                        break;
                        }

                    if(i == 0){//even dice = d100s, odd dice = d10s
                        diceCollection[i].setImageResource(d100Images[randNums[i]-1]);
                        rollResultText.append(randNums[i]*10 - 10).append(" + ");
                        sumOfRolls += randNums[i]*10 - 10;
                    }else{
                        diceCollection[i].setImageResource(d10Images[randNums[i]-1]);
                        rollResultText.append(randNums[i]).append(" + ");
                        sumOfRolls += randNums[i];
                    }
                    diceCollection[i].startAnimation(shake);
                }


                rollResultText = new StringBuilder(rollResultText.substring(0, rollResultText.length() - 3));//removing excessive " + "
                rollResultText.append(" = ").append(sumOfRolls);//add sum of rolls to end of text for rollResult TextView

                rollResult.setText(rollResultText.toString());

            }
        });

    }

}