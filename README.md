Java_TaskQueue
==============

TaskQueue in Java. You can use Async API in each task.

How to add this to your project
==========
1. Copy m2repo to your project
2. Add it to repositories in build.gradle

```
repositories {
  maven {
    url "path-to-m2repo"
  }
}
```

3. Add the following entry to dependencies in build.gradle

```
dependencies {
  compile 'jp.fkmsoft.libs:TaskQueue:1.0.0'
}
```



How to use this in your code.
=========

```
TaskQueue<String> queue = new TaskQueueImpl<String>();
// add tasks
for (int i = 0 ; i < 3 ; ++i) {
  final int num = i;
  queue.add(new Task<String>() {
      
    @Override
    public void execute(TaskQueue<String> queue) {
      // Call Async API here
      queue.notifyResult("item" + num);
    }
  });
}

// Execute 
queue.execute(new TaskCallback<String>() {
  @Override
  public void onSuccess(List<String> result) {
    // You can use a list of <T> 
  }

  @Override
  public void onError(List<String> result, Exception e) {
    // Handle error
  }
});
```


