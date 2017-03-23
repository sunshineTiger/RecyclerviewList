package com.zhanghong.recyclerviewlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView contactList;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private MyCustomerAdapter adapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letterView = (LetterView) findViewById(R.id.letter_view);
        contactList = (RecyclerView) findViewById(R.id.contact_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactList.setLayoutManager(layoutManager);
        adapter = new MyCustomerAdapter(this, letterView, getContact());
        textView = (TextView) findViewById(R.id.showText);
//        contactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);
        adapter.setCallback(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                switch (view.getId()) {
                    case R.id.mark:
                        if (null != o && o instanceof UserBean) {
                            UserBean userBean = (UserBean) o;
                            if (userBean.getmType() != MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
                                adapter.AddHeaderInfo(userBean, position);
                            } else {
                                adapter.DeleteHeaderInfo(userBean, position);
                            }
                        }
                        break;
                    case R.id.item_layout:
                        Toast.makeText(MainActivity.this, "点击了第" + position + "按钮", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        letterView.setCharaterListener(new CharacterClickListener() {
            @Override
            public void clickCharacter(String str) {
                textView.setText(str);
                if (str.equals("★")) {
                    layoutManager.scrollToPositionWithOffset(0, 0);
                } else {
                    layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(str), 0);
                }

            }
        });
    }

    private String[] getData() {
        String[] contactNames = new String[]{"张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清",
                "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智"};
        return contactNames;
    }

    private ArrayList<UserBean> getContact() {
        ArrayList<UserBean> list = new ArrayList<>();
        list.add(new UserBean("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("郭靖", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("黄蓉", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("黄老邪", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("赵敏", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("郭靖", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("黄老邪", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new UserBean("赵敏", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("123", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("天山童姥", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("任我行", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("于万亭", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("陈家洛", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("$6", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("穆人清", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("陈圆圆", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("郭芙", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("郭襄", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("穆念慈", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("东方不败", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("梅超风", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("林平之", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("林远图", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("灭绝师太", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("段誉", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new UserBean("鸠摩智", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        return list;
    }
}
