package mobileprogramming.devinwinardi.clevernote.ui.task;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TaskWithSubtasks{
    @Embedded
    public Task task;
    @Relation(parentColumn = "id",entityColumn = "taskId")
    public List<Subtask> subtasks;

    public TaskWithSubtasks(Task task, List<Subtask> subtasks){
        this.task = task;
        this.subtasks = subtasks;
    }
}
