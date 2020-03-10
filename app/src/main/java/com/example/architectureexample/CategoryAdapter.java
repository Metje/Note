package com.example.architectureexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryHolder> {
    private OnItemClickListener listener;

    public CategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {

            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getCategory().equals(newItem.getCategory());
        }
    };


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentNote = getItem(position);
        holder.textViewCategory.setText(currentNote.getCategory());
    }


    public Category getAt(int position) {
        return getItem(position);
    }


    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategory;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Category note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
