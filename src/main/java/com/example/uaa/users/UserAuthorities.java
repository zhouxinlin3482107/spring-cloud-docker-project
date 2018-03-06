package com.example.uaa.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "users_data_sets"
)
public class UserAuthorities {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "data_set_id")
    private String dataSetId;

    @Column(name = "username")
    private String userName;

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
