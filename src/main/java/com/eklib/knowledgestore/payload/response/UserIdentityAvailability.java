package com.eklib.knowledgestore.payload.response;

/**
 * Created by n.yushchenko on 25.03.2019
 */
public class UserIdentityAvailability {

    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
