package com.zhanghong.recyclerviewlist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView contactList;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private MyCustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letterView = (LetterView) findViewById(R.id.letter_view);
        contactList = (RecyclerView) findViewById(R.id.contact_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactList.setLayoutManager(layoutManager);
        adapter = new MyCustomerAdapter(this, letterView, getContact());
        contactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);
        adapter.setCallback(new MyCustomerAdapter.OnClickCallback() {
            @Override
            public void onItemCallback(Contact contact, int position) {
                if (contact.getmType() != MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
                    adapter.AddHeaderInfo(contact);
                } else {
                    adapter.DeleteHeaderInfo(position);
                }

            }
        });
        letterView.setCharaterListener(new CharacterClickListener() {
            @Override
            public void clickCharacter(String str) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(str), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }

    private String[] getData() {
        String[] contactNames = new String[]{"张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清",
                "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智"};
        return contactNames;
    }

    private ArrayList<Contact> getContact() {
        ArrayList<Contact> list = new ArrayList<>();
        list.add(new Contact("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("郭靖", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("黄蓉", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("黄老邪", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("赵敏", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("张三丰", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("郭靖", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("黄老邪", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
        list.add(new Contact("赵敏", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("123", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("天山童姥", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("任我行", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("于万亭", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("陈家洛", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("$6", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("穆人清", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("陈圆圆", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("郭芙", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("郭襄", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("穆念慈", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("东方不败", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("梅超风", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("林平之", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("林远图", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("灭绝师太", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("段誉", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        list.add(new Contact("鸠摩智", MyCustomerAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        return list;
    }
}
