package rsantillanc.sanjoylao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {

    private static CommentsFragment instance;
    private ListView mListView;
    private EditText etInput;
    private Button btSend;

    private List<Object> comments;



    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(String param1, String param2) {
           instance =  new CommentsFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_comments, container, false);

        initComponents(v);
        return v;
    }

    private void initComponents(View v) {
        /*Get views*/
        mListView = (ListView)v.findViewById(R.id.lv_comentarios);
        etInput = (EditText)v.findViewById(R.id.et_input_comment);
        btSend = (Button)v.findViewById(R.id.btn_send_comment);

        /*Setup*/
        CommentModel oModel = new CommentModel();
        comments =  oModel.testData();
        mListView.setAdapter(new ListViewAdapter(getActivity(),comments, Const.COMMENTS));

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Sending message...",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
