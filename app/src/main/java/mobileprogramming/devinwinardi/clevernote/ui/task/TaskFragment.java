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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaskFragment extends Fragment {

    public static final int ADD_TASK_REQUEST = 1;

    private TaskViewModel taskViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);

        setHasOptionsMenu(true);

        ExtendedFloatingActionButton buttonAddTask = root.findViewById(R.id.extended_fab_task);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Task deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                SubtaskFragment subtaskFragment = new SubtaskFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Task", task);
                subtaskFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.task_container, subtaskFragment, "subtaskFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTaskActivity.EXTRA_TITLE);

            Task task = new Task(title);
            taskViewModel.insert(task);
            Toast.makeText(getActivity(), "Task saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasks_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_tasks:
                taskViewModel.deleteAllTasks();
                Toast.makeText(getActivity(), "All tasks deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}