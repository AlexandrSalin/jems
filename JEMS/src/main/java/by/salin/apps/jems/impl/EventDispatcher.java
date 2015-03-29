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
import by.salin.apps.logger.LOG;

import java.util.*;

/**
 * Created by Alexandr.Salin on 15.12.13.
 */
public class EventDispatcher implements DynamicRouter<Event>
{
	private final Map<Class<? extends Event>, Set<EventHandler>> handlers;

	public EventDispatcher()
	{
		handlers = Collections.synchronizedMap(new HashMap<Class<? extends Event>, Set<EventHandler>>());
	}

	@Override
	public void registerChannel(Class<? extends Event> contentType, Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
            LOG.I(String.format("Try register new channel: {%s}, for event: {%s}", channel,contentType));
			Set<EventHandler> eventHandlerSet = handlers.get(contentType);
			if (eventHandlerSet == null)
			{
                LOG.I(String.format("Create new EventHandler set, for event: {%s}", contentType));
				eventHandlerSet = Collections.synchronizedSet(new HashSet<EventHandler>());
			}
			eventHandlerSet.add((EventHandler) channel);
			handlers.put(contentType, eventHandlerSet);
            LOG.I(String.format("Handlers state after register: {%s}", handlers.size()));
		}
	}

	@Override
	public void unregisterChannel(Class<? extends Event> contentType, Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
            LOG.I(String.format("Try unregister new channel: {%s}, for event: {%s}", channel,contentType));
			Set<EventHandler> eventHandlerSet = handlers.get(contentType);
			if (eventHandlerSet == null)
			{
				return;
			}
            LOG.I(String.format("Remove channel: {%s}", channel));
			eventHandlerSet.remove(channel);
            LOG.I(String.format("Handlers state after unregister: {%s}", handlers.size()));
		}
	}

	@Override
	public void unregisterChannel(Channel<? extends Event> channel)
	{
		synchronized (handlers)
		{
            LOG.I(String.format("Try unregister new channel: {%s}, for all events", channel));
			for (Set<EventHandler> eventHandlerSet : handlers.values())
			{
				if (eventHandlerSet == null)
				{
					continue;
				}
                LOG.I(String.format("Remove channel: {%s}", channel));
				eventHandlerSet.remove(channel);
			}
            LOG.I(String.format("Handlers state after unregister: {%s}", handlers.size()));
		}
	}

	@Override
	public void dispatch(Event content)
	{
		synchronized (handlers)
		{
            LOG.I(String.format("start dispatch , handlers set: {%s}", handlers.size()));
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

    public boolean stillNeeded(EventHandler handler) {
        boolean result = false;
        synchronized (handlers) {
            LOG.I(String.format("start check handler still need: {%s}, from set: {%s}",handler, handlers.size()));
            try {
                for (Set<EventHandler> eventHandlerSet : handlers.values()) {
                    if (eventHandlerSet == null) {
                        continue;
                    }
                    for (EventHandler eventHandler : eventHandlerSet) {
                        LOG.I(String.format("Compare: item from set {%s}, with item: {%s}",eventHandler,handler));
                        if (eventHandler.equals(handler)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.E(e.toString(), e);
            }
        }
        return false;
    }
}
