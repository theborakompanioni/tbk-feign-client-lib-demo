package org.tbk.feigndemoclient.github.model;

public class Contributor {
    private long id;
    private String login;
    private long contributions;

    public Contributor() {
    }

    public long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public long getContributions() {
        return this.contributions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setContributions(long contributions) {
        this.contributions = contributions;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Contributor)) return false;
        final Contributor other = (Contributor) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$login = this.getLogin();
        final Object other$login = other.getLogin();
        if (this$login == null ? other$login != null : !this$login.equals(other$login)) return false;
        if (this.getContributions() != other.getContributions()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $login = this.getLogin();
        result = result * PRIME + ($login == null ? 43 : $login.hashCode());
        final long $contributions = this.getContributions();
        result = result * PRIME + (int) ($contributions >>> 32 ^ $contributions);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Contributor;
    }

    public String toString() {
        return "org.tbk.feigndemoclient.github.model.Contributor(id=" + this.getId() + ", login=" + this.getLogin() + ", contributions=" + this.getContributions() + ")";
    }
}
