package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wwmeet_android.R;

import java.util.HashMap;
import java.util.Map;

public class AIFoodResultActivity extends AppCompatActivity {
    private Button findRestaurantBtn;
    private TextView percentText, emotionText, emotionSentence, foodTypeText, foodTypeRecommendText, emotionFoodRecommendText;
    private ImageView emotionImg, foodTypeRecommendImg, emotionFoodRecommendImg;
    private static final Map<String, String> emotionImageMap = new HashMap<>();

    static {
        emotionImageMap.put("행복", "https://w7.pngwing.com/pngs/871/712/png-transparent-emoji-love-happy-icon.png");
        emotionImageMap.put("분노", "https://w7.pngwing.com/pngs/918/603/png-transparent-emoji-anger-smiley-emoticon-icon-angry-emoji-angry-emoji-illustration-face-orange-computer-wallpaper.png");
        emotionImageMap.put("슬픔", "https://w7.pngwing.com/pngs/435/333/png-transparent-emoji-emoticon-sad-face-smiley-sadness.png");
        emotionImageMap.put("중립", "https://static.vecteezy.com/system/resources/previews/009/931/818/original/indifference-face-emoji-file-png.png");
        emotionImageMap.put("공포", "https://w7.pngwing.com/pngs/497/320/png-transparent-emojipedia-screaming-fear-emoticon-emoji-face-love-orange-smiley.png");
    }

    private static final Map<String, String> foodTypeImageMap = new HashMap<>();

    static {
        foodTypeImageMap.put("비빔밥", "https://png.pngtree.com/png-clipart/20190614/original/pngtree-korean-traditional-stone-pot-rice-png-image_3710021.jpg");
        foodTypeImageMap.put("불고기", "https://w7.pngwing.com/pngs/74/734/png-transparent-bulgogi-meat-churrasco-restaurant-loin-meat-food-recipe-onion.png");
        foodTypeImageMap.put("김치찌개", "https://png.pngtree.com/png-vector/20210927/ourmid/pngtree-kimchi-soup-still-life-diet-color-kitchen-png-image_3956341.png");
        foodTypeImageMap.put("떡볶이", "https://w7.pngwing.com/pngs/607/223/png-transparent-tteok-bokki-rice-cake-korean-cuisine-nian-gao-cheesecake-fry-rice-cake-in-the-basket-food-cheese-recipe-thumbnail.png");
        foodTypeImageMap.put("삼겹살", "https://w7.pngwing.com/pngs/216/449/png-transparent-pork-belly-sirloin-steak-roasting-recipe-dish-bbq-ribs-food-beef-recipe.png");
        foodTypeImageMap.put("전", "https://i.pinimg.com/736x/2c/fc/ba/2cfcbac6e1e20e6eae00439c99812efe.jpg");
        foodTypeImageMap.put("짜장면", "https://png.pngtree.com/png-clipart/20220124/ourmid/pngtree-jjajangmyeon-korean-food-illustration-png-image_4353965.png");
        foodTypeImageMap.put("탕수육", "https://png.pngtree.com/png-vector/20220120/ourmid/pngtree-cantonese-sweet-and-sour-pork-png-image_4351534.png");
        foodTypeImageMap.put("깐풍기", "https://cdn.imweb.me/thumbnail/20190430/5cc771423f74c.png");
        foodTypeImageMap.put("마파두부", "https://img.freepik.com/premium-psd/bowl-of-mapo-tofu-on-transparent-background_584197-1503.jpg?w=2000");
        foodTypeImageMap.put("볶음밥", "https://png.pngtree.com/png-vector/20201128/ourmid/pngtree-eight-delicacies-rice-png-image_2445376.jpg");
        foodTypeImageMap.put("초밥", "https://w7.pngwing.com/pngs/166/534/png-transparent-california-roll-sashimi-sushi-japanese-cuisine-chirashizushi-sushi.png");
        foodTypeImageMap.put("라멘", "https://img.freepik.com/premium-psd/japanese-ramen-isolated-on-transparent-background_879541-316.jpg");
        foodTypeImageMap.put("돈까스", "https://w7.pngwing.com/pngs/939/723/png-transparent-tonkatsu-korokke-veal-milanese-cutlet-plate-plate-food-recipe-plate-thumbnail.png");
        foodTypeImageMap.put("우동", "https://png.pngtree.com/png-clipart/20210618/ourmid/pngtree-nabeyaki-udon-with-tempura-png-image_3484910.jpg");
        foodTypeImageMap.put("텐동", "https://w1.pngwing.com/pngs/960/629/png-transparent-junk-food-tendon-tempura-shrimp-restaurant-rice-cake-deep-frying.png");
        foodTypeImageMap.put("스테이크", "https://img.freepik.com/premium-psd/slices-of-beef-steak-on-a-fork-isolated-on-transparent-background-png-psd_888962-628.jpg");
        foodTypeImageMap.put("파스타", "https://w7.pngwing.com/pngs/260/114/png-transparent-pasta.png");
        foodTypeImageMap.put("피자", "https://e7.pngegg.com/pngimages/935/770/png-clipart-pizza-pizza.png");
        foodTypeImageMap.put("카레", "https://w7.pngwing.com/pngs/804/454/png-transparent-yellow-curry-japanese-curry-red-curry-rice-and-curry-gulai-veg-curries-food-recipe-white-rice.png");
        foodTypeImageMap.put("아이스크림", "https://w7.pngwing.com/pngs/392/515/png-transparent-ice-cream-ice-cream-cones-ice.png");
        foodTypeImageMap.put("초콜릿", "https://w1.pngwing.com/pngs/103/553/png-transparent-chocolate-bar-hershey-bar-hershey-company-kinder-chocolate-white-chocolate-reeses-peanut-butter-cups-candy-types-of-chocolate.png");
        foodTypeImageMap.put("파스타", "https://e7.pngegg.com/pngimages/390/162/png-clipart-pasta-pasta.png");
        foodTypeImageMap.put("피자", "https://e7.pngegg.com/pngimages/152/1018/png-clipart-domino-s-pizza-garlic-bread-pepperoni-restaurant-cheese-splash-domino-s-pizza-garlic-bread.png");
    }

