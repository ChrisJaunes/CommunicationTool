package com.chrisjaunes.communication.client.group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.group.model.GroupDao;
import com.chrisjaunes.communication.client.group.model.GroupRetrofit;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.TimeHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChrisJaunes
 * the View Model management remote repository or local database
 *
 * uniApiResult : manage call back code. type : LiveData
 * GMessageList : list about result of GMessage when request remote repository or local databae to end. type : LiveData
 * gMessageDao : use room interface to operate local database. type : GroupDao
 * group_id : label of group. type : number
 *
 * use RxJava + Retrofix、room
 * @status XXX
 */
public class GMessageViewModel extends ViewModel {
    final private MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }
    final private  MutableLiveData<List<GMessage>> GMessageList = new MutableLiveData<>();
    public LiveData<List<GMessage>> getGMessageList() { return GMessageList; }

    private final GroupDao gMessageDao;
    private final String group_id;
    private final CompositeDisposable compositeDisposable;

    public GMessageViewModel(final String group_id) {
        this.gMessageDao = MyApplication.getInstance().getLocalDataBase().getGroupDao();
        this.group_id = group_id;
        this.compositeDisposable = new CompositeDisposable();
    }
    private void updateGMessageViewManage(List<GMessage> gMessageList) {
        for(GMessage message : gMessageList) {
            Log.d(">>>", message.toString());
            gMessageDao.InsertMessage(message);
        }
    }
    public void queryLocalMessageList() {
        new Thread(() -> GMessageList.postValue(gMessageDao.queryMessageAboutGroup(group_id))).start();
    }
    // TODO
    public void queryServerMessageList() {
        String requestTime = "1970-1-1 00:00:00";
        if (compositeDisposable != null && ! compositeDisposable.isDisposed()) {
            compositeDisposable.add(HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GroupRetrofit.class)
                    .queryMessage(group_id, requestTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<UniApiResult<List<GMessage>>>() {
                        @Override
                        public void onNext(UniApiResult<List<GMessage>> listUniApiResult) {
                            uniApiResult.postValue(new UniApiResult<>(listUniApiResult.status, listUniApiResult.status));
                            new Thread(() -> {
                                updateGMessageViewManage(listUniApiResult.data);
                                queryLocalMessageList();
                            }).start();
                        }
                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace();
                            uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
                        }
                        @Override
                        public void onComplete() { }
                    })
            );
        }
    }
    // TODO 发送消息
    public void addMessage(final int content_type, final String content) {
        String sendTime = TimeHelper.getNowTime();
        if (compositeDisposable != null && ! compositeDisposable.isDisposed()) {
            compositeDisposable.add(HttpHelper.getRetrofitBuilder()
                    .baseUrl(Config.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GroupRetrofit.class)
                    .addMessage(group_id, sendTime, content_type, content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<UniApiResult<String>>() {
                        @Override
                        public void onNext(UniApiResult<String> stringUniApiResult) {
                            uniApiResult.postValue(new UniApiResult<>(stringUniApiResult.status, stringUniApiResult.status));
                            queryServerMessageList();
                        }
                        @Override
                        public void onError(Throwable t) {
                            uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
                        }
                        @Override
                        public void onComplete() { }
                    })
            );
        };
    }
    @SuppressWarnings("unchecked")
    static class Factory implements ViewModelProvider.Factory{
        String group_id;
        Factory(int group_id) {
            this.group_id = String.valueOf(group_id);
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new GMessageViewModel(group_id);
        }
    }
}
