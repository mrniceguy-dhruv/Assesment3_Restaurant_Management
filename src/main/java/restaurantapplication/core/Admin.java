package restaurantapplication.core;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

@Entity
@Table(name = "admin")
@NamedQueries({
        @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
        @NamedQuery(name = "Admin.findById",
                query = "SELECT a FROM Admin a WHERE a.ad_id = :id"),
        @NamedQuery(name = "Admin.findByUsernameAndPassword",
                query = "SELECT a FROM Admin a WHERE a.username = :username "
                        + "and a.password = :password"),
        @NamedQuery(name = "Admin.findByUsername",
                query = "SELECT a FROM Admin a WHERE a.username = :username")})

public class Admin implements Principal, Serializable {


    @Column(name = "ad_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ad_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Admin() {
    }

    public Admin(long ad_id, String username, String password) {
        this.ad_id = ad_id;
        this.username = username;
        this.password = password;
    }


    public long getAd_id() {
        return ad_id;
    }

    public void setAd_id(long ad_id) {
        this.ad_id = ad_id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return ad_id == admin.ad_id && Objects.equals(username, admin.username) && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ad_id, username, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + ad_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String getName() {
        return username;
    }
}