    private static final Map<String, String> emotionFoodImageMap = new HashMap<>();

    static {
        emotionFoodImageMap.put("아이스크림", "https://w7.pngwing.com/pngs/392/515/png-transparent-ice-cream-ice-cream-cones-ice.png");
        emotionFoodImageMap.put("초콜릿", "https://w1.pngwing.com/pngs/103/553/png-transparent-chocolate-bar-hershey-bar-hershey-company-kinder-chocolate-white-chocolate-reeses-peanut-butter-cups-candy-types-of-chocolate.png");
        emotionFoodImageMap.put("파스타", "https://e7.pngegg.com/pngimages/390/162/png-clipart-pasta-pasta.png");
        emotionFoodImageMap.put("피자", "https://e7.pngegg.com/pngimages/152/1018/png-clipart-domino-s-pizza-garlic-bread-pepperoni-restaurant-cheese-splash-domino-s-pizza-garlic-bread.png");
        emotionFoodImageMap.put("토마토 수프", "https://w7.pngwing.com/pngs/769/660/png-transparent-tomato-soup-gazpacho-vegetarian-cuisine-vegetable-garnish-vegetable-soup-food-recipe.png");
        emotionFoodImageMap.put("죽", "https://w7.pngwing.com/pngs/419/937/png-transparent-congee-asian-cuisine-soup-rice-shrimp-rice-porridge-soup-food-animals.png");
        emotionFoodImageMap.put("따뜻한 차", "https://png.pngtree.com/png-vector/20210429/ourmid/pngtree-beverage-drink-black-tea-png-image_3240384.png");
        emotionFoodImageMap.put("그릴치즈샌드위치", "https://png.pngtree.com/png-vector/20210415/ourmid/pngtree-sandwich-nutrition-sandwich-png-image_3219812.jpg");
        emotionFoodImageMap.put("허브차", "https://w7.pngwing.com/pngs/994/520/png-transparent-green-tea-herbal-tea-drink-chamomile-tea.png");
        emotionFoodImageMap.put("치킨 스프", "https://w7.pngwing.com/pngs/467/812/png-transparent-leek-soup-vichyssoise-clam-chowder-potage-roasted-steak-soup-food-recipe.png");
        emotionFoodImageMap.put("샐러드", "https://e7.pngegg.com/pngimages/749/999/png-clipart-greek-salad-caesar-salad-fattoush-spinach-salad-chicken-salad-garden-salad-leaf-vegetable-food.png");
        emotionFoodImageMap.put("닭가슴살", "https://w7.pngwing.com/pngs/995/429/png-transparent-chicken-as-food-meat-chicken-breast-beef-tenderloin-chicken-fillet-seafood-recipe-chicken.png");
        emotionFoodImageMap.put("라면", "https://w7.pngwing.com/pngs/520/730/png-transparent-ramen-chinese-cuisine-instant-noodle-cafe-ramen-food-cuisine-restaurant-thumbnail.png");
        emotionFoodImageMap.put("불닭볶음면", "https://i.namu.wiki/i/8QIkCOxYp_Vc1sfS4CGlFqt9O4Wh-Zqaj_p4uaCFDfm_6C8y_uJ4NBibDOSxUSgC8vAQ-2REZRpxPjoC9xOO3g.webp");
        emotionFoodImageMap.put("떡볶이", "https://png.pngtree.com/png-clipart/20220119/ourmid/pngtree-ttoekbokki-illustration-png-image_4336800.png");
        emotionFoodImageMap.put("마라탕", "https://png.pngtree.com/png-clipart/20210126/ourlarge/pngtree-mara-hot-pot-png-image_2816953.png");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_food_result);
        init();
        setData();

