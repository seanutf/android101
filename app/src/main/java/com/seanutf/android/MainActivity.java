package com.seanutf.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seanutf.android.custom.CustomViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        final RecyclerView rvMain = findViewById(R.id.rvMain);

        rvMain.setLayoutManager(new LinearLayoutManager(this));

        rvMain.setAdapter(new MainAdapter());
    }

    class MainAdapter extends RecyclerView.Adapter{


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_main_list, parent,false);
            return new ViewItem(view);
        }



        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

            switch (position){
                case 0:
                    ((ViewItem)holder).tvText.setText("自定义View");
                    break;
                case 1:
                    ((ViewItem)holder).tvText.setText("架构");
                    break;
                case 2:
                    ((ViewItem)holder).tvText.setText("算法");
                    break;
                case 3:
                    ((ViewItem)holder).tvText.setText("Kotlin");
                    break;
                case 4:
                    ((ViewItem)holder).tvText.setText("系统源码");
                    break;
                case 5:
                    ((ViewItem)holder).tvText.setText("Flutter");
                    break;
                case 6:
                    ((ViewItem)holder).tvText.setText("组件化");
                    break;
                case 7:
                    ((ViewItem)holder).tvText.setText("设计模式");
                    break;
            }

            ((ViewItem)holder).tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            gotoCustom();
                            break;
                        case 1:
                            //gotoArch();
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
                    }
                }
            });

        }



        @Override
        public int getItemCount() {
            return 8;
        }
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
        public TextView tvText;
        public ViewItem(@NonNull View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tvText);
        }
    }
}
