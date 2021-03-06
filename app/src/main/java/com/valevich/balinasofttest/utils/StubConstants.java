package com.valevich.balinasofttest.utils;

import com.valevich.balinasofttest.R;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//--stubs for static data
//--zaglushki dlya staticheskih dannyh
//--заглушки
public interface StubConstants {
    String STUB_CATEGORY_BARBECUE = "Шашлыки";
    String STUB_CATEGORY_CONSTRUCTOR = "Конструктор";
    String STUB_CATEGORY_NOODLES = "Лапша";
    String STUB_CATEGORY_SETS = "Сеты";
    String STUB_CATEGORY_ROLLS = "Роллы";
    String STUB_CATEGORY_SUSHI = "Суши";
    String STUB_CATEGORY_SOUPS = "Супы";
    String STUB_CATEGORY_SUPPLEMENTS = "Добавки";
    String STUB_CATEGORY_SALADS = "Салаты";
    String STUB_CATEGORY_WARM = "Теплое";
    String STUB_CATEGORY_SNACKS = "Закуски";
    String STUB_CATEGORY_DESSERTS = "Десерты";
    String STUB_CATEGORY_DRINKS = "Напитки";
    String STUB_CATEGORY_PIZZA = "Пицца";

    //dummy icons
    int STUB_CATEGORY_BARBECUE_ICON = R.drawable.barbecue;
    int STUB_CATEGORY_CONSTRUCTOR_ICON = R.drawable.constructor;
    int STUB_CATEGORY_NOODLES_ICON = R.drawable.noodles;
    int STUB_CATEGORY_SETS_ICON = R.drawable.sets;
    int STUB_CATEGORY_ROLLS_ICON = R.drawable.rolls;
    int STUB_CATEGORY_SUSHI_ICON = R.drawable.sushi;
    int STUB_CATEGORY_SOUPS_ICON = R.drawable.soups;
    int STUB_CATEGORY_SUPPLEMENTS_ICON = R.drawable.supplements;
    int STUB_CATEGORY_SALADS_ICON = R.drawable.salads;
    int STUB_CATEGORY_WARM_ICON = R.drawable.warm;
    int STUB_CATEGORY_SNACKS_ICON = R.drawable.snacks;
    int STUB_CATEGORY_DESSERTS_ICON = R.drawable.deserts;
    int STUB_CATEGORY_DRINKS_ICON = R.drawable.drinks;
    int STUB_CATEGORY_PIZZA_ICON = R.drawable.pizza;
    int CATEGORY_PLACEHOLDER_ICON = R.drawable.image_placeholder;

    //dummy location
    List<Double> STUB_LATITUDES = new ArrayList<>(Arrays.asList(53.85,53.900333,53.900333,53.877068));
    List<Double> STUB_LONGITUDES = new ArrayList<>(Arrays.asList(27.47,27.47,27.461083,27.620239));
    List<String> STUB_ADDRESSES = new ArrayList<>(Arrays.asList(
            "Минск, Дзержинского 122",
            "Минск, Одинцова 4",
            "Минск, Победителей 20/2",
            "Минск, Партизанский проспект 71"));

    //dummy phone numbers
    List<String> STUB_PHONE_NUMBERS = new ArrayList<>(Arrays.asList(
            "+375 29 659 93 45",
            "+375 29 742 54 78",
            "+375 44 666 03 45",
            "+375 44 335 88 22"));

    List<Map.Entry<String,String>> ADDRESS_BY_NUMBER = new ArrayList<Map.Entry<String, String>>(Arrays.asList(
            new AbstractMap.SimpleEntry<>(STUB_ADDRESSES.get(0),STUB_PHONE_NUMBERS.get(0)),
            new AbstractMap.SimpleEntry<>(STUB_ADDRESSES.get(1),STUB_PHONE_NUMBERS.get(1)),
            new AbstractMap.SimpleEntry<>(STUB_ADDRESSES.get(2),STUB_PHONE_NUMBERS.get(2)),
            new AbstractMap.SimpleEntry<>(STUB_ADDRESSES.get(3),STUB_PHONE_NUMBERS.get(3))));

    String CONTACT_INFO_FORMAT = "%s %n %s";

}
