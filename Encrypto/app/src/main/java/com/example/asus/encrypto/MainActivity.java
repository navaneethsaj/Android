package com.example.asus.encrypto;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText,editText2;
    String rawdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.result);
        editText=(EditText)findViewById(R.id.text);
        editText2=(EditText)findViewById(R.id.edit_query);
    }
    public void encrypt(View view){
        Random random=new Random();
        rawdata=editText.getText().toString();
        int key=random.nextInt(26);
        //rawdata="navaneeth";
        String strkey=Integer.toString(key);
        editText2.setText(strkey);
        String normalized=Encrypt.normalize(rawdata);
        String oblified=Encrypt.obify(normalized);
        String ceaserified=Encrypt.ceaser(oblified,key);
        String grouped=Encrypt.group(ceaserified,3);
        String encrypted=grouped;
        textView.setText(encrypted);
    }
    public void decrypt(View view){
        String data=editText.getText().toString();
        editText2.getText().toString();
        String strkey=editText2.getText().toString();
        int key=Integer.parseInt(strkey);
        String degrp=Decrypt.degroup(data);
        String dcsr=Decrypt.deceaser(degrp,key);
        String deob=Decrypt.deobify(dcsr);
        String rawdata=deob;
        textView.setText(rawdata);
    }
}
