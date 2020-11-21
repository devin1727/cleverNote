package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.ui.cs.mobileprogramming.devinwinardi.clevernote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SubtaskFragment extends Fragment {
    public static final int ADD_SUBTASK_REQUEST = 1;

    private Task task;

    private TaskViewModel taskViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_subtask, container, false);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        task = (Task) bundle.getParcelable("Task");

        FloatingActionButton buttonAddSubtask = root.findViewById(R.id.fab_subtask);
        buttonAddSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSubtaskActivity.class);
                startActivityForResult(intent, ADD_SUBTASK_REQUEST);
            }
        });

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final SubtaskAdapter subtaskAdapter = new SubtaskAdapter();
        recyclerView.setAdapter(subtaskAdapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTaskWithSubtasks().observe(getViewLifecycleOwner(), new Observer<List<TaskWithSubtasks>>() {
            @Override
            public void onChanged(@Nullable List<TaskWithSubtasks> taskWithSubtasks) {
                int index = -1;
                for (int i = 0; i < taskWithSubtasks.size();i++){
                    Task current_task = taskWithSubtasks.get(i).task;
                    if (task.getId() == current_task.getId()){
                        index = i;
                    }
                }
                subtaskAdapter.submitList(taskWithSubtasks.get(index).subtasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(subtaskAdapter.getSubtaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Subtask finished", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SUBTASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddSubtaskActivity.EXTRA_TITLE);

            Subtask subtask = new Subtask(title);
            taskViewModel.insert(task, subtask);
            Toast.makeText(getActivity(), "Subtask saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Subtask not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.subtasks_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_subtasks:
                Toast.makeText(getActivity(), "All subtasks deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
