package com.pasich.mynotes.ui.helloUI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Features;
import com.pasich.mynotes.ui.helloUI.tool.HelloTool;
import com.pasich.mynotes.ui.helloUI.util.ZoomOutPageTransformer;
import com.pasich.mynotes.utils.adapters.FeaturesPageAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class FeaturesFragment extends Fragment {

    private HelloTool helloTool;
    private ViewPager2 listFeatures;
    private final ArrayList<Features> listFeaturesArray = new ArrayList<>();
    private CircleIndicator3 indicator;
    private Button skipButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helloTool = (HelloTool) getContext();

        listFeaturesArray.add(new Features().create(R.string.feature_desing_title,
                R.drawable.feature_desing, R.string.feature_desing_info, true));
        listFeaturesArray.add(new Features().create(R.string.feature_tags_title,
                R.drawable.feature_tag, R.string.feature_tags_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_create_title,
                R.drawable.feature_creates, R.string.feature_create_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_newFetures_title,
                R.drawable.feature_new, R.string.feature_newFetures_info, false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        listFeatures = view.findViewById(R.id.featuresViewPage);
        indicator = view.findViewById(R.id.indicatorViewPager);
        skipButton = view.findViewById(R.id.nextButton);
        initFeaturesList();
        return view;

    }

    private void initFeaturesList() {
        FeaturesPageAdapter featuresPageAdapter = new FeaturesPageAdapter(requireActivity(), listFeaturesArray);
        listFeatures.setAdapter(featuresPageAdapter);
        listFeatures.setPageTransformer(new ZoomOutPageTransformer());
        indicator.setViewPager(listFeatures);

        skipButton.setOnClickListener(v -> helloTool.nextFragment(2));
        featuresPageAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        listFeatures.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) skipButton.setText(R.string.Hello_next);
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}