package com.zhanghong.recyclerviewlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pinyin.Pinyin;

/**
 * Created by ZhangHong on 2017/3/22.
 */

public class MyCustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<Contact> mContactNames;// 联系人名称字符串数组
    private List<String> mContactList;// 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<Contact> headerList; // 记录已添加了的用户
    private List<String> characterList; // 字母List
    private LetterView letterView;
    private OnClickCallback callback;

    public void setCallback(OnClickCallback callback) {
        this.callback = callback;
    }

    public enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public MyCustomerAdapter(Context mContext, LetterView letterView, ArrayList<Contact> mContactNames) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContactNames = mContactNames;
        this.letterView = letterView;
        handleContact();
    }

    private void handleContact() {
        headerList = new ArrayList<>();
        mContactList = new ArrayList<>();
        Map<String, Contact> map = new HashMap<>();
        for (int i = 0; i < mContactNames.size(); i++) {
            if (mContactNames.get(i).getmType() == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {

            } else {
                String pinyin = transformPinYin(mContactNames.get(i).getmName());
                map.put(pinyin, mContactNames.get(i));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());
        resultList = new ArrayList<>();
        characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            if (mContactNames.get(i).getmType() == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
                headerList.add(map.get(name));
                resultList.add(new Contact(map.get(name).getmName(), ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
            }
        }
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) {
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            resultList.add(new Contact(map.get(name).getmName(), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
        letterView.upDataLetter(characterList);
    }


    public String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()) {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        }
        return null;
    }

    public void AddHeaderInfo(Contact contact) {
        if (!headerList.contains(contact)) {
            headerList.add(contact);
            resultList.add(0, new Contact(contact.getmName(), ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
            this.notifyDataSetChanged();
        }

    }

    public void DeleteHeaderInfo(int position) {
        if (headerList != null) {
            headerList.remove(position);
        }
        resultList.remove(position);
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
            ((ContactHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callback) {
                        callback.onItemCallback(resultList.get(position), position);
                    }
                }
            });
        }
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        LinearLayout layout;

        ContactHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.item_layout);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
        }
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }
        return -1; // -1不会滑动
    }


    public interface OnClickCallback {
        void onItemCallback(Contact contact, int position);
    }
}
