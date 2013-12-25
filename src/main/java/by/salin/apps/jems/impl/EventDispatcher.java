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

import by.salin.apps.jems.Channel;
import by.salin.apps.jems.DynamicRouter;

import java.util.*;

/**
 * Created by Alexandr.Salin on 15.12.13.
 */
public class EventDispatcher implements DynamicRouter<Event>
{
	private Map<Class<? extends Event>, Set<EventHandler>> handlers;

	public EventDispatcher()
	{
		handlers = Collections.synchronizedMap(new HashMap<Class<? extends Event>, Set<EventHandler>>());
	}

	@Override
	public void registerChannel(Class<? extends Event> contentType, Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
			Set<EventHandler> eventHandlerSet = handlers.get(contentType);
			if (eventHandlerSet == null)
			{
				eventHandlerSet = Collections.synchronizedSet(new HashSet<EventHandler>());
			}
			eventHandlerSet.add((EventHandler) channel);
			handlers.put(contentType, eventHandlerSet);
		}
	}

	@Override
	public void unregisterChannel(Class<? extends Event> contentType, Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
			Set<EventHandler> eventHandlerSet = handlers.get(contentType);
			if (eventHandlerSet == null)
			{
				return;
			}
			eventHandlerSet.remove((EventHandler) channel);
		}
	}

	@Override
	public void unregisterChannel(Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
			for (Set<EventHandler> eventHandlerSet : handlers.values())
			{
				if (eventHandlerSet == null)
				{
					continue;
				}
				eventHandlerSet.remove((EventHandler) channel);
			}
		}
	}

	@Override
	public void dispatch(Event content)
	{
		synchronized (handlers)
		{
			Set<EventHandler> eventHandlerSet = handlers.get(content.getClass());
			if (eventHandlerSet == null)
			{
				return;
			}
			for (EventHandler handler : eventHandlerSet)
			{
				if (handler == null)
				{
					continue;
				}
				handler.dispatch(content);
			}
		}
	}
}
