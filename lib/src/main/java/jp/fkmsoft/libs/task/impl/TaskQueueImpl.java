package jp.fkmsoft.libs.task.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jp.fkmsoft.libs.task.TaskQueue;

public class TaskQueueImpl<T> implements TaskQueue<T> {

    private final Queue<Task<T>> mTaskList = new LinkedList<Task<T>>();
    private List<T> mResult;
    private TaskCallback<T> mCallback;
    
    @Override
    public void add(Task<T> task) {
        mTaskList.add(task);
    }

    @Override
    public void execute(TaskQueue.TaskCallback<T> callback) {
        mCallback = callback;
        mResult = new ArrayList<T>(mTaskList.size());
        execute();
    }

    @Override
    public void notifyResult(T result) {
        mResult.add(result);
        mTaskList.poll();
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
        mTaskList.peek().execute(this);
    }
}
