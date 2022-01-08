package com.maciejBigos.poAwarii.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DatabaseFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    private String transferorId;

    @Lob
    private byte[] data;

    public DatabaseFile() {
    }

    public DatabaseFile(String name, String type, String transferorId, byte[] data) {
        this.name = name;
        this.type = type;
        this.transferorId = transferorId;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransferorId() {
        return transferorId;
    }

    public void setTransferorId(String transferorId) {
        this.transferorId = transferorId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
