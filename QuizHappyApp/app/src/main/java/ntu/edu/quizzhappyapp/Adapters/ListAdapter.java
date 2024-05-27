package ntu.edu.quizzhappyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import ntu.edu.quizzhappyapp.Activities.MainActivity;
import ntu.edu.quizzhappyapp.Activities.QuestionActivity;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.Models.Questions;
import ntu.edu.quizzhappyapp.Models.TypeQues;
import ntu.edu.quizzhappyapp.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context context;
    ArrayList<TypeQues> datas;
    LayoutInflater mInfater;
    int userID;
    public ListAdapter(Context context, ArrayList<TypeQues> datas,int userID) {
        this.context = context;
        this.datas = datas;
        this.userID = userID;
        mInfater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(viewItem, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        //Lấy phần tử tại vị trí position
        TypeQues type = datas.get(position);
        //Đặt các thuộc tính hiển thị
        holder.nameCaption.setText(type.nameType);
        //Đặt ảnh
        String packageName = holder.itemView.getContext().getPackageName();
        //Lấy id ảnh thông qua tên
        String nameFile = type.getImage();
        int imageID = holder.itemView.getResources().getIdentifier(nameFile, "mipmap", packageName);
        holder.imgView.setImageResource(imageID);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView nameCaption;
        ImageView imgView;
        Context mContext;
        QuizDBHelper db;

        public ListViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            nameCaption = itemView.findViewById(R.id.nameType);
            imgView = itemView.findViewById(R.id.img_Type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = new QuizDBHelper(mContext);
                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    if (position != RecyclerView.NO_POSITION) {
                        int ID = db.getTypeID(position + 1);
                        if (ID != -1) {
                            Intent questions = new Intent(mContext, QuestionActivity.class);
                            bundle.putInt("typeID", ID);
                            bundle.putInt("userID",userID);
                            questions.putExtras(bundle);
                            mContext.startActivity(questions);
                        }
                    }
                }
            });
        }
    }
}
