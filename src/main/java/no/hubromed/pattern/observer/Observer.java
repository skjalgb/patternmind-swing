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

import java.util.HashMap;
import java.util.Map;


/**
 * An {@code observer} listens for changes that takes place in a {@code Subject}. The benefit of this interface
 * is that it allows strong typing, the downside is that you cannot listen to more than one {@code Subject}, due to the
 * nature of generics. In that case you should use a {@code handler} that takes care of these events.</p>
 * <p/>
 * If you would like to test your {@code Subject}s (of course you will), you have a convenience
 * class {@link ObserverCounter} that can be used.
 *
 * @param <E> An enum that describes which field has a change.
 * @param <S> A reference to the model, so that values can be read. If you do not want the setters to be exposed,
 *            you can use an interface.
 */
public interface Observer<E extends Enum, S extends Subject> {


    /**
     * The way to communicate that something has changed.
     *
     * @param field   Field that has changed.
     * @param event   The event that took place, create, read, update, delete (depending on what you are listening for).
     * @param subject If the {@code observer} listens to more than one instance of a class, then use
     *                the {@link Subject#name()} to differentiate.
     */
    void update(final E field, final EventCRUD event, final S subject);


    /**
     * The {@code ObserverCounter} can be used for testing that a Subject fires the correct fields and events.
     * The {@link #count(Enum)} and {@link #count(Enum, EventCRUD)} can be used in assertions to verify correspondence
     * between actual and expected numbers.</p>
     * Remember to set the {@link Subject.AsynchronousSubject#setSynchronous(boolean)} to {@code true} when writing
     * tests, as you otherwise most likely will encounter timing problems.
     */
    class ObserverCounter<E extends Enum, S extends Subject> implements Observer<E, S> {
        private Map<String, Integer> counter = new HashMap<String, Integer>();

        public String key(final E field, final EventCRUD event) {
            return field.toString() + " - " + event.toString();
        }

        /**
         * @param field Field to be read.
         * @param event The event that took place, create, read, update, delete (depending on what you are listening for).
         * @return Number of times the {@code Observer} has received an {@code event} event for {@code field}.
         */
        public int count(final E field, final EventCRUD event) {
            final Integer c = counter.get(key(field, event));
            return c != null ? c : 0;
        }

        /**
         * @param field Field to be read.
         * @return Number of times the {@code Observer} has received an {@code EventCRUD.UPDATE} event for {@code field}.
         */
        public int count(final E field) {
            final Integer c = counter.get(key(field, EventCRUD.UPDATE));
            return c != null ? c : 0;
        }

        public void update(final E field, final EventCRUD event, final S subject) {
            final String key = key(field, event);
            Integer c = 0;
            if (counter.containsKey(key)) {
                c = counter.get(key);
            }
            c++;
            counter.put(key, c);
        }

        /**
         * Factory-method to avoid too much typing.
         */
        public static <E extends Enum<E>, S extends Subject<E, S>> ObserverCounter<E, S> newInstance() {
            return new ObserverCounter<E, S>();
        }
    }


}



