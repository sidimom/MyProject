package ru.startandroid.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Users_list extends AppCompatActivity {

    UsersAdapter usersAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Menu menu;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.getGroupId() == Menu.NONE){
                            Boolean isItemChecked = menuItem.isChecked();
                            menuItem.setChecked(!isItemChecked);
                            menu.setGroupVisible(menuItem.getItemId(), !isItemChecked);
                        }
                        return true;
                    }
                });
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                }
        );

        Intent intent = getIntent();
        String result = intent.getStringExtra("responceJSON");

        Type type = new TypeToken<List<MainMenu>>(){}.getType();
        ArrayList<MainMenu> inpList = new Gson().fromJson(result, type);

        for (int i = 0; i < inpList.size(); i++) {
            MainMenu currentItem = inpList.get(i);
            MenuItem subMenu = menu.add(Menu.NONE, currentItem.id, currentItem.sidebarOrder, currentItem.title);
            if (!(currentItem.subMenuItems == null)){
                ArrayList<SubMenuItems> subMenuItemsList = currentItem.subMenuItems;
                for (int j = 0; j < subMenuItemsList.size(); j++) {
                    SubMenuItems currentSubItem = subMenuItemsList.get(j);
                    MenuItem menuItem = menu.add(subMenu.getItemId(), currentSubItem.id, currentSubItem.sidebarOrder, currentSubItem.title);
                }
            }
            menu.setGroupVisible(subMenu.getItemId(), false);
        };




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
