package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);
    }

    public void initFruits() {
        for (int i = 0; i < 3; ++i) {
            Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            fruitList.add(apple);

            Fruit banana = new Fruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
            fruitList.add(banana);

            Fruit orange = new Fruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
            fruitList.add(orange);

            Fruit watermelon = new Fruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            fruitList.add(watermelon);

            Fruit pear = new Fruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
            fruitList.add(pear);

            Fruit pineapple = new Fruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            fruitList.add(pineapple);

            Fruit strawberry = new Fruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            fruitList.add(strawberry);
        }
    }

    private static String getRandomLengthName(String name) {
        Random rand = new Random();
        int length = rand.nextInt(20);
        StringBuilder builder = new StringBuilder(name.length() * length);

        for (int i = 0; i < length; ++i) {
            builder.append(name);
        }

        return builder.toString();
    }
}
