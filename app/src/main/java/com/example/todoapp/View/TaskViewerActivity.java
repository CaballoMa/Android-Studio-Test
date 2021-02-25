package com.example.todoapp.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.todoapp.Model.Task;
import com.example.todoapp.R;
import com.example.todoapp.vm.TaskViewerViewModel;
import com.example.todoapp.databinding.ActivityTaskViewerBinding;

public class TaskViewerActivity extends AppCompatActivity {

    public static final String KEY_TASK = "task";

    /**
     * 在这个极简的例子中，Activity里面没有什么逻辑，layout 文件也是简单的数据绑定，所有的业务逻辑都在 ViewModel 里面，
     * 这样的好处是 ViewModel 可以更灵活，同时可测试性更好。
     * onCreate 里面的代码可以认为都是些模板代码。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 拿到上一个 activity 传过来的 task
        Task task = (Task) getIntent().getSerializableExtra(KEY_TASK);

        // 创建一个 ViewModel
        TaskViewerViewModel viewModel = new TaskViewerViewModel(task);

        // Data binding 的模板代码
        ActivityTaskViewerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_task_viewer);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }
}