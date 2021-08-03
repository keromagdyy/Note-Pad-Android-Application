package com.example.notepad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText txtFileName;
    EditText txtWords;
    EditText txtSize;
    Button btnBig;
    Button btnSmall;
    Button btnBold;
    Button btnItalic;
    Button btnULine;
    Spinner SFont;
    Spinner SColor;
    RadioButton RadioLtR;
    RadioButton RadioRtL;
    RadioButton RadioCenter;
    EditText txtSearch;
    Button btnNew;
    Button btnSave;
    Button btnOpen;
    int s;
    boolean chBold = false;
    boolean chItalic = false;
    boolean chULine = false;
    Typeface tf;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFileName = findViewById(R.id.txtFileName);
        txtWords = findViewById(R.id.txtWords);
        txtSize = findViewById(R.id.txtSize);
        btnBig = findViewById(R.id.btnBig);
        btnSmall = findViewById(R.id.btnSmall);
        btnBold = findViewById(R.id.btnBold);
        btnItalic = findViewById(R.id.btnI);
        btnULine = findViewById(R.id.txtULine);
        SFont = findViewById(R.id.SFont);
        SColor = findViewById(R.id.SColor);
        RadioLtR = findViewById(R.id.radioLtR);
        RadioRtL = findViewById(R.id.radioRtL);
        RadioCenter = findViewById(R.id.radioCenter);
        txtSearch = findViewById(R.id.txtSearch);
        btnNew = findViewById(R.id.bntNew);
        btnSave = findViewById(R.id.btnSave);
        btnOpen = findViewById(R.id.btnOpen);
        Color();
        Font();

        s = Integer.parseInt(txtSize.getText() + "");
        txtWords.setTextSize(s);

        btnBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Size(true);
            }
        });

        btnSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Size(false);
            }
        });

        txtSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ("".equals(txtSize.getText().toString().trim())) {
                    s = 6;
                } else
                    s = Integer.parseInt(txtSize.getText() + "");

                if (s <= 6) s = 6;
                else if (s >= 99) s = 99;
                txtWords.setTextSize(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                SizeBold();
            }
        });

        btnItalic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                SizeItalic();
            }
        });

        btnULine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SizeULine();
            }
        });

        SColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Color2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Font2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RadioLtR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioBTN();
            }
        });

        RadioRtL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioBTN();
            }
        });

        RadioCenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioBTN();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewFile();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveFile();
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFile();
            }
        });


    }

    protected void Size(boolean ch) {
        if (ch == true) s++;
        else s--;

        txtWords.setTextSize(s);
        txtSize.setText(s + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void SizeBold() {
        if (chBold == false) {
            if (chItalic == true)
                txtWords.setTypeface(tf, Typeface.BOLD_ITALIC);
            else
                txtWords.setTypeface(tf, Typeface.BOLD);
            btnBold.setTypeface(tf, Typeface.BOLD);
            btnBold.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            chBold = true;
        } else {
            if (chItalic == true)
                txtWords.setTypeface(tf, Typeface.ITALIC);
            else
                txtWords.setTypeface(tf, Typeface.NORMAL);
            btnBold.setTypeface(tf, Typeface.NORMAL);
            btnBold.setBackground(getResources().getDrawable(R.drawable.btn1));
            chBold = false;
        }

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewFile();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void SizeItalic() {
        if (chItalic == false) {
            if (chBold == true)
                txtWords.setTypeface(tf, Typeface.BOLD_ITALIC);
            else
                txtWords.setTypeface(tf, Typeface.ITALIC);
            btnItalic.setTypeface(tf, Typeface.ITALIC);
            btnItalic.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            chItalic = true;
        } else {
            if (chBold == true)
                txtWords.setTypeface(tf, Typeface.BOLD);
            else
                txtWords.setTypeface(tf, Typeface.NORMAL);
            btnItalic.setTypeface(tf, Typeface.NORMAL);
            btnItalic.setBackground(getResources().getDrawable(R.drawable.btn1));
            chItalic = false;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SaveFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFile();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void SizeULine() {
        if (chULine == false) {
            txtWords.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            btnULine.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            btnULine.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            chULine = true;
        } else {
            txtWords.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            btnULine.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            btnULine.setBackground(getResources().getDrawable(R.drawable.btn1));
            chULine = false;
        }
    }

    protected void Color() {
        String[] ColorsSpinner = {
                "Black",
                "White",
                "Gray",
                "Red",
                "Green",
                "Blue",
                "Yellow",
                "Brown",
                "Pink",
                "Orange",
                "DarkRed",
                "DarkBlue",
                "DarkGreen",};
        ArrayAdapter<String> A = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ColorsSpinner);

        SColor.setAdapter(A);
    }

    protected void Color2() {
        String C = SColor.getSelectedItem().toString();
        switch (C) {
            case "Black":
                txtWords.setTextColor(getResources().getColor(R.color.Black));
                break;
            case "White":
                txtWords.setTextColor(getResources().getColor(R.color.White));
                break;
            case "Gray":
                txtWords.setTextColor(getResources().getColor(R.color.Gray));
                break;
            case "Red":
                txtWords.setTextColor(getResources().getColor(R.color.Red));
                break;
            case "Green":
                txtWords.setTextColor(getResources().getColor(R.color.Green));
                break;
            case "Blue":
                txtWords.setTextColor(getResources().getColor(R.color.Blue));
                break;
            case "Yellow":
                txtWords.setTextColor(getResources().getColor(R.color.Yellow));
                break;
            case "Brown":
                txtWords.setTextColor(getResources().getColor(R.color.Brown));
                break;
            case "Pink":
                txtWords.setTextColor(getResources().getColor(R.color.Pink));
                break;
            case "Orange":
                txtWords.setTextColor(getResources().getColor(R.color.Orange));
                break;
            case "DarkRed":
                txtWords.setTextColor(getResources().getColor(R.color.DarkRed));
                break;
            case "DarkBlue":
                txtWords.setTextColor(getResources().getColor(R.color.DarkBlue));
                break;
            case "DarkGreen":
                txtWords.setTextColor(getResources().getColor(R.color.DarkGreen));
        }
    }

    protected void Font() {
        String[] fontSpinner = {
                "SANS_SERIF",
                "Adventure",
                "ae_Dimnah",
                "BLATANT",
                "CRESSID",
                "firestar",
                "ANASTAS",
                "ITCBLKAD",
                "ARNORG_",
                "AL-Gemah-Alhoda",
                "ALMOSNOW",
                "aldhabi",
                "BAUHS93",
                "الخطوط اليدوية (158)",
                "أبو راضى عم الناس كلهم",
                "الخطوط اليدوية (146)",
                "الخطوط اليدوية (147)",
        };
        ArrayAdapter<String> A = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fontSpinner);
        SFont.setAdapter(A);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void Font2() {
        String f = SFont.getSelectedItem().toString();
        tf = Typeface.SANS_SERIF;
        switch (f) {
            case "SANS_SERIF":
                tf = Typeface.SANS_SERIF;
                break;
            case "Adventure":
                tf = Typeface.createFromAsset(getAssets(), "Adventure.TTF");
                break;
            case "ae_Dimnah":
                tf = Typeface.createFromAsset(getAssets(), "ae_Dimnah.ttf");
                break;
            case "BLATANT":
                tf = Typeface.createFromAsset(getAssets(), "BLATANT.TTF");
                break;
            case "CRESSID":
                tf = Typeface.createFromAsset(getAssets(), "CRESSID.TTF");
                break;
            case "firestar":
                tf = Typeface.createFromAsset(getAssets(), "firestar.ttf");
                break;
            case "ANASTAS":
                tf = Typeface.createFromAsset(getAssets(), "ANASTAS.TTF");
                break;
            case "ITCBLKAD":
                tf = Typeface.createFromAsset(getAssets(), "ITCBLKAD.TTF");
                break;
            case "ARNORG_":
                tf = Typeface.createFromAsset(getAssets(), "ARNORG_.TTF");
                break;
            case "AL-Gemah-Alhoda":
                tf = Typeface.createFromAsset(getAssets(), "AL-Gemah-Alhoda.ttf");
                break;
            case "ALMOSNOW":
                tf = Typeface.createFromAsset(getAssets(), "ALMOSNOW.TTF");
                break;
            case "aldhabi":
                tf = Typeface.createFromAsset(getAssets(), "aldhabi.ttf");
                break;
            case "BAUHS93":
                tf = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");
                break;
            case "الخطوط اليدوية (158)":
                tf = Typeface.createFromAsset(getAssets(), "الخطوط اليدوية (158).ttf");
                break;
            case "أبو راضى عم الناس كلهم":
                tf = Typeface.createFromAsset(getAssets(), "أبو راضى عم الناس كلهم.ttf");
                break;
            case "الخطوط اليدوية (146)":
                tf = Typeface.createFromAsset(getAssets(), "الخطوط اليدوية (146).ttf");
                break;
            case "الخطوط اليدوية (147)":
                tf = Typeface.createFromAsset(getAssets(), "الخطوط اليدوية (147).ttf");
                break;
        }
        txtWords.setTypeface(tf);

        SizeBold();
        SizeItalic();
        SizeBold();
        SizeItalic();
    }

    protected void RadioBTN() {
        if (RadioLtR.isChecked())
            txtWords.setGravity(Gravity.LEFT);
        else if (RadioRtL.isChecked())
            txtWords.setGravity(Gravity.RIGHT);
        else
            txtWords.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void NewFile() {
        txtFileName.setText("");
        txtWords.setText("");
        txtSize.setText("24");
        txtWords.setTextSize(24);

        txtWords.setTypeface(tf, Typeface.NORMAL);
        btnBold.setTypeface(tf, Typeface.NORMAL);
        btnBold.setBackground(getResources().getDrawable(R.drawable.btn1));
        chBold = false;

        btnItalic.setTypeface(tf, Typeface.NORMAL);
        btnItalic.setBackground(getResources().getDrawable(R.drawable.btn1));
        chItalic = false;

        txtWords.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        btnULine.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        btnULine.setBackground(getResources().getDrawable(R.drawable.btn1));
        chULine = false;

        tf = Typeface.SANS_SERIF;

        SFont.setSelection(0);
        SColor.setSelection(0);

        txtWords.setTextColor(getResources().getColor(R.color.Black));
        RadioLtR.setChecked(false);
        RadioRtL.setChecked(false);
        RadioCenter.setChecked(false);
        txtWords.setGravity(Gravity.NO_GRAVITY);
        txtWords.requestFocus();


    }

    protected void SaveFile() {
        try {
        if ("".equals(txtSearch.getText().toString().trim())) {
            Toast.makeText(this, "ERROR...\nPlease enter Name or Path of file !", Toast.LENGTH_LONG).show();
        } else {

//            String dirPath = "/data/data/com.testandroid" + "/Red NotePad";
//            File f = new File(dirPath);
//            f.mkdir();
//
                FileOutputStream fos = openFileOutput(txtSearch.getText() + ".txt", Context.MODE_PRIVATE);

                PrintWriter pw = new PrintWriter(fos);
                pw.write(txtWords.getText().toString());
                pw.close();

                FileOutputStream fos2 = openFileOutput(txtSearch.getText() + "Set.txt", Context.MODE_PRIVATE);

                PrintWriter pw2 = new PrintWriter(fos2);
                pw2.write(
                        txtFileName.getText().toString() + "\n"
                                + txtSize.getText().toString() + "\n"
                                + chBold + "\n"
                                + chItalic + "\n"
                                + chULine + "\n"
                                + SFont.getSelectedItem().toString() + "\n"
                                + SColor.getSelectedItem().toString() + "\n"
                                + RadioLtR.isChecked() + "\n"
                                + RadioCenter.isChecked() + "\n"
                                + RadioRtL.isChecked()
                );
                pw2.close();
                Toast.makeText(this, "File is Saved", Toast.LENGTH_SHORT).show();

        }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void OpenFile() {
        try {
            FileInputStream fis = openFileInput(txtSearch.getText() + ".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String strData = "";
            String Line = "";
            while ((Line = br.readLine()) != null) {
                strData+=Line + "\n";
            }

            fis = openFileInput(txtSearch.getText() + "Set.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            String[] strSetting = new String[10];
            int x =0;
            while((Line = br.readLine()) != null)
            {
                strSetting[x] = Line;
                x++;
            }

            fis.close();
            isr.close();
            br.close();

            txtWords.setText(strData);

            txtFileName.setText(strSetting[0]);
            txtSize.setText(strSetting[1]);
            SFont.setSelection(((ArrayAdapter<String>) SFont.getAdapter()).getPosition(strSetting[5]));
            SColor.setSelection(((ArrayAdapter<String>) SColor.getAdapter()).getPosition(strSetting[6]));
            if("true".equals(strSetting[2])) {
                txtWords.setTypeface(txtWords.getTypeface(), Typeface.BOLD);
                btnBold.setTypeface(txtWords.getTypeface(), Typeface.BOLD);
                btnBold.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            }
            if("true".equals(strSetting[3])) {
                txtWords.setTypeface(tf, Typeface.ITALIC);
                btnItalic.setTypeface(tf, Typeface.ITALIC);
                btnItalic.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            }
            if("true".equals(strSetting[4])) {
                txtWords.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                btnULine.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                btnULine.setBackground(getResources().getDrawable(R.drawable.btn1_press));
            }

            if ("true".equals(strSetting[7]))
                RadioLtR.setChecked(true);
            else if ("true".equals(strSetting[8]))
                RadioCenter.setChecked(true);
            else
                RadioRtL.setChecked(true);



            Toast.makeText(this, "File is Opened", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}