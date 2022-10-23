package com.pasich.mynotes.ui.view.helloUI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.Features;
import com.pasich.mynotes.ui.view.helloUI.tool.HelloTool;
import com.pasich.mynotes.utils.adapters.FeaturesPageAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class FeaturesFragment extends Fragment {

    private HelloTool helloTool;
    private ViewPager2 listFeatures;
    private final ArrayList<Features> listFeaturesArray = new ArrayList<>();
    private CircleIndicator3 indicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helloTool = (HelloTool) getContext();

        listFeaturesArray.add(new Features().create(R.string.feature_desing_title,
                R.drawable.features_desing, R.string.feature_desing_info, true));
        listFeaturesArray.add(new Features().create(R.string.feature_tags_title,
                R.drawable.features_tags, R.string.feature_tags_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_create_title,
                R.drawable.feature_creates, R.string.feature_create_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_newFetures_title,
                R.drawable.feature_new, R.string.feature_newFetures_info, false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        //  view.findViewById(R.id.backFragment).setOnClickListener(v -> helloTool.backFragment(2));
        // view.findViewById(R.id.backupFragmentNext).setOnClickListener(v -> helloTool.nextFragment(2));
        listFeatures = view.findViewById(R.id.featuresViewPage);
        indicator = view.findViewById(R.id.indicatorViewPager);
        initFeaturesList();
        return view;

    }

    private void initFeaturesList() {
        FeaturesPageAdapter featuresPageAdapter = new FeaturesPageAdapter(requireActivity(), listFeaturesArray);
        listFeatures.setAdapter(featuresPageAdapter);

        indicator.setViewPager(listFeatures);

        featuresPageAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        listFeatures.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}