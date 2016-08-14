package com.valevich.balinasofttest.storage.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.raizlabs.android.dbflow.structure.container.ModelContainerAdapter;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.valevich.balinasofttest.storage.BalinaSoftTestDatabase;
import com.valevich.balinasofttest.storage.TransactionExecutor;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
@Table(database = BalinaSoftTestDatabase.class,
        uniqueColumnGroups = {@UniqueGroup(groupNumber = 2, uniqueConflict = ConflictAction.REPLACE)})
public class Meal extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    @Unique(unique = false, uniqueGroups = 2)
    private String name;

    @Column
    @Unique(unique = false, uniqueGroups = 2)
    private String price;

    @Column
    @Unique(unique = false, uniqueGroups = 2)
    private String description;

    @Column
    @Unique(unique = false, uniqueGroups = 2)
    private String weight;

    @Column
    @Unique(unique = false, uniqueGroups = 2)
    private String imageUrl;

    @ForeignKey
    ForeignKeyContainer<Category> category;

    private static final TransactionExecutor<Meal> mTransactionExecutor
            = new TransactionExecutor<>();

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

    public static void create(List<Meal> mealsToProcess,
                              Transaction.Success successCallback,
                              Transaction.Error errorCallback) {
        mTransactionExecutor.create(mealsToProcess, successCallback, errorCallback);
    }

    public void associateCategory(Category category) {
        ModelContainerAdapter<Category> adapter = FlowManager
                .getContainerAdapter(Category.class);
        this.category = adapter.toForeignKeyContainer(category);
    }

}
