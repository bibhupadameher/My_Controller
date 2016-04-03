package bluetooth.my_controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends Activity{
 EditText ET_NAME,ET_PASS;
 String login_name,login_pass;
 Button b1,b2;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  ET_NAME = (EditText)findViewById(R.id.user_name);
  ET_PASS = (EditText)findViewById(R.id.user_pass);

  b1=(Button)findViewById(R.id.b1);
  b2=(Button)findViewById(R.id.b2);
  b1.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    login_name = ET_NAME.getText().toString();
    login_pass = ET_PASS.getText().toString();
    String method = "login";
    BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
    backgroundTask.execute(method,login_name,login_pass);
   }
  });
  b2.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    startActivity(new Intent(MainActivity.this,Register.class));
   }
  });
 }
/* public void userReg(View view)
 {
  startActivity(new Intent(this,Register.class));
 }
 public void userLogin(View view)
 {
  login_name = ET_NAME.getText().toString();
  login_pass = ET_PASS.getText().toString();
  String method = "login";
  BackgroundTask backgroundTask = new BackgroundTask(this);
  backgroundTask.execute(method,login_name,login_pass);
 }
*/
}
