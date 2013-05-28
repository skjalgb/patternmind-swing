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

import java.util.Date;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link no.hubromed.pattern.observer.AbstractSubject}.
 *
 * @author Skjalg Bjørndal
 * @since Sep 24, 2010
 */
public class AsynchronousDelegateSubjectTest {

    private enum Field {
        TIME
    }

    private SomeClassThatAlreadyExtendsSomething subject;
    private Observer.ObserverCounter<Field, SomeClassThatAlreadyExtendsSomething> observer;

    @Before
    public void setUp() throws Exception {
        subject = new SomeClassThatAlreadyExtendsSomething();
        observer = new Observer.ObserverCounter<Field, SomeClassThatAlreadyExtendsSomething>();
        subject.attach(observer, EventCRUD.CRUD, Field.TIME);
        subject.setTestable(true);
    }


    private class SomeClassThatAlreadyExtendsSomething extends Date implements Subject<Field, SomeClassThatAlreadyExtendsSomething> {
        Subject.AsynchronousSubject<Field, SomeClassThatAlreadyExtendsSomething> delegate =
                new Subject.AsynchronousSubject<Field, SomeClassThatAlreadyExtendsSomething>(this);

        public void setTestable(boolean testable) {
            delegate.setSynchronous(testable);
        }

        public String name() {
            return this.getClass().getSimpleName();
        }

        public void attach(Observer<Field, SomeClassThatAlreadyExtendsSomething> observer, Set<EventCRUD> events, Field... fields) {
            delegate.attach(observer, events, fields);
        }

        public void detach(Observer<Field, SomeClassThatAlreadyExtendsSomething> observer) {
            delegate.detach(observer);
        }


        public void detachAll() {
            delegate.detachAll();
        }

        public void notify(Field field, EventCRUD event) {
            delegate.notify(field, event);
        }

        public void notify(Field field) {
            delegate.notify(field);
        }

        @Override
        public long getTime() {
            notify(Field.TIME, EventCRUD.READ);
            return super.getTime();
        }

    }


    private void assertCRUD(int I, int C, int R, int U, int D) {
        assertEquals(I, observer.count(Field.TIME, EventCRUD.INITIAL));
        assertEquals(C, observer.count(Field.TIME, EventCRUD.CREATE));
        assertEquals(R, observer.count(Field.TIME, EventCRUD.READ));
        assertEquals(U, observer.count(Field.TIME, EventCRUD.UPDATE));
        assertEquals(D, observer.count(Field.TIME, EventCRUD.DELETE));
    }

    @Test
    public void testAttach() throws Exception {
        assertCRUD(1, 0, 0, 0, 0);
    }

    @Test
    public void testGetTime() throws Exception {
        subject.getTime();
        assertCRUD(1, 0, 1, 0, 0);
    }


}
