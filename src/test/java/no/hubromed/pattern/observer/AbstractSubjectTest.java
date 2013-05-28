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

import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link no.hubromed.pattern.observer.AbstractSubject}.
 *
 * @author Skjalg Bjørndal
 * @since Sep 23, 2010
 */
public abstract class AbstractSubjectTest {

    enum Field {
        NUMBER,
    }

    private Subject<Field, Subject.SynchronousSubject> subject;
    private Observer.ObserverCounter<Field, Subject.SynchronousSubject> observer;

    @Before
    public void setUp() throws Exception {
        subject = getInstance();
        observer = new Observer.ObserverCounter<Field, Subject.SynchronousSubject>();
    }

    public abstract Subject<Field, Subject.SynchronousSubject> getInstance();

    public abstract String getClassName();


    @Test
    public void testName() throws Exception {
        assertEquals(getClassName(), subject.name());
    }


    private void assertCRUD(int I, int C, int R, int U, int D) {
        assertEquals(I, observer.count(Field.NUMBER, EventCRUD.INITIAL));
        assertEquals(C, observer.count(Field.NUMBER, EventCRUD.CREATE));
        assertEquals(R, observer.count(Field.NUMBER, EventCRUD.READ));
        assertEquals(U, observer.count(Field.NUMBER, EventCRUD.UPDATE));
        assertEquals(D, observer.count(Field.NUMBER, EventCRUD.DELETE));
    }

    @Test
    public void testAttach() throws Exception {
        subject.attach(observer, EventCRUD.CRUD, Field.NUMBER);
        assertCRUD(1, 0, 0, 0, 0);
    }

    @Test
    public void testDetach() throws Exception {
        subject.attach(observer, EventCRUD.CRUD, Field.NUMBER);
        assertCRUD(1, 0, 0, 0, 0);

        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        assertCRUD(1, 0, 0, 0, 1);

        subject.detach(observer);
        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        assertCRUD(1, 0, 0, 0, 1);
    }

    @Test
    public void testDetachAll() throws Exception {
        subject.attach(observer, EventCRUD.CRUD, Field.NUMBER);
        assertCRUD(1, 0, 0, 0, 0);

        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        assertCRUD(1, 0, 0, 0, 1);

        subject.detachAll();
        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        assertCRUD(1, 0, 0, 0, 1);

        // Should be allowed to call several times.
        subject.detachAll();
    }

    @Test
    public void testNotify() throws Exception {
        subject.attach(observer, EventCRUD.CRUD, Field.NUMBER);
        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        subject.notify(Field.NUMBER, EventCRUD.DELETE);
        subject.notify(Field.NUMBER, EventCRUD.CREATE);
        subject.notify(Field.NUMBER, EventCRUD.CREATE);
        assertCRUD(1, 2, 0, 0, 2);

        subject.notify(Field.NUMBER);
        assertCRUD(1, 2, 0, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void attachWithNoEvents() {
        subject.attach(observer, new HashSet<EventCRUD>(0), Field.NUMBER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void attachWithNoFields() {
        subject.attach(observer, EventCRUD.CRUD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void attachWithNullObserver() {
        subject.attach(null, EventCRUD.CRUD, Field.NUMBER);
    }

}
