package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private SubtaskDao subtaskDao;
    private LiveData<List<Task>> allTasks;
    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        subtaskDao = database.subtaskDao();
        allTasks = taskDao.getAllTasks();
    }
    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }
    public void insert(Task task, Subtask subtask) {
        SubtaskParams subtaskParams = new SubtaskParams(task, subtask);
        new InsertSubtaskAsyncTask(taskDao).execute(subtaskParams);
    }
    public void update(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }
    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }
    public void delete(Subtask subtask) {
        new DeleteSubtaskAsyncTask(subtaskDao).execute(subtask);
    }
    public void deleteAllTasks() {
        new DeleteAllTasksAsyncTask(taskDao).execute();
    }
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }
    private static class InsertSubtaskAsyncTask extends AsyncTask<SubtaskParams, Void, Void> {
        private TaskDao taskDao;
        private InsertSubtaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(SubtaskParams... subtaskParams) {
            taskDao.insertSubtaskForTask(subtaskParams[0].task,subtaskParams[0].subtask);
            return null;
        }
    }
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
    private static class DeleteSubtaskAsyncTask extends AsyncTask<Subtask, Void, Void> {
        private SubtaskDao subtaskDao;
        private DeleteSubtaskAsyncTask(SubtaskDao subtaskDao) {
            this.subtaskDao = subtaskDao;
        }
        @Override
        protected Void doInBackground(Subtask... subtasks) {
            subtaskDao.delete(subtasks[0]);
            return null;
        }
    }
    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private DeleteAllTasksAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTasks();
            return null;
        }
    }
}

class SubtaskParams{
    Subtask subtask;
    Task task;

    SubtaskParams(Task task, Subtask subtask){
        this.subtask = subtask;
        this.task = task;
    }
}
