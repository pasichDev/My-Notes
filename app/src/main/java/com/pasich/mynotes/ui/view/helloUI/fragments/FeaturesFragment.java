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


public class FeaturesFragment extends Fragment {

    private HelloTool helloTool;
    private ViewPager2 listFeatures;
    private FeaturesPageAdapter featuresPageAdapter;
    private ArrayList<Features> listFeaturesArray = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helloTool = (HelloTool) getContext();

        listFeaturesArray.add(new Features().create(R.string.feature_desing_title,
                R.drawable.features_desing, R.string.feature_desing_info, false));
        listFeaturesArray.add(new Features().create(R.string.feature_tags_title,
                R.drawable.features_tags, R.string.feature_tags_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_create_title,
                R.drawable.feature_creates, R.string.feature_create_info, false));

        listFeaturesArray.add(new Features().create(R.string.feature_newFetures_title,
                R.drawable.feature_new, R.string.feature_newFetures_info, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        view.findViewById(R.id.backFragment).setOnClickListener(v -> helloTool.backFragment(2));
        view.findViewById(R.id.backupFragmentNext).setOnClickListener(v -> helloTool.nextFragment(2));
        listFeatures = view.findViewById(R.id.featuresViewPage);
        initFeaturesList();
        return view;

    }

    private void initFeaturesList() {
        featuresPageAdapter = new FeaturesPageAdapter(requireActivity(), listFeaturesArray);
        listFeatures.setAdapter(featuresPageAdapter);

    }
}