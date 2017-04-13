package ir.ac.iust.dml.kg.knowledge.expert.web.services.v1;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Impl {@link IExpertServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.expert.web.services.v1.IExpertServices")
public class ExpertServiceImpl implements IExpertServices {
    @Override
    public List<String> login() {
        final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        final List<String> result = new ArrayList<>();
        authorities.forEach(a -> result.add(a.getAuthority()));
        return result;
    }
}
