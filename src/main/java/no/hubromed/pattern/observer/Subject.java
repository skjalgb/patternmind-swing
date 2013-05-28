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

import javax.swing.SwingUtilities;
import java.util.Set;


/**
 * {@code Subject} is an instance being observed by an {@code Observer}.
 *
 * @param <E> An {@code enum} describing the fields.
 * @param <S> Subject.
 */
public interface Subject<E extends Enum, S extends Subject> {


    /**
     * @return Name that is sufficient for respective {@code Observer}s to differentiate between
     *         various {@code Subject}s.
     */
    String name();

    /**
     * Starts the subscription for the all or parts of the {@code Subject}'s subscriptions.
     * Since the {@code Observer} is not aware of the {@code Subject}'s initial state, it shall inform the {@code Observer}
     * by sending a {@link EventCRUD#CREATE} message for all fields the {@code Observer} has attached to.
     *
     * @param observer {@code Observer} wishing to subscribe to changes in {@code Subject}.
     * @param events   Type of events to listen for.
     * @param fields   List of 1 or more fields to observe.
     */
    void attach(Observer<E, S> observer, final Set<EventCRUD> events, E... fields);

    /**
     * Stop subscription for all fields and events.
     *
     * @param observer {@code Observer} wishing to unsubscribe.
     */
    void detach(Observer<E, S> observer);


    /**
     * Stop subscription for all fields, and events, for all {@code Observer}s.
     */
    void detachAll();

    /**
     * Primarily intended to be used by {@code Subject} to publish changes fo a given field.
     * All {@code Observer}s that subscribe to field and event will be notified.
     *
     * @param field Field that i being notified about..
     * @param event Type of event.
     */
    void notify(E field, EventCRUD event);

    /**
     * Primarily intended to be used by {@code Subject} to publish {@code UPDATE} fo a given field.
     * All {@code Observer}s that subscribe to field and event will be notified.
     *
     * @param field Field that i being notified about..
     */
    void notify(E field);

    /**
     * Implementation of {@link Subject} used when notifications should be sent asynchronous.
     * This implementation is useful when for instance writing a Swing application.
     * <p/>
     * Note that for testing purposes, when you in a unit test want to verify that the correct events and fields are
     * emitted as the model changes, remember set the {@link #setSynchronous(boolean)} to {@code true}, as you otherwise
     * most likely will encounter timing problems.
     *
     * @param <E> An {@code enum} describing the fields.
     * @param <S> Subject.
     */
    class AsynchronousSubject<E extends Enum, S extends Subject> extends AbstractSubject<E, S> {

        private boolean synchronous;

        public AsynchronousSubject() {
            super();
        }

        public AsynchronousSubject(final S delegateFor) {
            super(delegateFor);
        }

        /**
         * @param synchronous Set this to {@code true} (makes the class synchronous) when you are running unit tests,
         *                 otherwise {@code false}.
         */
        public void setSynchronous(final boolean synchronous) {
            this.synchronous = synchronous;
        }

        /**
         * @return {@code true} when you have made the class synchronous (for testing purposes, otherwise {@code false}.
         */
        public boolean isSynchronous() {
            return synchronous;
        }

        void notify(final E felt, final EventCRUD event, final Observer<E, S> observer) {
            if (SwingUtilities.isEventDispatchThread() || synchronous) {
                observer.update(felt, event, delegateFor());
            } else {
                Runnable doFirePropertyChange = new Runnable() {
                    public void run() {
                        observer.update(felt, event, delegateFor());
                    }
                };
                SwingUtilities.invokeLater(doFirePropertyChange);
            }
        }

    }

    /**
     * Implementation of {@link Subject} used when notifications should be sent synchronous, as would be the case in
     * a J2EE-server.
     *
     * @param <E> An {@code enum} describing the fields.
     * @param <S> Subject.
     */
    class SynchronousSubject<E extends Enum, S extends Subject> extends AbstractSubject<E, S> {

        public SynchronousSubject() {
            super();
        }

        public SynchronousSubject(final S delegateFor) {
            super(delegateFor);
        }

        void notify(final E felt, final EventCRUD event, final Observer<E, S> observer) {
            observer.update(felt, event, delegateFor());
        }

    }

}