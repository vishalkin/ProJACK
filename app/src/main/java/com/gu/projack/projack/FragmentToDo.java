package com.gu.projack.projack;

/**
 * Created by vishal on 15/12/2016.
 */

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentToDo extends ListFragment {


    private SimpleCursorAdapter dataAdapter;

    private int project_id,task_id;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}
    View rootView;
    int mSelectedItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_to_do, container, false);

        displayListView();
        return rootView;
    }


    private void displayListView() {

        project_id=1;
        task_id=0;
        final SqlDbHandler db = new SqlDbHandler(getActivity());
        Cursor cursor = db.fetchAllTasks(project_id, task_id);

        // The desired columns to be bound
        String[] columns = new String[] {
                db.KEY_TASK_ID,
                db.KEY_TASK_NAME,
                db.KEY_TASK_DESC,
                db.KEY_STORY_POINT,
                db.KEY_PRIORITY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.task_id,
                R.id.task_name,
                R.id.task_desc,
                R.id.story_point,
                R.id.priority

        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_task_item,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String projectId =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Toast.makeText(getActivity(),projectId, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
