package com.valevich.balinasofttest.storage;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

public class TransactionExecutor<T extends BaseModel> {

    public static String TRANSACTION_TYPE_CREATE = "CREATE";

    public interface ProcessModelCallback<T> {
        void processModel(T entry);
    }

    public void create(List<T> entries, Transaction.Success successCallback, Transaction.Error errorCallback) {
        executeProcessModelTransaction(entries, successCallback, errorCallback, mCreateCallback
                , TRANSACTION_TYPE_CREATE);
    }

    private final ProcessModelCallback<T> mCreateCallback = new ProcessModelCallback<T>() {
        @Override
        public void processModel(T entry) {
            entry.insert();
        }
    };

    private void executeProcessModelTransaction(final List<T> entries,
                                                Transaction.Success successCallback,
                                                Transaction.Error errorCallback,
                                                final ProcessModelCallback<T> processModelCallback,
                                                String type
    ) {
        DatabaseDefinition database = FlowManager.getDatabase(BalinaSoftTestDatabase.class);

        ProcessModelTransaction<T> processModelTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction
                        .ProcessModel<T>() {
                    @Override
                    public void processModel(T entry) {
                        processModelCallback.processModel(entry);
                    }
                }).processListener(new ProcessModelTransaction.OnModelProcessListener<T>() {
                    @Override
                    public void onModelProcessed(long current, long total, T entry) {

                    }
                }).addAll(entries).build();


        Transaction.Builder transactionBuilder = database
                .beginTransactionAsync(processModelTransaction);
        if (successCallback != null)
            transactionBuilder.success(successCallback);
        if (errorCallback != null)
            transactionBuilder.error(errorCallback);


        transactionBuilder.name(type).build().execute();
    }

}
