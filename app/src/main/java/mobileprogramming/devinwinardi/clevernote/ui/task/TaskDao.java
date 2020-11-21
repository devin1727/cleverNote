package mobileprogramming.devinwinardi.clevernote.ui.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class TaskDao {
    public void insertSubtaskForTask(Task task, Subtask subtask){
        subtask.setTaskId(task.getId());
        insert(subtask);
    }
    @Insert
    abstract void insert(Subtask subtask);
    @Insert
    abstract void insert(Task task);
    @Update
    abstract void update(Task task);
    @Delete
    abstract void delete(Task task);
    @Query("DELETE FROM task_table")
    abstract void deleteAllTasks();
    @Query("SELECT * FROM task_table")
    abstract LiveData<List<Task>> getAllTasks();
    @Transaction
    @Query("SELECT * FROM task_table")
    abstract LiveData<List<TaskWithSubtasks>> getTaskWithSubtasks();
}
