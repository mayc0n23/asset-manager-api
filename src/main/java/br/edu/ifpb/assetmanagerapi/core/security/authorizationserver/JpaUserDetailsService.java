package br.edu.ifpb.assetmanagerapi.core.security.authorizationserver;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.model.Admin;
import br.edu.ifpb.assetmanagerapi.domain.repository.AdminRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado"));
		return new User(admin.getLogin(), admin.getPassword(), Collections.emptyList());
	}

}