package hr.fer.progi.oneclick.humanitarnisetacipasa.security.services;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.UserRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AssociationRepository associationRepository;
    private final CitizenRepository citizenRepository;

    @Autowired
    UserRepository userRepository;

    public UserDetailsServiceImpl(AssociationRepository associationRepository, CitizenRepository citizenRepository) {
        this.associationRepository = associationRepository;
        this.citizenRepository = citizenRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return build(user);
    }

    public UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (associationRepository.existsAssociationByUser(user))
            authorities.add(new SimpleGrantedAuthority(Roles.ROLE_ASSOCIATION.name()));
        else if (citizenRepository.existsCitizenByUser(user))
            authorities.add(new SimpleGrantedAuthority(Roles.ROLE_CITIZEN.name()));
        else
            throw new NoSuchElementException();

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }
}
