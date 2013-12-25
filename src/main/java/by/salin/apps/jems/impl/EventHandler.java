/*
 * Copyright 2013 Alexandr Salin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package by.salin.apps.jems.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import by.salin.apps.jems.Channel;
import by.salin.apps.jems.EventHandlerCallback;

/**
 * Created by Alexandr.Salin on 15.12.13.
 */
public class EventHandler implements Channel<Event>, Handler.Callback
{
	private static final String TAG = EventHandler.class.getSimpleName();
	private static final String EXTRA_DATA_HANDLER = "by.salin.apps.jems.EXTRA_DATA_HANDLER";
	private EventHandlerCallback callback;
	private Handler handler;
	private boolean isMainThread;
	private BackgroundHandler bgThread;

	public EventHandler(EventHandlerCallback callback)
	{
		super();
		initBGThread();
		isMainThread = Looper.getMainLooper() == Looper.myLooper();
		if (isMainThread)
		{
			handler = new Handler(this);
		}
		this.callback = callback;
	}

	private void initBGThread()
	{
		bgThread = new BackgroundHandler(this);
		bgThread.start();
	}

	/**
	 * migrate to HandlerThread for event-driven between thread to thread
	 * in native code line 52
	 * https://android.googlesource.com/platform/frameworks/native/+/588d5c8280c89c646aa7c8c54900225ee04176ea/libs/utils/Looper.cpp
	 * Could not create wake pipe. or
	 * Could not create epoll instance.
	 *
	 * @param message
	 */
	@Override
	public void dispatch(Event message)
	{
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_DATA_HANDLER, message);
		msg.setData(bundle);
		if (isMainThread)
		{
			handler.sendMessage(msg);
		}
		else
		{
			bgThread.sendMessage(msg);
		}
	}

	@Override
	public boolean handleMessage(Message msg)
	{
		boolean result = false;
		if (callback != null)
		{
			Event event = (Event) msg.getData().getSerializable(EXTRA_DATA_HANDLER);
			callback.onEvent(event);
			Log.w(TAG, event.toString());
			result = true;
		}
		return result;
	}

}
