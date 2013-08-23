package biz.lungo.vacationtask2;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import org.apache.commons.lang3.StringUtils;

public class MainActivity extends Activity {
    static TextView output;
    private static TextView searchOutput;
    private static Activity mainActivity;
    private EditText input;
    private EditText search;
    private RadioGroup radioGroupSearchCase;
    private int textSizeStep;
    private int searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);
        CheckBox checkboxRed = (CheckBox) findViewById(R.id.checkBoxRed);
        CheckBox checkboxGreen = (CheckBox) findViewById(R.id.checkBoxGreen);
        CheckBox checkboxBlue = (CheckBox) findViewById(R.id.checkBoxBlue);
        RadioGroup radioGroupOpacity = (RadioGroup) findViewById(R.id.radioGroupOpacity);
        RadioButton radioButtonSolid = (RadioButton) findViewById(R.id.radioButtonSolid);
        RadioButton radioButtonAnyCase = (RadioButton) findViewById(R.id.rbIgnoreCase);
        Spinner textSizeStepChooser = (Spinner) findViewById(R.id.spinnerStepChooser);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        input = (EditText) findViewById(R.id.editTextInput);
        search = (EditText) findViewById(R.id.editTextSearch);
        output = (TextView) findViewById(R.id.textViewOutput);
        searchOutput = (TextView) findViewById(R.id.textViewResultOutput);
        radioGroupSearchCase = (RadioGroup) findViewById(R.id.rgSearchCase);

        radioButtonSolid.setChecked(true);
        radioButtonAnyCase.setChecked(true);
        input.setRawInputType(InputType.TYPE_CLASS_TEXT);
        input.setImeOptions(EditorInfo.IME_ACTION_GO);
        search.setRawInputType(InputType.TYPE_CLASS_TEXT);
        search.setImeOptions(EditorInfo.IME_ACTION_GO);
        checkboxRed.setOnCheckedChangeListener(new ColorCheckBox(this));
        checkboxGreen.setOnCheckedChangeListener(new ColorCheckBox(this));
        checkboxBlue.setOnCheckedChangeListener(new ColorCheckBox(this));

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setTextSize(TypedValue.COMPLEX_UNIT_PX, output.getTextSize() + textSizeStep);
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setTextSize(TypedValue.COMPLEX_UNIT_PX, output.getTextSize() - textSizeStep);
            }
        });

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                output.setText(input.getText());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                //noinspection ConstantConditions
                imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (search.getText() != null)
                    //noinspection ConstantConditions
                    searchResult = performSearch(search.getText().toString());

                searchOutput.setText(generateOutput(searchResult));

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                //noinspection ConstantConditions
                imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        radioGroupOpacity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButtonSolid) {
                    ColorCheckBox.alpha = 255;
                } else if (i == R.id.radioButtonHalf) {
                    ColorCheckBox.alpha = 127;
                } else if (i == R.id.radioButtonTransparent) {
                    ColorCheckBox.alpha = 0;
                }
                ColorCheckBox.setColor();
            }
        });

        textSizeStepChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //noinspection ConstantConditions
                textSizeStep = Integer.parseInt(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGroupSearchCase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (search.getText() != null)
                    //noinspection ConstantConditions
                    searchOutput.setText(generateOutput(performSearch(search.getText().toString())));
            }
        });
    }

    private String generateOutput(int searchResult) {
        String result;
        if (searchResult > 1 && searchResult < 5){
            result = "Найдено " + searchResult + " совпадения";
        }
        else if (searchResult >= 5 || searchResult == 0){
            result = "Найдено " + searchResult + " совпадений";
        }
        else{
            result = "Найдено " + searchResult + " совпадение";
        }
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    int performSearch(String searchQuery) {
        switch (radioGroupSearchCase.getCheckedRadioButtonId()){
            case R.id.rbIgnoreCase:
                return StringUtils.countMatches(output.getText().toString().toLowerCase(), searchQuery.toLowerCase());
            case R.id.rbUpperCase:
                return StringUtils.countMatches(output.getText().toString(), searchQuery.toUpperCase());
            case R.id.rbLowerCase:
                return StringUtils.countMatches(output.getText().toString(), searchQuery.toLowerCase());
            default:
                return 0;
        }
    }
}