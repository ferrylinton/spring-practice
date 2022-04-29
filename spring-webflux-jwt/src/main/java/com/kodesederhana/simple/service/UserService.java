package com.kodesederhana.simple.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.kodesederhana.simple.dto.AddUserDto;
import com.kodesederhana.simple.dto.UpdateUserDto;
import com.kodesederhana.simple.entity.Role;
import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.exception.NotFoundException;
import com.kodesederhana.simple.repository.RoleRepository;
import com.kodesederhana.simple.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserService {
	
	private static final String ROLES_BY_USERNAME = new StringBuilder()
			.append("select rl.*")
			.append("from m_user us ")
			.append("inner join m_user_role ur on us.id = ur.user_id ")
			.append("inner join m_role rl on rl.id = ur.role_id ")
			.append("where us.username = :username ").toString();
	
	private static final String INSERT_USER_ROLE = "INSERT INTO m_user_role (user_id, role_id) VALUES ($1, $2)";
	
	private static final String DELETE_USER_ROLE = "DELETE FROM m_user_role WHERE user_Id= $1";

	private final R2dbcEntityTemplate template;
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final Pbkdf2PasswordService passwordService;
	
	public Flux<User> findAll(){
		return userRepository.findAll();
	}
	
	public Mono<User> findById(UUID id){
		return  userRepository.findById(id)
				.flatMap(user -> {
					return findRolesByUsername(user.getUsername()).collectList()
							.map(roles -> {
								user.setRoles(toString(roles));
								return user;
							});
				}).switchIfEmpty(Mono.error(new NotFoundException()));
	}
	
	public Mono<User> findByUsername(String username){
		return userRepository.findOneByUsername(username)
				.flatMap(user -> {
					return findRolesByUsername(user.getUsername()).collectList()
							.map(roles -> {
								user.setRoles(toString(roles));
								return user;
							});
				});
	}
	
	public Mono<User> add(AddUserDto dto){
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setPassword(passwordService.encode(dto.getPassword()));
		
		return save(user, dto.getRoles());
	}
	
	public Mono<User> update(UpdateUserDto dto){
		return  userRepository.findById(dto.getId())
				.flatMap(user -> {
					user.setEmail(dto.getEmail());
					user.setUsername(dto.getUsername());

					return save(user, dto.getRoles());
				}).switchIfEmpty(Mono.error(new NotFoundException()));
	}
	
	public Mono<User> save(User user, Set<String> roles){
		Mono<List<Role>> findByNameIn = roleRepository.findByNameIn(roles).collectList();
		Mono<User> save = userRepository.save(user);
		
		return Mono.zip(save, findByNameIn)
				.flatMap(tuple -> {
					deleteUserRoles(tuple.getT1())
						.map(rowsUpdated -> {
							insertUserRoles(tuple.getT1(), tuple.getT2()).subscribe();
							return rowsUpdated;
						}).subscribe();
					
					return findByUsername(user.getUsername());
				});
	}
	
	public Mono<Map<String, Object>> delete(UUID id){
		return userRepository.findById(id)
				.flatMap(user -> {
						return deleteUserRoles(user)
							.map(rowsUpdated -> {
								userRepository.delete(user).subscribe();
								Map<String, Object> data = new HashMap<>();
								data.put("message", "Data with id=[" + id + "] is deleted");
								return data;
							});
				}).switchIfEmpty(Mono.error(new NotFoundException()));
	}
	
	public Flux<Role> findRolesByUsername(String username) {
		return template.getDatabaseClient().sql(ROLES_BY_USERNAME)
				.bind("username", username)
				.fetch()
				.all()
				.map(map -> {
                    Role role = new Role();
                    role.setId((UUID) map.get("id"));
                    role.setName((String) map.get("name"));
                    return role;
                });
	}
	
	private Flux<Object> insertUserRoles(User user, Collection<Role> roles) {
		return template.getDatabaseClient().inConnectionMany(connection -> {

            var statement = connection.createStatement(INSERT_USER_ROLE);

            for (var role : roles) {
                statement
                	.bind(0, user.getId())
                	.bind(1, role.getId())
                	.add();
            }

            return Flux.from(statement.execute());
        });
	}
	
	private Mono<Integer> deleteUserRoles(User user) {
		return template.getDatabaseClient().sql(DELETE_USER_ROLE)
                .bind(0, user.getId())
                .fetch()
                .rowsUpdated();
	}
	
	private List<String> toString(Collection<Role> roles){
		return roles.stream()
			    .map(Role::getName)
			    .collect(Collectors.toList());
	}
}
