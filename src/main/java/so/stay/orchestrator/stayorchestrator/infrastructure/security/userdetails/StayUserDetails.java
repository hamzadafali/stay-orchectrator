package so.stay.orchestrator.stayorchestrator.infrastructure.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.model.UserRole;

import java.util.Collection;
import java.util.List;

public class StayUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final UserRole role;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;

    public StayUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail().getValue();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
