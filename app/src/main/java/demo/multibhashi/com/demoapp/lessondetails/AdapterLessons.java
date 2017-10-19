package demo.multibhashi.com.demoapp.lessondetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.multibhashi.com.demoapp.R;
import demo.multibhashi.com.demoapp.network.models.Lesson;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class AdapterLessons extends RecyclerView.Adapter<AdapterLessons.ViewHolder> {

    private List<Lesson> listLessons;

    public AdapterLessons() {
        listLessons = new ArrayList<Lesson>();
    }

    public AdapterLessons(List<Lesson> listLessons) {
        this.listLessons = listLessons;
    }

    public void update(List<Lesson> listLessons) {
        this.listLessons = listLessons;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.rv_item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = listLessons.get(position);
        holder.tvLessonType.setText(lesson.getType());
        holder.tvLessonDesc.setText(lesson.getConceptName());
    }

    @Override
    public int getItemCount() {
        return listLessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLessonType;
        private TextView tvLessonDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLessonType = itemView.findViewById(R.id.tv_lesson_type);
            tvLessonDesc = itemView.findViewById(R.id.tv_lesson_desc);
        }
    }
}
