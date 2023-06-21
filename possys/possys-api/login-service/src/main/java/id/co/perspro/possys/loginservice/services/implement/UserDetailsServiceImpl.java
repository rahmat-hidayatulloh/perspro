package id.co.perspro.possys.loginservice.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import id.co.perspro.possys.loginservice.model.entity.UserEntity;
import id.co.perspro.possys.loginservice.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    UserEntity user = userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("username : " + username + " Tidak Ditemukan"));
    return UserDetailsImpl.build(user);
  }

}
