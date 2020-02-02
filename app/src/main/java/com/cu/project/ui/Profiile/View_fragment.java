package com.cu.project.ui.Profiile;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.ui.login.loginActivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.cu.project.R;
import com.cu.project.ui.Upload.UploadActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class View_fragment extends Fragment {
    Button upload_btn;
    TextView txt;

    String token = loginActivity.gettoken();
    View v;

    ListView list , list1 , list2 , list3;



    SwipeRefreshLayout swipeRefreshLayout;

    static int count1 =0;
    int count2 =0;
    int count3 = 0;
    int count4 = 0;
    private ArrayList<SubjectData> listitems =  new ArrayList<>();
    private ArrayList<SubjectData> listitems1 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems2 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems3 =  new ArrayList<>();
    HashMap<String, ArrayList>  map;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        v = inflater.inflate(R.layout.fragment_view_fragment, container, false);


        swipeRefreshLayout = v.findViewById(R.id.swipe);


        list = v.findViewById(R.id.listView1);
        list1 = v.findViewById(R.id.listView2);

        list3 = v.findViewById(R.id.listView4);

        list2 = v.findViewById(R.id.listView3);

        list.setAdapter(null);
        list1.setAdapter(null);
        list2.setAdapter(null);
        list3.setAdapter(null);

          map = new HashMap<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ApigetPaper apigetPaper = new ApigetPaper();

                        try {
                           map = apigetPaper.execute(token).get();
                            listitems = map.get("1");
                            listitems1 = map.get("2");
                            listitems2 = map.get("3");
                            listitems3 = map.get("4");
                            if (listitems.size() == 0) {
                                txt = v.findViewById(R.id.noitems1id);
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                list.setAdapter(null);
                                PublicationAdapter customAdapter = new PublicationAdapter(getContext(), listitems);
                                list.setAdapter(customAdapter);
                                count1 = list.getCount();
                                list.invalidateViews();
                                txt = v.findViewById(R.id.noitems1id);
                                txt.setVisibility(View.INVISIBLE);
                                ListUtils.setDynamicHeight(list);
                            }
                            if (listitems1.size() == 0) {
                                txt = v.findViewById(R.id.noitems1id1);
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                PatentAdapter customAdapter1 = new PatentAdapter(getContext(), listitems1);
                                list1.setAdapter(customAdapter1);
                                ListUtils.setDynamicHeight(list1);
                                list1.invalidateViews();
                                count2 = list1.getCount();

                                txt = v.findViewById(R.id.noitems1id1);
                                txt.setVisibility(View.INVISIBLE);
                                list1.invalidateViews();
                            }


                            if (listitems2.size() == 0) {
                                txt = v.findViewById(R.id.noitems1id2);
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                ProjectAdapter customAdapter2 = new ProjectAdapter(getContext(), listitems2);
                                list2.setAdapter(customAdapter2);
                                ListUtils.setDynamicHeight(list2);
                                count3 = list2.getCount();
                                txt = v.findViewById(R.id.noitems1id2);
                                txt.setVisibility(View.INVISIBLE);
                                list2.invalidateViews();
                            }


                            // for the list of patents

                            if (listitems3.size() == 0) {
                                txt = v.findViewById(R.id.noitems1id3);
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                Honors_and_AwardAdapter customAdapter3 = new Honors_and_AwardAdapter(getContext(), listitems3);
                                list3.setAdapter(customAdapter3);
                                list3.invalidateViews();
                                ListUtils.setDynamicHeight(list3);
                                list3.invalidateViews();
                                txt = v.findViewById(R.id.noitems1id3);
                                txt.setVisibility(View.INVISIBLE);
                                count4 = list3.getCount();
                            }


                            swipeRefreshLayout.setRefreshing(false);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                }, 1000);
            }
        });





        upload_btn = v.findViewById(R.id.uploadbtn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , UploadActivity.class);
                startActivity(intent);
            }
        });





        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }






}