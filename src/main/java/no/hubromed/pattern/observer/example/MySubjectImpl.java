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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.hubromed.pattern.observer.EventCRUD;
import no.hubromed.pattern.observer.Subject;

public class MySubjectImpl extends Subject.AsynchronousSubject<MySubject.Field, MySubject> implements MySubject {

    private String name = "Hello Moon";
    private List<String> moons = Arrays.asList("Moon A", "Moon B");

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
            // Shortcut for: notify(Field.NAME, EventCRUD.UPDATE);
            notify(Field.NAME);
        }
    }

    @Override
    public List<String> getMoons() {
        return Collections.unmodifiableList(moons);
    }

    public void setMoons(List<String> moons) {
        if (!this.moons.equals(moons)) {
            this.moons = new ArrayList<String>(moons);
            notify(Field.MOONS);
        }
    }

    public void add(String moon) {
        if (moons.add(moon)) {
            notify(Field.MOONS, EventCRUD.CREATE);
        }
    }

    public void remove(String moon) {
        if (moons.remove(moon)) {
            notify(Field.MOONS, EventCRUD.DELETE);
        }
    }
}
