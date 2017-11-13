package com.example.dell.mydemostreamred5.ui.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;


import com.example.dell.mydemostreamred5.MainActivity;
import com.example.dell.mydemostreamred5.R;
import com.example.dell.mydemostreamred5.ui.fragment.PublishFragment;
import com.example.dell.mydemostreamred5.ui.fragment.SubcribesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nganq on 4/18/2017.
 */

public class ListviewNavMenu extends ExpandableListView {

    private Boolean bl = true;
    private MainActivity activity;
    List<NavTittleHeader> listDataHeader;
    HashMap<NavTittleHeader, List<NavMenuMdl>> listDataChild;

    public ListviewNavMenu(Context context) {
        super(context);
        this.activity = (MainActivity) context;
        init();
    }

    public ListviewNavMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (MainActivity) context;
        init();

    }

    public ListviewNavMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.activity = (MainActivity) context;
        init();

    }

    public void init() {
        listDataChild = new HashMap<NavTittleHeader, List<NavMenuMdl>>();
        listDataHeader = new ArrayList<>();


        listDataHeader.add(new NavTittleHeader("Publish"));

        List<NavMenuMdl> listNavMenuPerson = new ArrayList();
        listNavMenuPerson.add(new NavMenuMdl(R.drawable.thuam_mp3, "Publish"));

        listNavMenuPerson.add(new NavMenuMdl(R.drawable.thuam_mp3, "Subcribe"));

        listDataChild.put(listDataHeader.get(0), listNavMenuPerson);
        NavMenuAdapter adapter = new NavMenuAdapter(activity, listDataHeader, listDataChild);
        setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandGroup(i);
        }

        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    if (childPosition == 0) {
                        activity.callFragment(PublishFragment.newInstance());

                    }
                    if (childPosition == 1) {
                        activity.callFragment(SubcribesFragment.newInstance());

                    }

                }

                activity.drawerAction(null);
                return false;
            }

        });
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
