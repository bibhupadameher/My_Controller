package bluetooth.my_controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by SM ROCKS on 4/1/2016.
 */
public class Register extends Activity{
    String tt,tb,tc;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        final EditText t1,t2,t3;
        t1=(EditText)findViewById(R.id.name);
        t2=(EditText)findViewById(R.id.new_user_name);
        t3=(EditText)findViewById(R.id.new_user_pass);
        Button b=(Button)findViewById(R.id.button9);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt = t1.getText().toString();
                tb = t2.getText().toString();
                tc = t3.getText().toString();
                String method = "register";
                BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                backgroundTask.execute(method, tt, tb, tc);
            }
        });


    }
    }

