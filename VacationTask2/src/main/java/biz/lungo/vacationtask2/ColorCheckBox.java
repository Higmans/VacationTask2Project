package biz.lungo.vacationtask2;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;

class ColorCheckBox extends CheckBox implements CompoundButton.OnCheckedChangeListener {
    static int alpha = 255;
    private static int red = 0;
    private static int green = 0;
    private static int blue = 0;

    public ColorCheckBox(Context context) {
        super(context);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if ((compoundButton.getId() == R.id.checkBoxRed) && compoundButton.isChecked())
            red = 255;
        else if (compoundButton.getId() == R.id.checkBoxRed)
            red = 0;

        if (compoundButton.getId() == R.id.checkBoxGreen && compoundButton.isChecked())
            green = 255;
        else if (compoundButton.getId() == R.id.checkBoxGreen)
            green = 0;

        if (compoundButton.getId() == R.id.checkBoxBlue && compoundButton.isChecked())
            blue = 255;
        else if (compoundButton.getId() == R.id.checkBoxBlue)
            blue = 0;

        setColor();
    }
    public static void setColor(){
        MainActivity.output.setTextColor(Color.argb(alpha, red, green, blue));
    }
}
