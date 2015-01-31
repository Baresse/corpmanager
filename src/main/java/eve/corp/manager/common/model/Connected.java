package eve.corp.manager.common.model;

import eve.corp.manager.common.enums.ConnectionOrigin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Connected implements Serializable {

    private static final long serialVersionUID = -3351453638102871973L;

    private Set<ConnectionOrigin> origins = new HashSet();

    public Connected() {
    }

    public Set<ConnectionOrigin> getOrigins() {
        return this.origins;
    }

    public void setOrigins(Set<ConnectionOrigin> origins) {
        this.origins = origins;
    }

    public void addOrigin(ConnectionOrigin origin) {
        if (origin != null) {
            this.origins.add(origin);
        }
    }
}
