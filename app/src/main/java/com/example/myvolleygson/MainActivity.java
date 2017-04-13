package com.example.myvolleygson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.APIUrl;
import com.example.model.InfoEntity;
import com.example.volley.APIHttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.myvolleygson.R.id.postSetCookie;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final int path_info_post_what = 1;
    private static final int path_info_get_what = 2;
    private static final int path_info_post_cookie_what = 3;
    private static final int path_info_put_what = 4;
    private static final int path_info_delete_cookie_what = 5;
    private static final int path_down_image_what = 6;

    private RequestQueue requestQueue;
    private List<InfoEntity> indolist = new ArrayList<InfoEntity>();
    private ListView listview;

    private TextView mTv;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.mTv);
        imageView = (ImageView) findViewById(R.id.imageView);

        requestQueue = Volley.newRequestQueue(this);


    }

    public void getCookieHttp() {
        APIHttp.getWithCookit(APIUrl.path_info_get_cookie, handler,path_info_post_cookie_what, requestQueue);
    }

    public void putCookieHttp(){

        View view = getLayoutInflater().inflate(R.layout.layout_edit_input_dialog,null);
        final EditText editText = (EditText) view.findViewById(R.id.input);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请输入要修改的昵称")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String niname = editText.getText().toString();
                        HashMap<String, String> values = new HashMap<String, String>();
                        values.put("first_name", niname);
                        APIHttp.putWithCookit(APIUrl.path_info_put, handler,path_info_put_what, requestQueue,values);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }
    public void deleteCookieHttp(){
        APIHttp.deleteWithCookit(APIUrl.path_info_delete_cookie, handler,path_info_delete_cookie_what, requestQueue);
    }

    public void imageHttp(){
        APIHttp.Image(requestQueue,APIUrl.path_down_image, handler,path_down_image_what,50,50, Bitmap.Config.ARGB_8888);
    }

    public void postSetCookieHttp(){
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("username", "Android");
        values.put("password", "1234");
        values.put("imei", "00000000");
        APIHttp.postSetCookie(APIUrl.path_login_post, handler,path_info_post_what, requestQueue,values);
    }

    public void getHttp(){
        APIHttp.get(APIUrl.path_info_get, handler,path_info_get_what, requestQueue);
    }

    public void onClick(View view){
        switch (view.getId()){
            case postSetCookie:
                postSetCookieHttp();
                break;
            case R.id.get:
                getHttp();
                break;
            case R.id.getCookie:
                getCookieHttp();
                break;
            case R.id.post:
//                APIHttp.post();
                break;
            case R.id.postCookie:
//                APIHttp.postCookie();
                break;
            case R.id.put:
                putCookieHttp();
                break;
            case R.id.deleteCookie:
                deleteCookieHttp();
                break;
            case R.id.image:
                imageHttp();
                break;
        }

    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case path_info_post_what:
                    String postSetCookie = (String) msg.obj;
                    mTv.setText("postSetCookie: "+postSetCookie);

//                    InfoEntity InfoEn = new Gson().fromJson(s, new InfoEntity().getClass());
//                    List<InfoEntity> list = new ArrayList<InfoEntity>();
//                    list.add(InfoEn);
//                    listview.setAdapter(new Adapter(list, getApplicationContext(), R.layout.item));
                    break;
                case path_info_get_what:
                    String get = (String) msg.obj;
                    mTv.setText("get: "+get);
                    break;
                case path_info_post_cookie_what:
                    String getCookie = (String) msg.obj;
                    mTv.setText("getCookie: "+getCookie);
                    break;
                case path_info_put_what:
                    String put = (String) msg.obj;
                    mTv.setText("put: "+put);
                    break;
                case path_info_delete_cookie_what:
                    String delete = (String) msg.obj;
                    mTv.setText("delete: "+delete);
                    break;
                case path_down_image_what:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

}
