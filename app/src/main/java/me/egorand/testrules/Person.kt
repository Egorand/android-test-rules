/*
 * Copyright 2016 Egor Andreevici
 *
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

package me.egorand.testrules

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Egor
 */
@Suppress("unused")
open class Person(
        @PrimaryKey var name: String,
        var age: Int) : RealmObject() {

    constructor() : this("", 0) // required by Realm

    companion object {
        val FIELD_AGE = "age"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Person
        if (name != other.name) return false
        if (age != other.age) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }

    override fun toString(): String {
        return "Person(name='$name', age=$age)"
    }
}