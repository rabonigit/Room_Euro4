package robac.pl.room_euro4.model;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class PojemnikStatusInfo {
    String Pojemniki;
    Boolean Wybor;


    public PojemnikStatusInfo(String pojemniki, Boolean wybor) {
        Pojemniki = pojemniki;
        Wybor = wybor;
    }

    public String getPojemniki() {
        return Pojemniki;
    }

    public void setPojemniki(String pojemniki) {
        Pojemniki = pojemniki;
    }

    public Boolean getWybor() {
        return Wybor;
    }

    public void setWybor(Boolean wybor) {
        Wybor = wybor;
    }


    public boolean equals(PojemnikStatusInfo obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PojemnikStatusInfo other = (PojemnikStatusInfo) obj;
        if ((this.Pojemniki == null) ? (other.Pojemniki != null) : !this.Pojemniki.equals(other.Pojemniki)) {
            return false;
        }
        if (this.Wybor != other.Wybor && (this.Wybor == null || !this.Wybor.equals(other.Wybor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.Pojemniki != null ? this.Pojemniki.hashCode() : 0);
        hash = 23 * hash + (this.Wybor != null ? this.Wybor.hashCode() : 0);
        return hash;
    }

}
