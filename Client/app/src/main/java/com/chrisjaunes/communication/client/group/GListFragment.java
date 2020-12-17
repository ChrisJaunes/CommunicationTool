package com.chrisjaunes.communication.client.group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.group.model.GroupConfig;

import java.util.Objects;
/**
 * a fragment about function(add contacts)
 * version 1.1 - 1.2: 添加群聊
 * version 1.3 : 将ViewModel和其他控制逻辑移到onActivityCreated里面
 * @author chrisjuanes
 * @version 1.3（2020/12/18）
 */
public class GListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_group, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        assert view != null;
        // TODO GroupViewModel 生命周期为 Activity
        final GListViewModel gListViewModel = new ViewModelProvider(requireActivity()).get(GListViewModel.class);
        // DONE 下拉刷新控件，用于下拉时请求服务器端
        final TextView tvSwipeRefresh = view.findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout layoutSwipeRefresh = view.findViewById(R.id.layout_swipe_refresh);
        layoutSwipeRefresh.setEnabled(true);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            tvSwipeRefresh.setText("正在刷新");
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            gListViewModel.queryServer();
        });
        // DONE RecycleView控件，更新current Contacts列表，Adapter支持点击跳转
        final RecyclerView rvGroup = view.findViewById(R.id.rv_group);
        rvGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
        final GListAdapter gListAdapter = new GListAdapter((group, groupName) -> {
            Log.d("NowGroup", "" + group);
            Intent intent = new Intent(getActivity(), GMessageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(GroupConfig.STR_GROUP, group);
            bundle.putString(GroupConfig.STR_GROUP_NAME, groupName);
            intent.putExtras(bundle);
            requireActivity().startActivity(intent);
        });
        rvGroup.setAdapter(gListAdapter);
        rvGroup.setItemAnimator(new DefaultItemAnimator());
        // TODO contactsViewModel 监听远程服务端的返回结果 监听current contacts list 变化情况
        gListViewModel.getUniApiResult().observe(requireActivity(), uniApiResult -> {
            Log.d("NowGroup", uniApiResult.status + uniApiResult.data);
            Toast.makeText(getActivity(), uniApiResult.status, Toast.LENGTH_SHORT).show();
            tvSwipeRefresh.setVisibility(View.GONE);
            layoutSwipeRefresh.setRefreshing(false);
        });
        gListViewModel.getGInfoList().observe(requireActivity(), contactsList -> {
            Log.d("NowGroup", "groupList" + contactsList + "| size : " + contactsList.size());
            gListAdapter.addGInfoList(contactsList);
        });
    }
}