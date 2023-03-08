package com.example.projectmobilecse441;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.ByteBuffer;
import java.util.List;

public class BookRecyclerViewAdapter  extends RecyclerView.Adapter<BookViewHolder>{
    private List<Book> books;
    private Context context;
    private LayoutInflater mLayoutInflater;
    Button Btn_holder;

    public BookRecyclerViewAdapter(Context context, List<Book> books) {
        this.books = books;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType){
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.book_recyclerview_layout, parent, false);
        recyclerViewItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                doClick((RecyclerView) parent, view);
            }
        });
        return new BookViewHolder(recyclerViewItem);
    }
    private void doClick(RecyclerView recyclerView, View itemView){
        int itemPosition = recyclerView.getChildAdapterPosition(itemView);
        Book book = this.books.get(itemPosition);
        Intent myIntent = new Intent(context,BookDetailsActivity.class);
        //Toast.makeText(this.context, book.getBookTitle(), Toast.LENGTH_SHORT).show();
        int id = book.getId();
        Bundle bundle = new Bundle();
        bundle.putString("Title",book.getBookTitle());
        bundle.putInt("Img",getDrawableResIdByName(book.getImage()));
        bundle.putInt("Id",id);
        bundle.putString("Author",book.getAuthor());
        bundle.putString("Price",book.getPrice());
        bundle.putString("Category",book.getCategory());
        bundle.putString("Description",book.getDescription());
        bundle.putString("PublishDate",book.getPublishDate());
        bundle.putString("Quantity",book.getQuantity());
        myIntent.putExtra("MyPackage",bundle);
        context.startActivity(myIntent);



    }
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position){
        Book book = this.books.get(position);
        int imageResId = this.getDrawableResIdByName(book.getImage());
        holder.bookView.setImageResource(imageResId);
        holder.author.setText(book.getAuthor());
        holder.bookTitleView.setText(book.getBookTitle());
        holder.priceView.setText(book.getPrice()+"đ");
        holder.btn_AddtoCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[]imgByte = ByteBuffer.allocate(4).putInt(imageResId).array();
                boolean checkAdded = false;
                int price = Integer.parseInt(book.getPrice());
                int quantity = Integer.parseInt(book.getQuantity());
                List<Cart> carts = CartHelper.getAll(context.getApplicationContext());
                if(carts.size()==0){
                    if(CartHelper.insert(context.getApplicationContext(),1,book.getId(),book.getBookTitle(),price,imgByte,1,price,book.getAuthor(),quantity)){
                        Toast.makeText(context.getApplicationContext(),"Add Success !!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    for (int i =0 ;i<carts.size();i++){
                        if(carts.get(i).getBookId()==book.getId()){
                            Toast.makeText(context.getApplicationContext(),"Book Has Been Added !!!", Toast.LENGTH_SHORT).show();
                        }else{
                            if(CartHelper.insert(context.getApplicationContext(),1,book.getId(),book.getBookTitle(),price,imgByte,1,price,book.getAuthor(),quantity)){

                                Toast.makeText(context.getApplicationContext(),"Add Success !!!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                }
            }
        });

    }
    //Find ImageID corresponding to the name of the image (in the directory drawable)
    public int getDrawableResIdByName(String resName){
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        return resID;
    }
    @Override
    public int getItemCount(){
        return books.size();
    }
}
