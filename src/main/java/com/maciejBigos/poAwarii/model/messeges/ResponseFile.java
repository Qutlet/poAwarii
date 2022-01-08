package com.maciejBigos.poAwarii.model.messeges;

public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
    private String transferorId;

    public ResponseFile(String name, String url, String type, long size, String transferorId) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.transferorId = transferorId;
    }

    public String getName() {
        return name;
    }

    public ResponseFile setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResponseFile setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getType() {
        return type;
    }

    public ResponseFile setType(String type) {
        this.type = type;
        return this;
    }

    public long getSize() {
        return size;
    }

    public ResponseFile setSize(long size) {
        this.size = size;
        return this;
    }

    public String getTransferorId() {
        return transferorId;
    }

    public ResponseFile setTransferorId(String transferorId) {
        this.transferorId = transferorId;
        return this;
    }
}
