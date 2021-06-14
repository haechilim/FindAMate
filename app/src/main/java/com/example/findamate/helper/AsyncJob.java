package com.example.findamate.helper;

import android.os.Handler;
import android.os.Message;

public abstract class AsyncJob<Params, Progress, Result> extends Thread implements Runnable {
    protected void onPreExecute() {};
    abstract protected Result doInBackground(Params... params);
    protected void onPostExecute(Result result) {};
    protected void onCancell() {};

    Params[] params;
    static final int MESSAGE_POST_RESULT = 30;

    enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    private Status status = Status.PENDING;
    private boolean isCancel = false;
    final InternalHandler internalHandler = new InternalHandler();

    public AsyncJob()
    {

    }

    public AsyncJob<Params, Progress, Result> execute(Params... params)
    {
        if (status != Status.PENDING) {
            switch (status) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
                default:
                    break;
            }
        }
        status = Status.RUNNING;

        this.params = params;
        onPreExecute();
        start();
        return this;
    }

    public final Status getStatus() {
        return status;
    }

    public final boolean isCancelled() {
        return isCancel;
    }

    public final boolean cancel() {
        isCancel = true;
        return isCancel;
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        isCancel = true;
        return isCancel;
    }

    public void run()
    {
        final Result result = doInBackground(params);

        Message msg = internalHandler.obtainMessage(MESSAGE_POST_RESULT);
        msg.obj = new AsyncResult(this, result);
        internalHandler.sendMessage(msg);
    }

    protected void onCancell(Result result)
    {
        onCancell();
    }

    private void finish(Result result)
    {
        if (isCancelled()) {
            onCancell(result);
        }
        else {
            onPostExecute(result);
        }
        status = Status.FINISHED;
    }

    private static class InternalHandler extends Handler {
        @SuppressWarnings({"unchecked"})
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    AsyncResult aResult = (AsyncResult)msg.obj;
                    aResult.thread.finish(aResult.result);
                    break;
            }
        }
    }

    @SuppressWarnings({"rawtypes"})
    static class AsyncResult {
        final AsyncJob thread;
        final Object result;
        public AsyncResult(AsyncJob thread, Object result)
        {
            this.thread = thread;
            this.result = result;
        }
    }
}