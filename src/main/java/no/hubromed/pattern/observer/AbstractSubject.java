/*
 * Copyright 2005-2013 Skjalg Bjorndal
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package no.hubromed.pattern.observer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code AbstractSubject} is an implementation of {@code Subject} that is deliberately made package local, so that
 * it cannot be extended. You should instead use {@link SynchronousSubject} or {@link AsynchronousSubject}, depending
 * on your need. (For GUI purposes use the latter).</p>
 * Its is important to note that we cannot always extend a class. Instead we would like to use the {@code Delegate}
 * pattern. In such cases, use the constructor {@link #AbstractSubject(Subject)}.
 *
 * @param <E> An enum that describes the fields that we can observe.
 * @param <S> Subject self reference, mainly needed in order set up the observer correctly with generics.
 */
abstract class AbstractSubject<E extends Enum, S extends Subject> implements Subject<E, S> {

    private final transient Set<Observer<E, S>> observers = Collections.synchronizedSet(new HashSet<Observer<E, S>>(10));
    private final transient Map<Observer<E, S>, Set<E>> fields = Collections.synchronizedMap(new HashMap<Observer<E, S>, Set<E>>());
    private final transient Map<Observer<E, S>, Set<EventCRUD>> events = Collections.synchronizedMap(new HashMap<Observer<E, S>, Set<EventCRUD>>());

    private S delegateFor;
    private transient Logger logger = Logger.getLogger(getClass().getSimpleName());

    public AbstractSubject() {
        //noinspection unchecked
        delegateFor = (S) this;
    }

    public AbstractSubject(final S delegateFor) {
        this.delegateFor = delegateFor;
    }

    public String name() {
        return getClass().getSimpleName();
    }

    public void attach(final Observer<E, S> observer, final E... fields) {
        attach(observer, EventCRUD.CRUD, fields);
    }

    public void attach(final Observer<E, S> observer, final Set<EventCRUD> event, final E... fields) {
        Validate.notNull(observer, "Observer");
        Validate.notNull(event, "event");
        Validate.notNull(fields, "fields");
        Validate.isTrue(fields.length > 0, "Please subscribe (attach) to 1 or more fields");
        Validate.isTrue(event.size() > 0, "Please subscribe (attach) to at least 1 event");

        Set<E> subscribedFields = new HashSet<E>();
        for (E field : fields) {
            Validate.notNull(field, "Field");
            subscribedFields.add(field);
            // Send INITIAL message to all fields observer has attached to.
            if (event.contains(EventCRUD.INITIAL)) {
                //noinspection unchecked
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("@attach: > Sending INITIAL " + field + " to " + observer.getClass().getName());
                }
                observer.update(field, EventCRUD.INITIAL, delegateFor);
            }
        }

        synchronized (this.observers) {
            this.observers.add(observer);
        }

        synchronized (this.fields) {
            this.fields.put(observer, subscribedFields);
        }

        synchronized (this.events) {
            this.events.put(observer, event);
        }
    }

    private String getObserverName(final Observer<E, S> observer) {
        return observer.getClass().getSimpleName();
    }

    public void detach(final Observer<E, S> observer) {
        synchronized (observers) {
            this.observers.remove(observer);
        }

        synchronized (fields) {
            this.fields.remove(observer);
        }

        synchronized (events) {
            this.events.remove(observer);
        }
    }

    public void detachAll() {
        synchronized (observers) {
            this.observers.clear();
        }

        synchronized (fields) {
            this.fields.clear();
        }

        synchronized (events) {
            this.events.clear();
        }
    }

    public void notify(final E field, final EventCRUD event) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("notify: field = " + field + ", event = " + event);
        }

        // Traverse all observers og notify those that observe the field and event.
        final Set<Observer<E, S>> copyObservers;
        synchronized (observers) {
            copyObservers = new HashSet<Observer<E, S>>(observers);
        }

        for (final Observer<E, S> observer : copyObservers) {
            final Set<E> observableFields = fields.get(observer) == null ? new HashSet<E>() : fields.get(observer);
            if (observableFields.contains(field)) {
                final Set<EventCRUD> observedEvents = events.get(observer);
                if (observableFields.contains(field) && observedEvents.contains(event)) {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("-> observer = " + getObserverName(observer));
                    }
                    notify(field, event, observer);
                }
            }
        }
    }

    public void notify(final E field) {
        notify(field, EventCRUD.UPDATE);
    }

    /**
     * Makes the publication of the {@code event} for {@code field} to the {@code observer}.
     *
     * @param field Field being observed.
     * @param event An event {@code observer} is interested in.
     * @param observer The observer that will receive the message.
     */
    abstract void notify(final E field, final EventCRUD event, final Observer<E, S> observer);

    protected S delegateFor() {
        return delegateFor;
    }
}



