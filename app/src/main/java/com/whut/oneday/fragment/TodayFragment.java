package com.whut.oneday.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.oneday.R;
import com.whut.oneday.adapter.TodayRecyclerViewAdapter;
import com.whut.oneday.tools.Content;
import com.whut.oneday.weatherUtils.WeatherMsg;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    @InjectView(R.id.today_recyclerview)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TodayRecyclerViewAdapter adapter;
    private WeatherMsg todayWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWeather();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TodayRecyclerViewAdapter(todayWeather, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void reFresh() {
        adapter.notifyDataSetChanged();
    }

    private void initWeather() {
        todayWeather = Content.localWeatherMsg.get("天津市");
    }
}
