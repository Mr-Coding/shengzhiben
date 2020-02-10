package com.frank.shengziben.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.frank.shengziben.MyAdapter;
import com.frank.shengziben.R;
import com.frank.shengziben.Tools;
import com.frank.shengziben.data.ChineseWord;
import com.frank.shengziben.data.ChineseWordDao;
import com.frank.shengziben.data.ChineseWordDatabase;
import com.frank.shengziben.databinding.ActivityMainBinding;
import com.rnkrsoft.bopomofo4j.Bopomofo4j;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private ChineseWord         checkWord;
    private ChineseWordDao      dao;
    private ChineseWordDatabase database;
    private MyAdapter           adapter;
    private ActivityMainBinding binding;
    private Activity            activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        // 数据
        database = Room.databaseBuilder(this,ChineseWordDatabase.class,"chineseword_database")
                .allowMainThreadQueries().build();
        dao = database.getChineseWordDao();

        // Adapter
        adapter = new MyAdapter(dao.getAllChineseWord());
        adapter.setOnLongClickListener(new MyAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(ChineseWord word) {
                Tools.VIBRATE(getApplicationContext());
                findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                binding.deletePanel.setVisibility(View.VISIBLE);
                checkWord = word;
                binding.tvNotice.setText(word.getWord());
            }
        });

        // RecyclerView
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);

        // 删除按钮
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deletePanel.setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                dao.deleteChineseWord(checkWord);
                adapter.delete();
            }
        });

        // 复制按钮
        binding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deletePanel.setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(checkWord.getWord()+" "+checkWord.getPinyin());
                Toast.makeText(v.getContext(),"复制成功！",Toast.LENGTH_SHORT).show();
            }
        });

        // 解释按钮
        binding.btnExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deletePanel.setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);

                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                Uri uri = Uri.parse("https://www.zdic.net/hans/"+checkWord.getWord());
                intent.setData(uri);
                startActivity(intent);
            }
        });

        // 悬浮按钮
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                binding.scrollview.setVisibility(View.VISIBLE);
                binding.inputPanel.setVisibility(View.VISIBLE);
                Tools.showInput(activity,binding.editText);
            }
        });
        binding.floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Tools.VIBRATE(getApplicationContext());
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                return false;
            }
        });

        // 确定存入生字的按钮
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = binding.editText.getText().toString();
                if (word.equals("")){
                    Toast.makeText(v.getContext(),"请输入生字！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //汉语句子->声母音调拼音
                String pinyin = Bopomofo4j.pinyin(word,0, false, false, " ");
                ChineseWord chineseWord = new ChineseWord(word,pinyin);
                dao.insetChineseWord(chineseWord);
                adapter.add(chineseWord);
                binding.recyclerView.scrollToPosition(0);
                binding.editText.setText("");
                binding.inputPanel.setVisibility(View.GONE);
                binding.scrollview.setVisibility(View.GONE);
                Tools.hideInput(activity);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
            }
        });

        binding.inputPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                binding.scrollview.setVisibility(View.GONE);
                Tools.hideInput(activity);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
            }
        });

        binding.deletePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (binding.deletePanel.getVisibility() == View.VISIBLE){
            binding.deletePanel.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