        findRestaurantBtn = findViewById(R.id.ai_food_result_restaurant_btn);
        findRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        emotionText = findViewById(R.id.ai_food_result_emotion);
        emotionImg = findViewById(R.id.ai_food_result_emotion_image);
        percentText = findViewById(R.id.ai_food_result_probability);

        emotionSentence = findViewById(R.id.ai_food_result_emotion_sentence);

        foodTypeText = findViewById(R.id.ai_food_result_food_type);
        foodTypeRecommendImg = findViewById(R.id.ai_food_result_food_type_image);
        foodTypeRecommendText = findViewById(R.id.ai_food_result_type_food);

        emotionFoodRecommendImg = findViewById(R.id.ai_food_result_emotion_food_image);
        emotionFoodRecommendText = findViewById(R.id.ai_food_result_emotion_food);
    }

    private void setData(){
        Intent intent = getIntent();
        String aiData = intent.getStringExtra("ai data");
        String aiData1 = intent.getStringExtra("ai data1");
        String[] dataArray = aiData.split(" ");

        emotionText.setText(dataArray[0]);
        percentText.setText(dataArray[1]);
        emotionSentence.setText(aiData1);

        foodTypeText.setText(dataArray[4]);
        foodTypeRecommendText.setText(dataArray[2]);
        emotionFoodRecommendText.setText(dataArray[3]);


        String emotionImageUrl = emotionImageMap.get(dataArray[0]);
        String foodTypeImageUrl = foodTypeImageMap.get(dataArray[2]);
        String emotionFoodImageUrl = emotionFoodImageMap.get(dataArray[3]);


        Glide.with(getApplicationContext())
                .load(emotionImageUrl)
                .into(emotionImg);

        Glide.with(getApplicationContext())
                .load(foodTypeImageUrl)
                .into(foodTypeRecommendImg);

        Glide.with(getApplicationContext())
                .load(emotionFoodImageUrl)
                .into(emotionFoodRecommendImg);

    }

}