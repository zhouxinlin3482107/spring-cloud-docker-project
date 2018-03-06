package com.example.uaa.users;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class UserDetailsController {

    UserDetailsRepository userDetailsRepository;
    GroupDetailsRepository groupDetailsRepository;
    PosixUserRepository posixUserRepository;
    UserDataSetRepository userDataSetRepository;

    public UserDetailsController(@Autowired UserDetailsRepository userDetailsRepository,
                                 @Autowired GroupDetailsRepository groupDetailsRepository,
                                 @Autowired PosixUserRepository posixUserRepository,
                                 @Autowired UserDataSetRepository userDataSetRepository
    ) {
        this.userDetailsRepository = userDetailsRepository;
        this.groupDetailsRepository = groupDetailsRepository;
        this.posixUserRepository = posixUserRepository;
        this.userDataSetRepository = userDataSetRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDto> list() {
        List<UserDetails> users = userDetailsRepository.list();
        return Lists.transform(users, (user) -> UserDto.create(user));
    }

    @PreAuthorize("#userName == authentication.name || hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userName}/data-sets", method = RequestMethod.GET)
    public List<UserDataSet> getUserDataSets(@PathVariable String userName) {
        return userDataSetRepository.getDataSets(userName);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userName}/data-sets/{dataSet}", method = RequestMethod.POST)
    public ResponseEntity<?> addUserDataSet(@PathVariable String userName, @PathVariable String dataSet, Principal user) {
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setUserName(userName);
        userDataSet.setDataSetId(dataSet);
        userDataSet.setModifier(user.getName());
        userDataSet.setCreateTime(new Date(System.currentTimeMillis()));
        userDataSetRepository.add(userDataSet);
        ResponseEntity<?> response = new ResponseEntity(HttpStatus.CREATED);
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userName}/data-sets/{dataSetId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserDataSet(@PathVariable String userName, @PathVariable String dataSetId) {
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setUserName(userName);
        userDataSet.setDataSetId(dataSetId);
        userDataSetRepository.delete(userDataSet);
        ResponseEntity<?> response = new ResponseEntity(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/users/current")
    public UserDto current(Principal currentUser) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (currentUser instanceof OAuth2Authentication) {
            OAuth2Authentication auth = (OAuth2Authentication) currentUser;
            authorities.addAll(auth.getAuthorities());
        }

        UserDetails userDetails = userDetailsRepository.getUserByName(currentUser.getName());
        return UserDto.create(userDetails, isAdmin(authorities));
    }

    private boolean isAdmin(List<GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (Roles.ADMIN.equalsTo(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @PreAuthorize("#u.userName == authentication.name || hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody @P("u") UserDetailsUpdateDto updateDto) {
        System.out.println(updateDto.getUserName());
        UserDetails userDetails = userDetailsRepository.getUserByName(updateDto.getUserName());
        userDetails.merge(updateDto);
        userDetailsRepository.update(userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        UserDetails userDetails = new UserDetails(user);
        PosixUser posixUser = posixUserRepository.add(userDetails.getUserName());
        userDetails.setUidNumber(String.valueOf(posixUser.getUserId()));
        userDetailsRepository.create(userDetails);
        groupDetailsRepository.addMemberToGroup(userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("#u.userName == authentication.name || hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/reset", method = RequestMethod.PUT)
    public ResponseEntity<?> newPassword(@RequestBody @P("u") ResetPasswordDto resetPasswordDto) {
        return userDetailsRepository.verifyAndResetPassword(resetPasswordDto);
    }

    @PreAuthorize("#u.userName == authentication.name || hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/default", method = RequestMethod.PUT)
    public ResponseEntity<?> defaultPassword(@RequestBody @P("u") ResetPasswordDto defaultPasswordDto) {
        UserDetails userDetails = userDetailsRepository.getUserByName(defaultPasswordDto.getUserName());
        userDetailsRepository.resetPassword(userDetails, defaultPasswordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
