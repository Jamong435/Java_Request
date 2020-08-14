package com.kim9212.ex76request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etMsg;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etMsg = findViewById(R.id.et_msg);
        tv = findViewById(R.id.tv);


        //쉬는시간에 볼께용
    }

    public void clickGet(View view) {

        new Thread() {
            @Override
            public void run() {


                String name = etName.getText().toString();
                String msg = etMsg.getText().toString();


                String serverUrl = "http://toki666.dothome.co.kr/Android/getTest.php";
                try {
                    name = URLEncoder.encode(name, "utf-8");
                    msg = URLEncoder.encode(msg, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //데이터를 포함한 최종 요청 url
                String getUrl = serverUrl + "?name=" + name + "&msg=" + msg;

                try {
                    //getUrl주소의 서버와 연결하는 무지개로드를 만들어주는 해임달
                    URL url = new URL(getUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    OutputStream os = connection.getOutputStream();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) break;

                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

    public void clickPost(View view) {

        new Thread() {
            @Override
            public void run() {

                String name = etName.getText().toString();
                String msg = etMsg.getText().toString();

                //서버주소
                String serverUrl = "http://toki666.dothome.co.kr/Android/postTest.php";

                //POST방식이므로 OutputStream을 만들어서 데이터를 write해야만 함

                try {
                    URL url = new URL(serverUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    //보낼 데이터
                    String query = "name=" + name + "&msg=" + msg;

                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(os);

                    writer.write(query, 0, query.length());
                    writer.flush();
                    writer.close();

                    //postTest.php에서 echo된 결과 데이터를 받아오기
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) break;

                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public void clickUpload(View view) {
        //서버의 Database에 저장하기..
        new Thread() {
            @Override
            public void run() {

                String name = etName.getText().toString();
                String msg = etMsg.getText().toString();

                String serverUrl = "http://toki666.dothome.co.kr/Android/insertDB.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    String query = "name=" + name + "&msg=" + msg;

                    OutputStream os = connection.getOutputStream();
                    os.write(query.getBytes());//String->byte[]
                    os.flush();
                    os.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) break;

                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void clickLoad(View view) {
        //서버의 Database의 정보를 읽어와서 보여주는 화면(Activity) 실행
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }
}