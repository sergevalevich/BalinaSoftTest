package com.valevich.balinasofttest.ui.recyclerview.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.ContactSelectedEvent;
import com.valevich.balinasofttest.ui.recyclerview.ViewWrapper;
import com.valevich.balinasofttest.ui.recyclerview.views.ContactItemView;
import com.valevich.balinasofttest.ui.recyclerview.views.ContactItemView_;
import com.valevich.balinasofttest.utils.StubConstants;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Map;

@EBean
public class ContactsAdapter
        extends RecyclerViewAdapterBase<Map.Entry<String,String>, ContactItemView> {

    @RootContext
    Context mContext;

    @Bean
    EventBus mEventBus;

    public void initAdapter() {
        mItems = StubConstants.ADDRESS_BY_NUMBER;
    }

    @Override
    protected ContactItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ContactItemView_.build(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ContactItemView> holder, int position) {
        ContactItemView view = holder.getView();
        Map.Entry<String,String> item = mItems.get(position);
        view.bindData(item);

        setItemClickNotification(view, item);
    }

    private void setItemClickNotification(ContactItemView view, final Map.Entry<String,String> item){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyContactSelectedClicked(item);
            }
        });
    }

    private void notifyContactSelectedClicked(Map.Entry<String,String> item) {
        mEventBus.post(new ContactSelectedEvent(item));
    }

}
