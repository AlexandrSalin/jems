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

package by.salin.apps.jems;

import by.salin.apps.jems.impl.Event;
import by.salin.apps.jems.impl.EventDispatcher;
import by.salin.apps.jems.impl.EventHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 15.12.13.
 */
public class JEMS
{
	static private final Map<EventHandlerCallback, EventHandler> handlerRegistry = new ConcurrentHashMap<EventHandlerCallback, EventHandler>();
	static private final EventDispatcher dispatcher = new EventDispatcher();
	static private JEMS instance;

	static void defaultInit()
	{
		if (instance == null)
		{
			init();
		}
	}

	synchronized static void init()
	{
		if (instance == null)
		{
			instance = new JEMS();
		}
	}

	static public JEMS dispatcher()
	{
		defaultInit();
		return instance;
	}

	public void addListenerOnEvent(Class<? extends Event> eventClazz, EventHandlerCallback listener)
	{
		EventHandler handler = handlerRegistry.get(listener);
		if (handler != null)
		{
			dispatcher.registerChannel(eventClazz, handler);
		}
		else
		{
			synchronized (handlerRegistry)
			{
				handler = handlerRegistry.get(listener);
				if (handler != null)
				{
					dispatcher.registerChannel(eventClazz, handler);
				}
				else
				{
					dispatcher.registerChannel(eventClazz, new EventHandler(listener));
				}
			}
		}
	}

	public void removeListenerOnEvent(Class<? extends Event> eventClazz, EventHandlerCallback listener)
	{
		synchronized (handlerRegistry)
		{
			EventHandler handler = handlerRegistry.get(listener);
			if (handler != null)
			{
				dispatcher.unregisterChannel(eventClazz, handler);
				handlerRegistry.remove(handler);
			}
		}
	}

	public void removeListener(EventHandlerCallback listener)
	{
		synchronized (handlerRegistry)
		{
			EventHandler handler = handlerRegistry.get(listener);
			if (handler != null)
			{
				dispatcher.unregisterChannel(handler);
				handlerRegistry.remove(handler);
			}
		}
	}

	public void sendEvent(Event event)
	{
		dispatcher.dispatch(event);
	}
}
