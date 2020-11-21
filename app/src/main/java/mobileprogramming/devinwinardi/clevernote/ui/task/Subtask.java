package mobileprogramming.devinwinardi.clevernote.ui.task;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity=Task.class,parentColumns = "id",childColumns = "taskId",onDelete = CASCADE))
public class Subtask{
    @PrimaryKey(autoGenerate = true)
    private int subtaskId;

    private int taskId;

    public String title;

    public Subtask(String title){
        this.title = title;
    }

    public int getSubtaskId() {
        return subtaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
