package com.valevich.balinasofttest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CatalogSavedEvent;
import com.valevich.balinasofttest.eventbus.events.ErrorEvent;
import com.valevich.balinasofttest.network.RestService;
import com.valevich.balinasofttest.network.model.CategoryApiModel;
import com.valevich.balinasofttest.network.model.FetchedCatalogModel;
import com.valevich.balinasofttest.network.model.MealApiModel;
import com.valevich.balinasofttest.network.model.ShopApiModel;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.utils.ConstantsManager;
import com.valevich.balinasofttest.utils.NetworkStateChecker;
import com.valevich.balinasofttest.utils.TriesCounter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EService
public class CatalogLoadingService extends Service implements
        Callback<FetchedCatalogModel>,
        Transaction.Success,
        Transaction.Error {

    @Bean
    RestService mRestService;

    @Bean
    EventBus mEventBus;

    @Bean
    TriesCounter mTriesCounter;

    @Bean
    NetworkStateChecker mNetworkStateChecker;

    @StringRes(R.string.network_unavailable_message)
    String mNetworkUnavailableMessage;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        fetchCatalog();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // network callbacks
    @Override
    public void success(FetchedCatalogModel fetchedCatalogModel, Response response) {
        saveCatalog(fetchedCatalogModel);
    }

    @Override
    public void failure(RetrofitError error) {
        mTriesCounter.reduceTry();
        if (mTriesCounter.areTriesLeft()) fetchCatalog();
        else {
            notifyAboutError(error.getLocalizedMessage());
            stopSelf();
        }
    }

    // database callbacks
    @Override
    public void onSuccess(Transaction transaction) {
        notifyCatalogSaved();
        stopSelf();
    }

    @Override
    public void onError(Transaction transaction, Throwable error) {
        notifyAboutError(error.getLocalizedMessage());
        stopSelf();
    }

    private void fetchCatalog() {
        if (mNetworkStateChecker.isNetworkAvailable())
            mRestService.fetchCatalog(ConstantsManager.API_KEY, this);
        else {
            notifyAboutError(mNetworkUnavailableMessage);
            stopSelf();
        }
    }

    private void saveCatalog(FetchedCatalogModel model) {

        final ShopApiModel shop = model.getShop();
        final List<CategoryApiModel> fetchedCategories = shop.getCategoriesApiModel().getCategories();
        final List<MealApiModel> fetchedMeals = shop.getMealsApiModel().getMeals();

        Category.create(getCategoriesToSave(fetchedCategories), new Transaction.Success() {
            @Override
            public void onSuccess(Transaction transaction) {
                //saving meals only after saving categories,
                //because we can associate meals only with the already saved categories
                Meal.create(getMealsToSave(fetchedMeals,fetchedCategories),
                        CatalogLoadingService.this,
                        CatalogLoadingService.this);
            }
        }, CatalogLoadingService.this);
    }

    private List<Category> getCategoriesToSave(List<CategoryApiModel> fetchedCategories) {
        List<Category> categoriesToSave = new ArrayList<>();

        for (CategoryApiModel fetchedCategory : fetchedCategories) {
            categoriesToSave.add(createCategory(fetchedCategory));
        }
        return categoriesToSave;
    }

    private List<Meal> getMealsToSave(List<MealApiModel> fetchedMeals,
                                      List<CategoryApiModel> fetchedCategories) {

        List<Meal> mealsToSave = new ArrayList<>();
        List<Category> savedCategories = Category.getAllCategories();

        for (int i = 0; i < savedCategories.size(); i++) {
            Category savedCategory = savedCategories.get(i);

            int categoryId = fetchedCategories.get(i).getId();

            for (MealApiModel fetchedMeal : getMealListByCategory(fetchedMeals, categoryId)) {
                Meal mealToSave = createMeal(fetchedMeal);
                mealToSave.associateCategory(savedCategory);
                mealsToSave.add(mealToSave);
            }
        }
        return mealsToSave;
    }

    private Category createCategory(CategoryApiModel fetchedCategory) {
        Category categoryToSave = new Category();
        categoryToSave.setName(fetchedCategory.getName());
        return categoryToSave;
    }

    private Meal createMeal(MealApiModel fetchedMeal) {
        Meal mealToSave = new Meal();
        mealToSave.setName(fetchedMeal.getName());
        mealToSave.setPrice(fetchedMeal.getPrice());
        mealToSave.setDescription(fetchedMeal.getDescription());
        mealToSave.setWeight(fetchedMeal.getParam(ConstantsManager.MEAL_PARAMETER_WEIGHT_NAME));
        mealToSave.setImageUrl(fetchedMeal.getPictureUrl());
        return mealToSave;
    }

    private List<MealApiModel> getMealListByCategory(List<MealApiModel> fetchedMeals, int categoryId) {
        List<MealApiModel> mealsToSave = new ArrayList<>();

        for (MealApiModel fetchedMeal : fetchedMeals) {
            if (fetchedMeal.getCategoryId() == categoryId) mealsToSave.add(fetchedMeal);
        }

        return mealsToSave;
    }

    private void notifyCatalogSaved() {
        mEventBus.post(new CatalogSavedEvent());
    }

    private void notifyAboutError(String message) {
        mEventBus.post(new ErrorEvent(message));
    }

}
