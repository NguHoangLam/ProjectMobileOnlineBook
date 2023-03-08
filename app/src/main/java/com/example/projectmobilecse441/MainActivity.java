package com.example.projectmobilecse441;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.text.Layout;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbarHomePage;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listViewCategories;
    DrawerLayout drawerLayout;
    EditText edt_UserName, edt_Password;
    Button btn_Login, btn_Logout;
    DrawerLayout drawerLogin;
    TextView tv_UserNameProfile, tv_UserType, tv_TotalSpentDetails, tv_TotalRewardPoint;
    List<BookCategories> bookCategoriesList = new ArrayList<>();
    ArrayAdapter<BookCategories> categoriesArrayAdapter = null;
    int UserId = -1;
    int timesShopping = -1;
    boolean checkLogin = false;
    String address = null;
    int phone = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        ShowMainMenu();
        ShowViewFlipper();
        List<Book> books = getListData();
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new BookRecyclerViewAdapter(this, books));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        registerForContextMenu(recyclerView);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edt_UserName.getText().toString().toLowerCase();
                String password = edt_Password.getText().toString().toLowerCase();
                List<UsersAccount> usersAccountList = UsersAccountHelper.getAll(getApplicationContext());
                checkLogin = false;
                for(int i =0;i<usersAccountList.size();i++){
                    if(usersAccountList.get(i).getUsername().equalsIgnoreCase(userName)){
                        if(usersAccountList.get(i).getPassword().equalsIgnoreCase(password)){
                            drawerLogin.setVisibility(View.VISIBLE);
                            checkLogin = true;

                            UsersAccount auth = usersAccountList.get(i);
                            tv_UserNameProfile.setText(auth.getFullName());
                            int rank = auth.getAccountType();
                            if(rank==0){
                                tv_UserType.setText("Normal Member");
                            }
                            if(rank==1){
                                tv_UserType.setText("VIP Member");
                            }
                            tv_TotalSpentDetails.setText(auth.getTotalSpent()+"");
                            tv_TotalRewardPoint.setText(auth.getRewardPoint()+"");
                            UserId = auth.getId();
                            timesShopping = auth.getTimesShopping();
                            address = auth.getAddress();
                            phone = auth.getPhone();
                        }
                    }
                }
                if (checkLogin){
                    Toast.makeText(MainActivity.this,"Login Success!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Login Fail!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLogin.setVisibility(View.GONE);
                checkLogin = false;
                edt_UserName.setText("");
                edt_Password.setText("");
            }
        });






    }


    private void ShowViewFlipper() {
        List<Integer> advertisements = new ArrayList<>();
        advertisements.add(R.drawable.banner1);
        advertisements.add(R.drawable.banner2);
        advertisements.add(R.drawable.banner3);
        for (int i = 0; i < advertisements.size(); i++) {
            // create the object of ImageView
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(advertisements.get(i)); // set image in ImageView
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView); // add the created ImageView in ViewFlipper
        }
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        // set the animation type's to ViewFlipper
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        // set interval time for flipping between views
        viewFlipper.setFlipInterval(3000);
        // set auto start for flipping between views
        viewFlipper.setAutoStart(true);

    }

    private void ShowMainMenu() {
        setSupportActionBar(toolbarHomePage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHomePage.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarHomePage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



    }

    public void mapping(){
        toolbarHomePage = findViewById(R.id.toolbarhomepage);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        edt_UserName = (EditText) findViewById(R.id.edt_username);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        btn_Login = (Button) findViewById(R.id.btn_login);
        drawerLogin = (DrawerLayout) findViewById(R.id.drawerLogin);
        btn_Logout = (Button) findViewById(R.id.btn_logout);
        tv_UserNameProfile = (TextView) findViewById(R.id.tv_userNameProfile);
        tv_UserType = (TextView) findViewById(R.id.tv_userRankProfile);
        tv_TotalSpentDetails =(TextView) findViewById(R.id.tv_totalspentdetails);
        tv_TotalRewardPoint = (TextView) findViewById(R.id.tv_rewardpointdetails);
//        listViewCategories = findViewById(R.id.listviewmainmenu);
//        BookCategories categories1 = new BookCategories(1,"Arts & Music");
//        BookCategories categories2 = new BookCategories(2,"Business");
//        BookCategories categories3 = new BookCategories(3,"Comics");
//        BookCategories categories4 = new BookCategories(4,"Cooking");
//        BookCategories categories5 = new BookCategories(5,"Computers & Technology");
//        BookCategories categories6 = new BookCategories(6,"Education & Reference");
//        bookCategoriesList.add(categories1);
//        bookCategoriesList.add(categories2);
//        bookCategoriesList.add(categories3);
//        bookCategoriesList.add(categories4);
//        bookCategoriesList.add(categories5);
//        bookCategoriesList.add(categories6);
//        categoriesArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,bookCategoriesList);
//        listViewCategories.setAdapter(categoriesArrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.exit){
            finish();
        }
        if(id==R.id.Cart){
            if(checkLogin){
                Intent myIntent = new Intent(getApplicationContext(),CartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("UserId", UserId);
                bundle.putInt("TimesShopping",timesShopping);
                bundle.putString("Address",address);
                bundle.putInt("Phone",phone);
                myIntent.putExtra("UserPackage",bundle);
                startActivity(myIntent);
            }else {
                Toast.makeText(MainActivity.this,"Login First, Please !!!", Toast.LENGTH_SHORT).show();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addtocart:
                Toast.makeText(getApplicationContext(),"Add Successful!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.details:
                Toast.makeText(getApplicationContext(),"Details...",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private List<Book> getListData(){
        List<Book> list = new ArrayList<Book>();
        Book book1 = new Book(1,"The Words of Jesus", "290000", "Various", "10", "Science fiction","book1","11/12/2022","Record your own words of wisdom for future generations. If you're looking for a new book in which to record your thoughts, lists, and more, you're in luck! Canterbury Classics, known for publishing fine works of literature, has released a new line of devotional journals for your writing pleasure.");
        Book book2 = new Book(2,"Steve Jobs", "349000", "Walter Isaacson", "10", "Information Technology","book2","11/12/2022","Based on more than forty interviews with Steve Jobs conducted over two years--as well as interviews with more than 100 family members, friends, adversaries, competitors, and colleagues--Walter Isaacson has written a riveting story of the roller-coaster life and searingly intense personality of a creative entrepreneur whose passion for perfection and ferocious drive revolutionized six industries: personal computers, animated movies, music, phones, tablet computing, and digital publishing.");
        Book book3 = new Book(3,"A Promised Land", "299000", "Barack Obama", "10", "U.S. History ","book3","11/12/2022","A riveting, deeply personal account of history in the making--from the president who inspired us to believe in the power of democracy");
        Book book4 = new Book(4,"A Guide to Visual Presentation", "399000", "Ruzaimi Mat Rani", "10", "Drawing","book4","11/12/2022","Put pen to paper and master the quality graphics and visual presentation techniques to create creative commercial and architectural compositions!");
        Book book5 = new Book(5,"Noise : A Flaw in Human Judgment", "199000", "Daniel Kahneman", "10", "Psychology","book5","11/12/2022","From the Nobel Prize-winning author of Thinking, Fast and Slow and the coauthor of Nudge, a revolutionary exploration of why people make bad judgments and how to make better ones--\"a tour de force\" (New York Times).");
        Book book6 = new Book(6,"How Google Works", "179000", "Eric Schmidt, Jonathan Rosenberg", "10", "Information Technology","book6","11/12/2022","Seasoned Google executives Eric Schmidt and Jonathan Rosenberg provide an insider's guide to Google, from its business history and disruptive corporate strategy to developing a new managment philosophy and creating a corporate culture where innovation and creativity thrive.");

        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);
        list.add(book6);
        return list;
    }
}