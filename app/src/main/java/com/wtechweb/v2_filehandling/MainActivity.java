package com.wtechweb.v2_filehandling;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etSurname;
    TextView tvResult;
    ArrayList<Person> personArrayList;
    ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        tvResult = findViewById(R.id.tvResult);
        ivImage = findViewById(R.id.ivImage);
        personArrayList = new ArrayList<>();
        loadData();
    }

    public void addData(View v)
    {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();

        if(name.isEmpty())
        {
            etName.setError("Error: This field can't be empty");
        }
        else if(surname.isEmpty())
        {
            etSurname.setError("Error: This field can't be empty");
        }
        else
        {
            personArrayList.add(new Person(name, surname));
            Toast.makeText(this, "Data added Successfully", Toast.LENGTH_SHORT).show();
            clear();
            showDataInTextView();
        }

        ivImage.setImageResource(R.mipmap.usb);
    }

    public void showDataInTextView()
    {
        String text="";
        for (Person p:personArrayList)
        {
            text += p.getName()+" "+p.getSurname()+"\n";
        }

        tvResult.setText(text);
    }

    public void clear()
    {
        etName.setText("");
        etSurname.setText("");
    }

    public void saveData(View v)
    {
        try {
            FileOutputStream file = openFileOutput("Data.txt", MODE_PRIVATE);
            OutputStreamWriter writeDataInFile = new OutputStreamWriter(file);
            for (Person p:personArrayList)
            {
                writeDataInFile.write(p.getName()+","+p.getSurname()+"\n");
            }
            writeDataInFile.flush();
            writeDataInFile.close();

        }catch(IOException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData()
    {
        personArrayList.clear();
        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String line;
        if(file.exists())
        {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));
                while((line=reader.readLine())!=null)
                {
                    StringTokenizer tokens = new StringTokenizer(line, ",");
                    Person p = new Person(tokens.nextToken(), tokens.nextToken());
                    personArrayList.add(p);
                }

                showDataInTextView();

            }catch (IOException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Data.txt not exists", Toast.LENGTH_SHORT).show();
        }
    }
}