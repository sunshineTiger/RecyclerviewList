package com.zhanghong.recyclerviewlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    private ArrayList<UserBean> mUserBeanNames;// 联系人名称字符串数组
    private List<String> mContactList;// 联系人名称List（转换成拼音）
    private List<UserBean> resultList; // 最终结果（包含分组的字母）
    private Map<String, UserBean> mapHeaderList; // 记录已添加了的用户
    private List<String> characterList; // 字母List
    private LetterView letterView;
    private OnItemClickListener callback;

    public void setCallback(OnItemClickListener callback) {
        this.callback = callback;
    }

    public enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public MyCustomerAdapter(Context mContext, LetterView letterView, ArrayList<UserBean> mUserBeanNames) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mUserBeanNames = mUserBeanNames;
        this.letterView = letterView;
        handleContact();
    }

    private void handleContact() {
        mapHeaderList = new HashMap<>();
        mContactList = new ArrayList<>();
        Map<String, UserBean> map = new HashMap<>();
        for (int i = 0; i < mUserBeanNames.size(); i++) {
            if (mUserBeanNames.get(i).getmType() == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {

            } else {
                String pinyin = transformPinYin(mUserBeanNames.get(i).getmName());
                map.put(pinyin, mUserBeanNames.get(i));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());
        resultList = new ArrayList<>();
        characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            if (mUserBeanNames.get(i).getmType() == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
                mapHeaderList.put(map.get(name).getmName(), map.get(name));
                resultList.add(new UserBean(map.get(name).getmName(), ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
            }
        }
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) {
                    characterList.add(character);
                    resultList.add(new UserBean(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new UserBean("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            resultList.add(new UserBean(map.get(name).getmName(), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
        if (mapHeaderList.size() > 0) {
            characterList.add(0, "★");
            resultList.add(0, new UserBean("特别关注", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
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
            return new HeaderContactHolder(mLayoutInflater.inflate(R.layout.item_header_contact, parent, false));
        }
        return null;
    }

    /**
     * 添加顶部条目
     *
     * @param userBean
     * @param position
     */
    public void AddHeaderInfo(UserBean userBean, int position) {
        if (null != mapHeaderList) {
            if (mapHeaderList.size() == 0) {
                mapHeaderList.put(userBean.getmName(), userBean);
                resultList.add(0, new UserBean(userBean.getmName(), ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
                resultList.add(0, new UserBean("特别关注", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                this.notifyDataSetChanged();
            } else {
                if (!mapHeaderList.containsKey(userBean.getmName())) {
                    if (resultList.get(0).getmName().equals("特别关注")) {
                        resultList.remove(0);
                    }
                    mapHeaderList.put(userBean.getmName(), userBean);
                    resultList.add(0, new UserBean(userBean.getmName(), ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()));
                    resultList.add(0, new UserBean("特别关注", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    this.notifyDataSetChanged();
                }
            }
            //更新右边显示条目
            if (mapHeaderList.size() == 1) {
                characterList.add(0, "★");
                letterView.addStart(characterList);
            }
        }
    }

    /**
     * 删除顶部条目
     *
     * @param userBean
     * @param position
     */
    public void DeleteHeaderInfo(UserBean userBean, int position) {
        if (mapHeaderList != null) {
            mapHeaderList.remove(userBean.getmName());
        }
        resultList.remove(position);
        if (null != mapHeaderList && mapHeaderList.size() == 0) {
            characterList.remove(0);
            letterView.removeStart(characterList);
            resultList.remove(0);
        }
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            if (null != mapHeaderList && mapHeaderList.containsKey(resultList.get(position).getmName())) {
                ((ContactHolder) holder).mark.setText("★");
                ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
                ((ContactHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != callback) {
                            callback.onItemClick(((ContactHolder) holder).layout, resultList.get(position), position);
                        }
                    }
                });
                ((ContactHolder) holder).mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != callback) {
                            callback.onItemClick(((ContactHolder) holder).layout, resultList.get(position), position);
                        }
                    }
                });
            } else {
                ((ContactHolder) holder).mark.setText("☆");
                ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
                ((ContactHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != callback) {
                            callback.onItemClick(((ContactHolder) holder).layout, resultList.get(position), position);
                        }
                    }
                });
                ((ContactHolder) holder).mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != callback) {
                            callback.onItemClick(((ContactHolder) holder).mark, resultList.get(position), position);
                        }
                    }
                });
            }

        } else if (holder instanceof HeaderContactHolder) {
            ((HeaderContactHolder) holder).mark.setText("★");
            ((HeaderContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
            ((HeaderContactHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callback) {
                        if (null != callback) {
                            callback.onItemClick(((HeaderContactHolder) holder).layout, resultList.get(position), position);
                        }
                    }
                }
            });
            ((HeaderContactHolder) holder).mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callback) {
                        callback.onItemClick(((HeaderContactHolder) holder).mark, resultList.get(position), position);
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
        RelativeLayout layout;
        TextView mark;

        ContactHolder(View view) {
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.item_layout);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
            mark = (TextView) view.findViewById(R.id.mark);
        }
    }

    public class HeaderContactHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        RelativeLayout layout;
        TextView mark;

        HeaderContactHolder(View view) {
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.item_layout);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
            mark = (TextView) view.findViewById(R.id.mark);
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
}
