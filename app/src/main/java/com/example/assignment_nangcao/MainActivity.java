package com.example.assignment_nangcao;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.assignment_nangcao.Fragment.BillFragment;
import com.example.assignment_nangcao.Fragment.BookFragment;
import com.example.assignment_nangcao.Fragment.SellerFragment;
import com.example.assignment_nangcao.Fragment.StatisticalFragment;
import com.example.assignment_nangcao.Fragment.TypeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ImageView img_avt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        drawer = findViewById(R.id.drawer_layout);

        //
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        img_avt = (ImageView) header.findViewById(R.id.img_avt);
        img_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TypeFragment()).commit();
            navigationView.setCheckedItem(R.id.two);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.one:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserFragment()).commit();
//                break;
            case R.id.two:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TypeFragment()).commit();
                break;
            case R.id.three:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookFragment()).commit();
                break;
            case R.id.four:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BillFragment()).commit();
                break;
            case R.id.five:
//                final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Signing out...");
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                }, 2000);
//
//                Toast.makeText(MainActivity.this, "Log out of your account", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellerFragment()).commit();

                break;
            case R.id.six:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticalFragment()).commit();
                break;
//            case R.id.seven:
//                Intent endMain = new Intent(Intent.ACTION_MAIN);
//                endMain.addCategory(Intent.CATEGORY_HOME);
//                startActivity(endMain);
//                finish();
//                Toast.makeText(MainActivity.this, "See you again", Toast.LENGTH_SHORT).show();
//                break;
//
//            default:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserFragment()).commit();
        }
        item.setCheckable(true);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
