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

import java.util.Arrays;
import java.util.List;
import no.hubromed.pattern.observer.EventCRUD;

public final class MyExample {

    private MyExample() {
    }

    public static void main(String[] args) {
        // Lag Subject
        MySubjectImpl subject = new MySubjectImpl();

        // Lag observers
        MyObserver observerAll = new MyObserver("observerAll");
        MyObserver observerName = new MyObserver("observerName");
        MyObserver observerValues = new MyObserver("observerValues");

        // La observers lytte på Subject
        subject.attach(observerAll, EventCRUD.CUD, MySubjectImpl.Field.values());
        subject.attach(observerName, EventCRUD.CUD, MySubjectImpl.Field.NAME);
        subject.attach(observerValues, EventCRUD.CUD, MySubjectImpl.Field.MOONS);

        List<String> aList = Arrays.asList("One", "Two", "Three");

        subject.setMoons(aList);
        subject.setName("Hello World");
    }
}
