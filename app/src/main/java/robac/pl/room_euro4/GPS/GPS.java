package robac.pl.room_euro4.GPS;


public class GPS  {
    Double Latitude;
    Double Longitude;

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public GPS(Double latitude, Double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }
    public void set(GPS ob) {
        Latitude = ob.getLatitude();
        Longitude = ob.getLongitude();
    }

    public Double getLongitude() {
        return Longitude;
    }

    public GPS(GPS ob) {
        Latitude = ob.getLatitude();
        Longitude = ob.getLongitude();
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
//    @NonNull
//    @NotNull
//    @Override
    public String toString() {
        return Latitude+";"+Longitude;
    }
}
