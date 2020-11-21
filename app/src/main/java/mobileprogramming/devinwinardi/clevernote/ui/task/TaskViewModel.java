package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<TaskWithSubtasks>> taskWithSubtasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }
    public void insert(Task task) {
        repository.insert(task);
    }
    public void insert(Task task, Subtask subtask) {
        repository.insert(task, subtask);
    }
    public void update(Task task) {
        repository.update(task);
    }
    public void delete(Task task) {
        repository.delete(task);
    }
    public void delete(Subtask subtask) {
        repository.delete(subtask);
    }
    public void deleteAllTasks() {
        repository.deleteAllTasks();
    }
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    public LiveData<List<TaskWithSubtasks>> getTaskWithSubtasks() {
        return taskWithSubtasks;
    }
}