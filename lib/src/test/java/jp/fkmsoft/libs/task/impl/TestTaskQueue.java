package jp.fkmsoft.libs.task.impl;

import java.util.List;

import jp.fkmsoft.libs.task.TaskQueue;
import jp.fkmsoft.libs.task.TaskQueue.TaskCallback;

import org.junit.Assert;
import org.junit.Test;

public class TestTaskQueue {
    
    @Test
    public void test_0000_add_3() {
        TaskQueue<String> queue = new TaskQueueImpl<String>();
        for (int i = 0 ; i < 3 ; ++i) {
            final int num = i;
            queue.add(new Task<String>() {
                
                @Override
                public void execute(TaskQueue<String> queue) {
                    queue.notifyResult("item" + num);
                }
            });
        }
        queue.execute(new TaskCallback<String>() {
            
            @Override
            public void onSuccess(List<String> result) {
                if (result.size() != 3) {
                    Assert.fail("Result must be 3");
                }
                String item = result.get(0);
                if (!"item0".equals(item)) {
                    Assert.fail("Item[0] must be item0");
                }
            }
            
            @Override
            public void onError(List<String> result, Exception e) {
                Assert.fail("Unexpected error" + e.getMessage());
            }
        });
    }

    @Test
    public void test_0001_Exception() {
        TaskQueue<String> queue = new TaskQueueImpl<String>();
        queue.add(new Task<String>() {
            
            @Override
            public void execute(TaskQueue<String> queue) {
                queue.notifyError(new RuntimeException());
            }
        });
        for (int i = 0 ; i < 3 ; ++i) {
            final int num = i;
            queue.add(new Task<String>() {
                
                @Override
                public void execute(TaskQueue<String> queue) {
                    queue.notifyResult("item" + num);
                }
            });
        }
        queue.execute(new TaskCallback<String>() {
            
            @Override
            public void onSuccess(List<String> result) {
                Assert.fail("onSuccess must not be called");
            }
            
            @Override
            public void onError(List<String> result, Exception e) {
                if (!(e instanceof RuntimeException)) {
                    Assert.fail("Unexpected Exception " + e.getMessage());
                }
            }
        });
    }    
}
