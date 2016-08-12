package com.valevich.balinasofttest.storage.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.raizlabs.android.dbflow.structure.container.ModelContainerAdapter;
import com.valevich.balinasofttest.storage.BalinaSoftTestDatabase;

import org.androidannotations.annotations.EBean;

@EBean
@Table(database = BalinaSoftTestDatabase.class)
public class Meal extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    @Column
    private String price;

    @Column
    private String description;

    @Column
    private String weight;

    @Column
    private String imageUrl;

    @ForeignKey
    ForeignKeyContainer<Category> category;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void associateCategory(Category category) {
        ModelContainerAdapter<Category> adapter = FlowManager
                .getContainerAdapter(Category.class);
        this.category = adapter.toForeignKeyContainer(category);
    }

}
