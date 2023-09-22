package com.example.taskmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.Adapter.ToDoAdapter;


public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {
   private ToDoAdapter adapter;

    public RecyclerViewTouchHelper(ToDoAdapter adapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position= viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder=new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.deleteTask(position);
                }
            });
            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else{
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            View itemView = viewHolder.itemView;


            int backgroundColor = (dX > 0) ? Color.RED : Color.GREEN;


            RectF background = new RectF(
                    itemView.getLeft(),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );
            Paint paint = new Paint();
            paint.setColor(backgroundColor);
            c.drawRect(background, paint);


            Drawable icon1 = ContextCompat.getDrawable(itemView.getContext(), R.drawable.baseline_edit_note);
            Drawable icon2 = ContextCompat.getDrawable(itemView.getContext(), R.drawable.baseline_delete);

            if (icon1 != null&&dX>0) {
                int iconMargin = (itemView.getHeight() - icon2.getIntrinsicHeight()) / 2;
                int iconLeft, iconRight;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon2.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon2.getIntrinsicHeight();

                if (dX > 0) {
                    // Swiping to the right
                    iconLeft = itemView.getLeft() + iconMargin;
                    iconRight = iconLeft + icon2.getIntrinsicWidth();
                } else {
                    // Swiping to the left
                    iconRight = itemView.getRight() - iconMargin;
                    iconLeft = iconRight - icon2.getIntrinsicWidth();
                }

                icon2.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                icon2.draw(c);
            }else{
                if (icon2 != null) {
                    int iconMargin2 = (itemView.getHeight() - icon1.getIntrinsicHeight()) / 2;
                    int iconLeft2, iconRight2;
                    int iconTop2 = itemView.getTop() + (itemView.getHeight() - icon1.getIntrinsicHeight()) / 2;
                    int iconBottom2 = iconTop2 + icon1.getIntrinsicHeight();

                    if (dX > 0) {
                        // Swiping to the right
                        iconLeft2 = itemView.getLeft() + iconMargin2;
                        iconRight2 = iconLeft2 + icon1.getIntrinsicWidth();
                    } else {
                        // Swiping to the left
                        iconRight2 = itemView.getRight() - iconMargin2;
                        iconLeft2 = iconRight2 - icon1.getIntrinsicWidth();
                    }

                    icon1.setBounds(iconLeft2, iconTop2, iconRight2, iconBottom2);
                    icon1.draw(c);}
            }



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}}
