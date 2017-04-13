package ir.ac.iust.dml.kg.knowledge.expert.web.security;

import ir.ac.iust.dml.kg.knowledge.expert.access.dao.IUserDao;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * implements UserDetailsService to read user
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserDao Autowired;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = Autowired.readByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        final Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.getPermissions().contains(UserPermission.Superuser))
            for (UserPermission permission : UserPermission.values())
                authorities.add(new SimpleGrantedAuthority("ROLE_" + permission.toString().toUpperCase()));
        else
            for (UserPermission permission : user.getPermissions())
                authorities.add(new SimpleGrantedAuthority("ROLE_" + permission.toString().toUpperCase()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
