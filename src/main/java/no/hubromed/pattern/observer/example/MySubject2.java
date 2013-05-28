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

package no.hubromed.pattern.observer.example;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import no.hubromed.pattern.observer.EventCRUD;
import no.hubromed.pattern.observer.Observer;
import no.hubromed.pattern.observer.Subject;

/**
 */
public class MySubject2 extends SomeClassIWantToInherit implements Subject<MySubject2.Field, MySubject2> {


    public enum Field {
        SUM,
        VALUES
    }

    private Subject.AsynchronousSubject<Field, MySubject2> delegate = new AsynchronousSubject<Field, MySubject2>(this);
    private Integer sum = 0;
    private List<Integer> values = Collections.emptyList();


    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public void attach(Observer<Field, MySubject2> observer, Set<EventCRUD> events, Field... fields) {
        delegate.attach(observer, events, fields);
    }

    @Override
    public void detach(Observer<Field, MySubject2> observer) {
        delegate.detach(observer);
    }

    @Override
    public void detachAll() {
        delegate.detachAll();
    }

    @Override
    public void notify(Field field, EventCRUD event) {
        delegate.notify(field, event);
    }

    @Override
    public void notify(Field field) {
        delegate.notify(field);
    }


    public Integer getSum() {
        return sum;
    }

    public List<Integer> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void setValues(final List<Integer> values) {
        if (!this.values.equals(values)) {
            this.values = values;
            this.sum = sum(values);
            notify(Field.VALUES);
            notify(Field.SUM);
        }
    }
}
