package com.cu.project.ui.Profiile;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.ui.login.loginActivity;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;


import com.cu.project.R;
import com.cu.project.Util.ApiLogin;
import com.cu.project.ui.Upload.UploadActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class View_fragment extends Fragment {
    Button upload_btn;
    Toolbar toolbar;

    String token = loginActivity.gettoken();



    loginActivity activity;
    View v;

    ListView ls1 , ls2 ,ls3 , ls4;



    private RecyclerView recyclerView;
    List<Achievements> lachievements;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        v = inflater.inflate(R.layout.fragment_view_fragment, container, false);


        swipeRefreshLayout = v.findViewById(R.id.swipe);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        ApigetPaper apigetPaper = new ApigetPaper();
//
//                        try {
//                            Log.e("TOKEN  GENE" , token);
//                            lachievements = apigetPaper.execute(token).get();
//
//                            recyclerView = v.findViewById(R.id.recycler_view);
//                            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext() , lachievements);
//
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            recyclerView.setAdapter(recyclerViewAdapter);
//
//
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });


        //ls1 = v.findViewById(R.id.pub_list);
//        ls2 = v.findViewById(R.id.patent_list);
//        ls3 = v.findViewById(R.id.project_list);
//        ls4 = v.findViewById(R.id.honors_list);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ApigetPaper apigetPaper = new ApigetPaper();

                        try {
                            lachievements = apigetPaper.execute(token).get();




                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });

        final ListView list = v.findViewById(R.id.listView1);
        ArrayList<SubjectData> arrayList = new ArrayList<>();
        arrayList.add(new SubjectData("JAVA", "20/10/12",1));
        arrayList.add(new SubjectData("Python", "20/10/12", 2));
        arrayList.add(new SubjectData("Javascript", "20/10/12", 3));
        arrayList.add(new SubjectData("Cprograsfsafdsfsdfsfsdfsdfdsfsfdsfmming", "20/10/12", 4));
        arrayList.add(new SubjectData("Cplusplus", "20/10/12", 5));
        arrayList.add(new SubjectData("Android", "20/10/12", 6));
        arrayList.add(new SubjectData("Android", "20/10/12", 7));
        arrayList.add(new SubjectData("Android", "20/10/12", 8));

        CustomAdapter customAdapter = new CustomAdapter(getContext(), arrayList);
        list.setAdapter(customAdapter);


        upload_btn = v.findViewById(R.id.uploadbtn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , UploadActivity.class);
                startActivity(intent);
            }
        });

        ListUtils.setDynamicHeight(list);



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