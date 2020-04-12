package com.seanutf.android.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.custom.CustomViewActivity;
import com.seanutf.android.dagger.DaggerActivity;
import com.seanutf.android.databinding.ActivityMainBinding;
import com.seanutf.android.databinding.ItemMainListBinding;
import com.seanutf.cmmonui.arch.BaseActivity;

public class MainActivity extends BaseActivity {

    ActivityMainBinding activityBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityBind.getRoot());
        initView();
    }

    private void initView() {
        activityBind.rvMain.setLayoutManager(new LinearLayoutManager(this));

        activityBind.rvMain.setAdapter(new MainAdapter());
        activityBind.tvText.setText("这是一个测试文案");
        activityBind.tvTitle.setText("这是一个测试标题");
        activityBind.tvContent.setText("这是一个测试内容");

    }

    class MainAdapter extends RecyclerView.Adapter{


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMainListBinding itemMainListBinding = ItemMainListBinding.inflate(getLayoutInflater(),parent, false);
            return new ViewItem(itemMainListBinding);
        }



        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

            switch (position){
                case 0:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("自定义View");
                    break;
                case 1:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("架构");
                    break;
                case 2:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("算法");
                    break;
                case 3:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("Kotlin");
                    break;
                case 4:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("系统源码");
                    break;
                case 5:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("Flutter");
                    break;
                case 6:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("组件化");
                    break;
                case 7:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("设计模式");
                    break;
                case 8:
                    ((ViewItem)holder).itemMainListBinding.tvText.setText("依赖注入Dagger");
                    break;
            }

            ((ViewItem)holder).itemMainListBinding.tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            gotoCustom();
                            break;
                        case 1:
                            gotoArch();
                            break;
                        case 2:
                            gotoAlgorithm();
                            break;
                        case 3:
                            gotoKotlin();
                            break;
                        case 4:
                            gotoSystem();
                            break;
                        case 5:
                            gotoFlutter();
                            break;
                        case 6:
                            gotoComponent();
                            break;
                        case 7:
                            gotoDesignPatterns();
                            break;
                        case 8:
                            gotoDagger();
                            break;
                    }
                }
            });

        }



        @Override
        public int getItemCount() {
            return 9;
        }
    }

    private void gotoDagger() {
        DaggerActivity.startActivity(this);
    }

    private void gotoDesignPatterns() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoComponent() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoFlutter() {








        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoSystem() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoKotlin() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoAlgorithm() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    private void gotoCustom() {
        CustomViewActivity.startActivity(this);
    }

    private void gotoArch() {
        //todo
        Toast.makeText(this, "未实现！", Toast.LENGTH_SHORT).show();
    }

    class ViewItem extends RecyclerView.ViewHolder {
        public ItemMainListBinding itemMainListBinding;
        public ViewItem(ItemMainListBinding itemMainListBinding) {
            super(itemMainListBinding.getRoot());
            this.itemMainListBinding = itemMainListBinding;
        }
    }
}
