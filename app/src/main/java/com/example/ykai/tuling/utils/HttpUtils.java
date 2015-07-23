package com.example.ykai.tuling.utils;

import android.util.Log;

import com.example.ykai.tuling.bean.ChatMessage;
import com.example.ykai.tuling.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by ykai on 2015/7/22.
 */
public class HttpUtils {
    private static final String URL="http://www.tuling123.com/openapi/api";
    private static final String API_KEY="9e33acb54b76ba5d81e84f155a093271";

    /**
     * 发送一个消息，得到消息
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg){
        ChatMessage chatMessage=new ChatMessage();
        String jsonRes=doGet(msg);

        Gson gson=new Gson();
        Result result=null;
        try{
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());


        }catch (JsonSyntaxException e){
            chatMessage.setMsg("服务器繁忙");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);

        return chatMessage;
    }

    public static String doGet(String msg){
        String result="";
        HttpURLConnection conn=null;
        String url=setParams(msg);
        InputStream inputStream=null;
        ByteArrayOutputStream baos=null;

        try {
                java.net.URL urlnet=new  java.net.URL(url);
                conn=(HttpURLConnection) urlnet.openConnection();
                conn.setReadTimeout(5 * 1000);
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
            if(conn.getResponseCode()==200){
                baos = new ByteArrayOutputStream();

                inputStream=conn.getInputStream();
               // Log.e("aaa", "test2:" + result + msg);
                int len=-1;
                byte[] buf=new byte[128];

                while ((len= inputStream.read(buf))!=-1)
                {
                    baos.write(buf, 0, len);
                }
                baos.flush();

                result= new String(baos.toByteArray());
            }else {

            }



            }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

                try {
                    if(baos!=null) {
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    if(inputStream!=null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
conn.disconnect();

        }


        return result;

    }

    private static String setParams(String msg) {
        String url= "";
        try {
            url = URL+"?key="+API_KEY+"&info="+ URLEncoder.encode(msg,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;

    }

}
