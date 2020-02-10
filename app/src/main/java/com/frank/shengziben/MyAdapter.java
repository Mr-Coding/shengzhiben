package com.frank.shengziben;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frank.shengziben.data.ChineseWord;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder1> {
    private String TAG = "MyAdapter";
    private List<ChineseWord> words;
    private int mainPosition;
    private OnLongClickListener listener;

    public interface OnLongClickListener{
        void onLongClick(ChineseWord word);
    }

    public void setOnLongClickListener(OnLongClickListener listener){
        this.listener = listener;
    }

    public MyAdapter(List<ChineseWord> words) {
        Collections.sort(words);
        this.words = words;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, final int position) {
        ChineseWord word = words.get(position);
        holder.tv_word.setText(word.getWord());
        holder.tv_pinyin.setText(word.getPinyin());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ChineseWord word1 = words.get(position);
                mainPosition = position;
                listener.onLongClick(word1);
                return false;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // 添加数据
    public void add(ChineseWord word){
        words.add(0,word);
//        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    // 删除数据
    public void delete(){
        words.remove(mainPosition);
//        notifyItemRemoved(mainPosition);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView tv_word;
        TextView tv_pinyin;
        MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.item_word);
            tv_pinyin= itemView.findViewById(R.id.item_pinyin);
        }
    }
}
