package org.tech.dto.request;

import java.io.Serializable;

public class RequestToData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String to;

    private String xmlData;

    public RequestToData() {

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

    public RequestToData(String to, String xmlData) {
        this.to = to;
        this.xmlData = xmlData;
    }

    @Override
    public String toString() {
        return "RequestToData{" +
                "to='" + to + '\'' +
                ", xmlData='" + xmlData + '\'' +
                '}';
    }
}
