package com.example.ykai.tuling;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.ykai.tuling.utils.HttpUtils;

/**
 * Created by ykai on 2015/7/22.
 */
public class TestClass extends InstrumentationTestCase {
    public void test(){
        String res="";
        res= HttpUtils.doGet("鬼故事");
        if(res!=null)
        Log.e("aaa",res);
        else
            Log.e("aaa","xxx");


    }
}
