package com.valevich.balinasofttest.network.sync;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CatalogSavedEvent;
import com.valevich.balinasofttest.eventbus.events.ErrorEvent;
import com.valevich.balinasofttest.eventbus.events.FetchStartedEvent;
import com.valevich.balinasofttest.network.RestService;
import com.valevich.balinasofttest.network.model.CategoryApiModel;
import com.valevich.balinasofttest.network.model.FetchedCatalogModel;
import com.valevich.balinasofttest.network.model.MealApiModel;
import com.valevich.balinasofttest.network.model.ShopApiModel;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.utils.NetworkStateChecker;
import com.valevich.balinasofttest.utils.TriesCounter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


@EBean(scope = EBean.Scope.Singleton)
public class SyncAdapter extends AbstractThreadedSyncAdapter implements
        Callback<FetchedCatalogModel>,
        Transaction.Success,
        Transaction.Error {

    private static Account mAccount;

    private static final int DEFAULT_SYNC_INTERVAL = 3600;//hour

    @StringRes(R.string.api_key)
    String mApiKey;

    @StringRes(R.string.network_unavailable_message)
    String mNetworkUnavailableMessage;

    @StringRes(R.string.weight_param_name)
    String mWeightParamName;

    @StringRes(R.string.content_authority)
    String mContentAuthority;

    @StringRes(R.string.sync_account_type)
    String mSyncAccountType;

    @Bean
    NetworkStateChecker mNetworkStateChecker;

    @Bean
    RestService mRestService;

    @Bean
    EventBus mEventBus;

    @Bean
    TriesCounter mTriesCounter;


    public SyncAdapter(Context context) {
        super(context, true);
    }

    public void syncImmediately() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,
                true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL,
                true);
        ContentResolver.requestSync(mAccount,
                mContentAuthority, bundle);
    }

    public void initializeSyncAdapter(Context context) {
        createAccount(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        fetchCatalog();

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
        }
    }

    // database callbacks
    @Override
    public void onSuccess(Transaction transaction) {
        notifyCatalogSaved();
    }

    @Override
    public void onError(Transaction transaction, Throwable error) {
        notifyAboutError(error.getLocalizedMessage());
    }

    private void fetchCatalog() {
        if (mNetworkStateChecker.isNetworkAvailable()) {
            notifyFetchStarted();
            mRestService.fetchCatalog(mApiKey, this);
        } else {
            notifyAboutError(mNetworkUnavailableMessage);
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
                        SyncAdapter.this,
                        SyncAdapter.this);
            }
        }, SyncAdapter.this);
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
        List<Category> savedCategories = Category.getAll();

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
        mealToSave.setWeight(fetchedMeal.getParam(mWeightParamName));
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

    private void notifyFetchStarted() {
        mEventBus.post(new FetchStartedEvent());
    }

    private void createAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        mAccount = new Account(context.getString(R.string.app_name),
                mSyncAccountType);
        ContentResolver.setIsSyncable(mAccount, mContentAuthority, 1);
        if (null == accountManager.getPassword(mAccount)) {
            if (!accountManager.addAccountExplicitly(mAccount, "", null)) {
                mAccount = null;
            }
            onAccountCreated();
        }
    }

    private void onAccountCreated() {
        configureSync();
        syncImmediately();
    }

    public void configureSync() {

        final int SYNC_INTERVAL = DEFAULT_SYNC_INTERVAL;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(SYNC_INTERVAL, SYNC_FLEXTIME).
                    setSyncAdapter(mAccount, mContentAuthority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(mAccount,
                    mContentAuthority, new Bundle(), SYNC_INTERVAL);
        }

        ContentResolver.setSyncAutomatically(mAccount,
                mContentAuthority, true);
        ContentResolver.addPeriodicSync(mAccount, mContentAuthority,
                Bundle.EMPTY,
                SYNC_INTERVAL);
    }
}
