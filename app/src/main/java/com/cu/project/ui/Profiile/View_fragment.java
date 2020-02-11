package com.cu.project.ui.Profiile;
import com.cu.project.APIHelper.ApigetPaper;
import com.cu.project.APIHelper.Asyncresponsegetpaper;
import com.cu.project.ui.login.loginActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cu.project.R;
import com.cu.project.ui.Upload.UploadActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class View_fragment extends Fragment implements Asyncresponsegetpaper {
    private Button upload_btn;
    private TextView txt;


//    String token = loginActivity.gettoken();
    View v;

    private ListView list , list1 , list2 , list3;
    private static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    private SwipeRefreshLayout swipeRefreshLayout;

    int count1 =0;
    int count2 =0;
    int count3 = 0;
    int count4 = 0;
    private ArrayList<SubjectData> listitems =  new ArrayList<>();
    private ArrayList<SubjectData> listitems1 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems2 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems3 =  new ArrayList<>();
    private ArrayList<SubjectData> listitems4 =  new ArrayList<>();
    ApigetPaper apigetPaper;
    HashMap<String, ArrayList>  map;
    HashMap<String, ArrayList>  map1;
    Gson gson;
     @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        v = inflater.inflate(R.layout.fragment_view_fragment, container, false);
           gson = new Gson();

        swipeRefreshLayout = v.findViewById(R.id.swipe);
         sharedpreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
         editor = sharedpreferences.edit();

        list = v.findViewById(R.id.listView1);
        list1 = v.findViewById(R.id.listView2);

        list3 = v.findViewById(R.id.listView4);

        list2 = v.findViewById(R.id.listView3);

        list.setAdapter(null);
        list1.setAdapter(null);
        list2.setAdapter(null);
        list3.setAdapter(null);
         apigetPaper = new ApigetPaper(  );
         apigetPaper.asyncresponsegetpaper = this;
          map = new HashMap<>();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(sharedpreferences.getLong("Exp_time", 0)<System.currentTimeMillis())
                    editor.clear();
                String token = sharedpreferences.getString("Token", "");
                if(token.equals("")){
                    Log.e(TAG, "gettoken: token doesnot exists or expired " );
                }

                apigetPaper.execute(token);

            }
        });



//
//         String storedHashMapString = sharedpreferences.getString("apigetpaper", "");
//        if(storedHashMapString.equals("")){
//            Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();
//        }
//        else{
////            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList> >(){}.getType();
////            map1 = gson.fromJson(storedHashMapString, type);
////            listitems4 = map1.get("1");
////            SubjectData data = new SubjectData();
////            data  = listitems4.get(1);
////            Log.e("cache testing",data.getDate()+data.getId());
//        }

//        swipeRefreshLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//            }
//        });
//        swipeRefreshLayout.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View view, DragEvent dragEvent) {
//                ApigetPaper apigetPaper = new ApigetPaper();
//
//                apigetPaper.asyncresponsegetpaper = (Asyncresponsegetpaper) v.getContext();
//
//                apigetPaper.execute(token);
//                return false;
//            }
//        });





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

    @Override
    public void processFinishgetpaper(HashMap map1) {
        String hashMapString = gson.toJson(map1);

        editor.putString("apigetpaper", hashMapString);
        editor.commit();

        try{
        listitems = (ArrayList<SubjectData>)map1.get("1");
        listitems1 = (ArrayList<SubjectData>)map1.get("2");
        listitems2 = (ArrayList<SubjectData>)map1.get("3");
        listitems3 = (ArrayList<SubjectData>)map1.get("4");
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
            setDynamicHeight(list);

        }
        if (listitems1.size() == 0) {
            txt = v.findViewById(R.id.noitems1id1);
            txt.setVisibility(View.VISIBLE);
        } else {
            PatentAdapter customAdapter1 = new PatentAdapter(getContext(), listitems1);
            list1.setAdapter(customAdapter1);
            setDynamicHeight(list1);
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
            setDynamicHeight(list2);
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
            setDynamicHeight(list3);
            list3.invalidateViews();
            txt = v.findViewById(R.id.noitems1id3);
            txt.setVisibility(View.INVISIBLE);
            count4 = list3.getCount();
        }


        swipeRefreshLayout.setRefreshing(false);

        apigetPaper = new ApigetPaper(  );
        apigetPaper.asyncresponsegetpaper = this;
        }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }




    }

        public   void setDynamicHeight(ListView mListView) {
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