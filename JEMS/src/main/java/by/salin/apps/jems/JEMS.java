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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import by.salin.apps.jems.impl.Event;
import by.salin.apps.jems.impl.EventDispatcher;
import by.salin.apps.jems.impl.EventHandler;
import by.salin.apps.logger.LOG;

/**
 * Created by root on 15.12.13.
 */
public class JEMS {
    public static final String TAG = JEMS.class.getSimpleName();
    static private final Map<EventHandlerCallback, EventHandler> handlerRegistry = new ConcurrentHashMap<EventHandlerCallback, EventHandler>();
    static private final EventDispatcher dispatcher = new EventDispatcher();
    static private JEMS instance;

    static void defaultInit() {
        
        if (instance == null) {
            init();
        }
    }

    synchronized static void init() {
        if (instance == null) {
            LOG.I("============= START INIT JEMS ===========");
            instance = new JEMS();
            LOG.I(String.format("============= JEMS - {%s} ===========", instance));
        }
    }

    static public JEMS dispatcher() {
        defaultInit();
        return instance;
    }

    public void addListenerOnEvent(Class<? extends Event> eventClazz, EventHandlerCallback listener) {
        LOG.I(String.format("Add listener : {%s} on Event : {%s}", eventClazz, listener));
        EventHandler handler = handlerRegistry.get(listener);
        if (handler != null) {
            LOG.I(String.format("Select already exists handler : {%s}", handler));
            dispatcher.registerChannel(eventClazz, handler);
        } else {
            synchronized (handlerRegistry) {
                handler = handlerRegistry.get(listener);
                if (handler != null) {
                    LOG.I(String.format("Select already exists handler : {%s}", handler));
                    dispatcher.registerChannel(eventClazz, handler);
                } else {
                    handler = new EventHandler(listener);
                    LOG.I(String.format("Create new handler : {%s}", handler));
                    dispatcher.registerChannel(eventClazz, handler);
                    handlerRegistry.put(listener, handler);
                    LOG.I(String.format("handler registry after add. State : { %s}", handlerRegistry.size()));
                }
            }
        }
    }

    public void removeListenerOnEvent(Class<? extends Event> eventClazz, EventHandlerCallback listener) {
        synchronized (handlerRegistry) {
            LOG.I(String.format("Remove listener : {%s} on Event : {%s}", eventClazz, listener));
            EventHandler handler = handlerRegistry.get(listener);
            LOG.I(String.format("try remove handler : {%s}", handler));
            if (handler != null) {
                dispatcher.unregisterChannel(eventClazz, handler);
                if (!dispatcher.stillNeeded(handler)) {
                    handlerRegistry.remove(listener);
                    handler.resolve();
                }
                LOG.I(String.format("handler registry after remove. State : { %s}", handlerRegistry.size()));
            }
        }
    }

    public void removeListener(EventHandlerCallback listener) {
        synchronized (handlerRegistry) {
            LOG.I(String.format("Remove listener : {%s} ", listener));
            EventHandler handler = handlerRegistry.get(listener);
            LOG.I(String.format("try remove handler : {%s}", handler));
            if (handler != null) {
                dispatcher.unregisterChannel(handler);
                if (!dispatcher.stillNeeded(handler)) {
                    handlerRegistry.remove(listener);
                    handler.resolve();
                }
                LOG.I(String.format("handler registry after remove. State : { %s}", handlerRegistry.size()));
            }
        }
    }

    public void sendEvent(Event event) {
        LOG.I(String.format("Try send event:  {%s}", event));
        dispatcher.dispatch(event);
    }

}
