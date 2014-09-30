package jp.fkmsoft.libs.task.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jp.fkmsoft.libs.task.TaskQueue;

public class TaskQueueImpl<T> implements TaskQueue<T> {

    private final List<Task<T>> mTaskList = new LinkedList<Task<T>>();
    private List<T> mResult;
    private TaskCallback<T> mCallback;
    
    @Override
    public void add(Task<T> task) {
        mTaskList.add(task);
    }

    @Override
    public void execute(TaskQueue.TaskCallback<T> callback) {
        mCallback = callback;
        mResult = new ArrayList<>(mTaskList.size());
        execute();
    }

    @Override
    public void notifyResult(T result) {
        mResult.add(result);
        mTaskList.remove(0);
        execute();
    }

    @Override
    public void notifyError(Exception e) {
        mCallback.onError(mResult, e);
    }
    
    private void execute() {
        if (mTaskList.isEmpty()) {
            mCallback.onSuccess(mResult);
            return;
        }
        mTaskList.get(0).execute(this);
    }
}
