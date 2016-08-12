package com.valevich.balinasofttest.storage.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.valevich.balinasofttest.storage.BalinaSoftTestDatabase;
import com.valevich.balinasofttest.utils.StubConstants;

import org.androidannotations.annotations.EBean;

import java.io.Serializable;
import java.util.List;

@EBean
@ModelContainer
@Table(database = BalinaSoftTestDatabase.class)
public class Category extends BaseModel implements Serializable{

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    List<Meal> meals;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "meals")
    public List<Meal> getMeals() {
        if (meals == null || meals.isEmpty()) {
            meals = SQLite.select()
                    .from(Meal.class)
                    .where(Meal_Table.category_id.eq(id))
                    .queryList();
        }
        return meals;
    }

    public static List<Category> getAllCategories() {
        return SQLite.select()
                .from(Category.class)
                .queryList();
    }

    //giving stub static icons
    public int getIconResourceId() {
        switch (name) {
            case StubConstants.STUB_CATEGORY_PIZZA:
                return StubConstants.STUB_CATEGORY_PIZZA_ICON;
            case StubConstants.STUB_CATEGORY_BARBECUE:
                return StubConstants.STUB_CATEGORY_BARBECUE_ICON;
            case StubConstants.STUB_CATEGORY_CONSTRUCTOR:
                return StubConstants.STUB_CATEGORY_CONSTRUCTOR_ICON;
            case StubConstants.STUB_CATEGORY_NOODLES:
                return StubConstants.STUB_CATEGORY_NOODLES_ICON;
            case StubConstants.STUB_CATEGORY_SETS:
                return StubConstants.STUB_CATEGORY_SETS_ICON;
            case StubConstants.STUB_CATEGORY_ROLLS:
                return StubConstants.STUB_CATEGORY_ROLLS_ICON;
            case StubConstants.STUB_CATEGORY_SUSHI:
                return StubConstants.STUB_CATEGORY_SUSHI_ICON;
            case StubConstants.STUB_CATEGORY_SOUPS:
                return StubConstants.STUB_CATEGORY_SOUPS_ICON;
            case StubConstants.STUB_CATEGORY_SUPPLEMENTS:
                return StubConstants.STUB_CATEGORY_SUPPLEMENTS_ICON;
            case StubConstants.STUB_CATEGORY_SALADS:
                return StubConstants.STUB_CATEGORY_SALADS_ICON;
            case StubConstants.STUB_CATEGORY_WARM:
                return StubConstants.STUB_CATEGORY_WARM_ICON;
            case StubConstants.STUB_CATEGORY_SNACKS:
                return StubConstants.STUB_CATEGORY_SNACKS_ICON;
            case StubConstants.STUB_CATEGORY_DESSERTS:
                return StubConstants.STUB_CATEGORY_DESSERTS_ICON;
            case StubConstants.STUB_CATEGORY_DRINKS:
                return StubConstants.STUB_CATEGORY_DRINKS_ICON;
        }
        return StubConstants.CATEGORY_PLACEHOLDER_ICON;
    }
}
