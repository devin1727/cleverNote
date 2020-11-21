package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Task.class, Subtask.class}, version = 2)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;
    public abstract TaskDao taskDao();
    public abstract SubtaskDao subtaskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new TaskDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private SubtaskDao subtaskDao;
        private PopulateDbAsyncTask(TaskDatabase db) {
            taskDao = db.taskDao();
            subtaskDao = db.subtaskDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insert(new Task("Task 1"));
            taskDao.insert(new Task("Task 2"));
            taskDao.insert(new Task("Task 3"));
            return null;
        }
    }
}
