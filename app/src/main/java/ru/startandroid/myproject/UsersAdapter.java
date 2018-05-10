package ru.startandroid.myproject;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UsersAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<UsersResponce> objects;

    UsersAdapter(Context context, ArrayList<UsersResponce> usersResponces) {
        ctx = context;
        objects = usersResponces;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_users_list, parent, false);
        }

        UsersResponce p = getUsersResponce(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tv_id)).setText(p.id);
        ((TextView) view.findViewById(R.id.tv_name)).setText(p.name);
        return view;
    }

    // товар по позиции
    UsersResponce getUsersResponce(int position) {
        return ((UsersResponce) getItem(position));
    }



}
