package jp.fkmsoft.libs.task;

import java.util.List;

import jp.fkmsoft.libs.task.impl.Task;

public interface TaskQueue<T> {
    void add(Task<T> task);
    
    public interface TaskCallback<T> {
        void onSuccess(List<T> result);
        void onError(List<T> result, Exception e);
    }
    void execute(TaskCallback<T> callback);
    void notifyResult(T result);
    void notifyError(Exception e);
}
