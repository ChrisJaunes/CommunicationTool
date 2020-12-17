package com.chrisjaunes.communication.client.group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.group.model.GInfoRaw;
import com.chrisjaunes.communication.client.group.model.GroupConfig;
import com.chrisjaunes.communication.client.group.model.GroupDao;
import com.chrisjaunes.communication.client.group.model.GroupRetrofit;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.TimeHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * TODO 基本上本地数据库部分都没有写
 * 基于RxJava 和 Retrofit
 * @author ChrisJaunes
 * @version 1
 */
public class GListViewModel extends ViewModel {
    final private MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }
    final private  MutableLiveData<List<GInfoRaw>> GInfoList = new MutableLiveData<>();
    public LiveData<List<GInfoRaw>> getGInfoList() { return GInfoList; }

    private final GroupDao gListDao;
    private final CompositeDisposable compositeDisposable;

    public GListViewModel() {
        gListDao = MyApplication.getInstance().getLocalDataBase().getGroupDao();
        this.compositeDisposable = new CompositeDisposable();
    }

    public void queryLocalGroups() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<GroupInfo> groupInfos = userDatabaseDAO.queryAllGroups();
//                LocalGroupLiveData.postValue(groupInfos);
//            }
//        }).start();
    }

    public void queryServer() {
        //String sendTime = TimeHelper.getNowTime();
        if (compositeDisposable != null && ! compositeDisposable.isDisposed()) {
            ResourceSubscriber<UniApiResult<List<GInfoRaw>>> addMessage = HttpHelper.getRetrofitBuilder()
                    .baseUrl(Config.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GroupRetrofit.class)
                    .query()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<UniApiResult<List<GInfoRaw>>>() {
                        @Override
                        public void onNext(UniApiResult<List<GInfoRaw>> listUniApiResult) {
                            uniApiResult.postValue(new UniApiResult<>(listUniApiResult.status, ""));
                            if(GroupConfig.STATUS_QUERY_GROUP_SUCCESSFUL.equals(listUniApiResult.status)) {
                                GInfoList.postValue(listUniApiResult.data);
                            }
                        }
                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace();
                            uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, ""));
                        }
                        @Override
                        public void onComplete() { }
                    });
            compositeDisposable.add(addMessage);
        }
    }
}
