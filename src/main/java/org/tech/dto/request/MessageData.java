package org.tech.dto.request;

import java.io.Serial;
import java.io.Serializable;

public class MessageData  implements Serializable {


    private static final long serialVersionUID = 1L;

    private String from;

    private String to;

    private String xmlData;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", xmlData='" + xmlData + '\'' +
                '}';
    }
}
