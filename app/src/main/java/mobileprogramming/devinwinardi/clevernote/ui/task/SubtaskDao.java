package mobileprogramming.devinwinardi.clevernote.ui.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
interface SubtaskDao {
    @Insert
    void insert(Subtask subtask);
    @Delete
    void delete(Subtask subtask);
}
