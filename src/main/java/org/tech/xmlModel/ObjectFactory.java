package org.tech.xmlModel;

import jakarta.xml.bind.annotation.XmlRegistry;
import org.tech.entity.PersonModel;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Person createPerson() {
        return new Person();
    }
}
