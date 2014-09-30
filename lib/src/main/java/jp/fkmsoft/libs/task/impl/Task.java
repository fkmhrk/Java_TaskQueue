package jp.fkmsoft.libs.task.impl;

import jp.fkmsoft.libs.task.TaskQueue;

public abstract class Task<T> {
    abstract public void execute(TaskQueue<T> queue);
}
