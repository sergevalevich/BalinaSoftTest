package com.valevich.balinasofttest.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CatalogSavedEvent;
import com.valevich.balinasofttest.eventbus.events.CategorySelectedEvent;
import com.valevich.balinasofttest.eventbus.events.ErrorEvent;
import com.valevich.balinasofttest.eventbus.events.FetchStartedEvent;
import com.valevich.balinasofttest.network.sync.SyncAdapter;
import com.valevich.balinasofttest.ui.fragments.CategoriesFragment_;
import com.valevich.balinasofttest.ui.fragments.ContactsFragment_;
import com.valevich.balinasofttest.ui.fragments.MealsFragment_;
import com.valevich.balinasofttest.utils.NetworkStateChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity
public class MainActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener {

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.navigation_view)
    NavigationView mNavigationView;

    @ViewById(R.id.swipeToRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @StringRes(R.string.nav_drawer_categories)
    String mCategoriesTitle;

    @StringRes(R.string.nav_drawer_contacts)
    String mContactsTitle;

    @StringRes(R.string.meals)
    String mMealsTitle;

    @StringRes(R.string.loading_message)
    String mLoadingMessage;

    @StringRes(R.string.network_unavailable_message)
    String mNetworkUnavailableMessage;

    @InstanceState
    String mToolbarTitle;

    @Bean
    EventBus mEventBus;

    @Bean
    NetworkStateChecker mNetworkStateChecker;

    @Bean
    SyncAdapter mSyncAdapter;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSyncAdapter.initializeSyncAdapter(this);

        if (savedInstanceState == null) {
            replaceFragment(new CategoriesFragment_());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRefreshing();
    }

    @Subscribe
    public void onCategorySelected(CategorySelectedEvent event) {
        replaceFragment(MealsFragment_
                .builder()
                .mCategoryName(event.getCategoryName())
                .build());
    }

    @Subscribe
    public void onFetchStarted(FetchStartedEvent event) {
        notifyUser(mLoadingMessage,false);
    }

    @Subscribe
    public void onError(ErrorEvent event) {
        stopRefreshing();
        notifyUser(event.getMessage(),true);
    }

    @Subscribe
    public void onCatalogSaved(CatalogSavedEvent event) {
        stopRefreshing();
    }

    @AfterViews
    void setupViews() {
        setupActionBar();
        setUpSwipeToRefresh();
        setupDrawerLayout();
        setupFragmentManager();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mFragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onBackStackChanged() {

        Fragment f = mFragmentManager
                .findFragmentById(R.id.main_container);

        if (f != null) {
            changeToolbarTitle(f.getClass().getName());
        }

    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mNetworkStateChecker.isNetworkAvailable())
                    mSyncAdapter.syncImmediately();
                else {
                    notifyUser(mNetworkUnavailableMessage,true);
                    stopRefreshing();
                }
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void stopRefreshing() {
        if(mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setupDrawerLayout() {
        setupNavigationContent();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout
                , mToolbar
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
        if (mToolbarTitle != null)
            setTitle(mToolbarTitle);
    }

    private void setupNavigationContent() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.drawer_categories:
                        replaceFragment(new CategoriesFragment_());
                        break;
                    case R.id.drawer_contacts:
                        replaceFragment(new ContactsFragment_());
                        break;
                }
                return true;
            }
        });
    }

    private void setupFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);
    }

    private void changeToolbarTitle(String backStackEntryName) {
        if (backStackEntryName.equals(CategoriesFragment_.class.getName())) {
            setTitle(mCategoriesTitle);
            mToolbarTitle = mCategoriesTitle;
            mNavigationView.setCheckedItem(R.id.drawer_categories);
        } else if (backStackEntryName.equals(ContactsFragment_.class.getName())) {
            setTitle(mContactsTitle);
            mToolbarTitle = mContactsTitle;
            mNavigationView.setCheckedItem(R.id.drawer_contacts);
        } else {
            setTitle(mMealsTitle);
            mToolbarTitle = mMealsTitle;
        }
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        boolean isFragmentPopped = mFragmentManager.popBackStackImmediate(backStackName, 0);

        if (!isFragmentPopped && mFragmentManager.findFragmentByTag(backStackName) == null) {

            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, backStackName);
            transaction.addToBackStack(backStackName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

        }
    }

    private void notifyUser(String message, boolean withSnackBar) {
        if(withSnackBar)
            Snackbar.make(mDrawerLayout, message, Snackbar.LENGTH_LONG)
                    .show();

        else Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
