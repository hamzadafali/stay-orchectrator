package so.stay.orchestrator.stayorchestrator.infrastructure.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import so.stay.orchestrator.stayorchestrator.domain.user.port.UserRepository;

@Service
public class StayUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public StayUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(StayUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
