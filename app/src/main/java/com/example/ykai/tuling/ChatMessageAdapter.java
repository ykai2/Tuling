package com.example.ykai.tuling;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ykai.tuling.bean.ChatMessage;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ykai on 2015/7/22.
 */
public class ChatMessageAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<ChatMessage>mDatas;
    public ChatMessageAdapter(Context context,List<ChatMessage>mDatas){
        mInflater=LayoutInflater.from(context);
        this.mDatas=mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getViewTypeCount(){
        return 2;
    }
    @Override
    public int getItemViewType(int i){
        ChatMessage chatMessage=mDatas.get(i);
        if(chatMessage.getType()== ChatMessage.Type.INCOMING){
            return 0;

        }
        return 1;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage=mDatas.get(i);
        ViewHolder viewHolder=null;
        if(view==null)
        {
            if(getItemViewType(i)==0) {
                view = mInflater.inflate(R.layout.item_from_msg, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.mData = (TextView) view.findViewById(R.id.id_from_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.id_from_msg_info);
            }
            else {
                view = mInflater.inflate(R.layout.item_to_msg, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.mData = (TextView) view.findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.id_to_msg_info);

            }
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mData.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());



        return view;
    }
    private final class ViewHolder{
        TextView mData;
        TextView mMsg;
    }
}

