package co.edu.uniquindio.clinica.infra.security;

import co.edu.uniquindio.clinica.repositorios.CuentaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AutenticacionService implements UserDetailsService {
    private final CuentaRepo cuentaRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cuentaRepo.findByCorreo(username);
    }
}
