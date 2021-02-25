package com.example.todoapp.vm; // 注意包名用全小写，类名用驼峰格式： ClassName

import android.graphics.Color;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.todoapp.Model.Task;

public class TaskViewerViewModel extends ViewModel {

    /**
     * ViewModel 里面一般会包含一个或多个 model，在这个类里面 task 就是 model.
     */
    public final Task task;

    public TaskViewerViewModel(Task task) {
        this.task = task;
    }

    /**
     * ViewModel 决定了怎么展示这个 model, 它是 View 的 model。
     * 比如说：这个地方可以可以改成 "A to-do item:" + task.getText();
     * view 就看起来不一样了，但是 model 和 view 都没改动，其实是 ViewModel 处理了所有的逻辑。
     */
    public String getTaskSummary() {
        return task.getText();
    }

    /**
     * 这个地方同样的道理，ViewModel 决定了 View 长什么样，而不是把这些 业务逻辑 写到 View 里面去
     */
    public int getSummaryTextColor() {
        return task.getImp() ? Color.RED : Color.GRAY;
    }

    /**
     * 这个地方也可以改成显示一个星星的icon，或是什么其他的你想要的东西
     */
    public int importantHintVisibility() {
        return task.getImp() ? View.VISIBLE : View.GONE;
    }
}
