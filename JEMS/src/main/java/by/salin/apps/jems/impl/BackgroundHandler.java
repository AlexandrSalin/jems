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

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Alexandr.Salin on 20.12.13.
 */
public class BackgroundHandler extends HandlerThread
{
	private static final String TAG = BackgroundHandler.class.getSimpleName();
	private Handler mHandler;
	private Handler.Callback callback;

	BackgroundHandler(Handler.Callback callback)
	{
		super(TAG);
		this.callback = callback;
	}

	@Override
	protected void onLooperPrepared()
	{
		super.onLooperPrepared();
		Looper mLooper = getLooper();
		if (mLooper != null)
		{
			mHandler = new Handler(getLooper(), callback);
		}
	}

	public void sendMessage(Message msg){
		if (mHandler == null){
			throw new IllegalStateException("Background handler for messaging between threads is null");
		}
		mHandler.sendMessage(msg);
	}
}
