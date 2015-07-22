package com.example.ykai.tuling.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by ykai on 2015/7/22.
 */
public class HttpUtils {
    private static final String URL="http://www.tuling123.com/openapi/api";
    private static final String API_KEY="9e33acb54b76ba5d81e84f155a093271";
    public static String doGet(String msg){
        String result="";
        String url=setParams(msg);
        InputStream inputStream=null;
        ByteArrayOutputStream baos=null;
        try {
            java.net.URL urlnet=new URL(url);
                HttpURLConnection conn=(HttpURLConnection) urlnet.openConnection();
                conn.setReadTimeout(5 * 1000);
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                inputStream=conn.getInputStream();
                int len=-1;
                byte[] buf=new byte[128];

                while ((len= inputStream.read(buf))!=-1)
                {
                    baos.write(buf,0,len);
                }
            baos.flush();
            result=new String(baos.toByteArray());


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

        }


        return null;

    }

    private static String setParams(String msg) {
        String url=URL+"?key="+API_KEY+"&info="+msg;
        return url;
    }

}
