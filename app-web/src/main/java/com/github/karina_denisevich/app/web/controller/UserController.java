package com.github.karina_denisevich.app.web.controller;


import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.LinesInfo;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.services.UserService;
import com.github.karina_denisevich.app.web.dto.AbstractDTO;
import com.github.karina_denisevich.app.web.dto.LinesInfoDto;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings("unchecked")
public class UserController {

    private final UserService userService;

    private final ConversionServiceFactoryBean conversionService;

    @Autowired
    public UserController(final UserService userService, final ConversionServiceFactoryBean conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    //FIXME refactor that, throw exceptions at service layer instead of api layer

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getById(@PathVariable String userId,
                                           @RequestHeader(value = "Custom-Lang", required = false, defaultValue = "en") String language) {
        //customLocale.setLanguage(language);

        User user = userService.find(userId);
        if (user == null) {
            user = userService.findByUsername(userId);
        }
        if (user == null) {
            throw new UserNotFoundException("There is no user : " + userId);
        }
        return new ResponseEntity<>(conversionService.getObject()
                .convert(user, UserDTO.class), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<? extends AbstractDTO>> getAll(@RequestHeader(value = "Custom-Lang", required = false, defaultValue = "en") String language) {

        List<User> entities = userService.findAll();

        if (CollectionUtils.isEmpty(entities)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<? extends AbstractDTO> convertedList;
        if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Authority.ROLE_ADMIN)) {
            convertedList = getDtoList(UserDTOAdmin.class, entities);
        } else {
            convertedList = getDtoList(UserDTO.class, entities);
        }

        return new ResponseEntity<>(convertedList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@Valid @RequestBody UserDTOAdmin userDTOAdmin,
                                    @PathVariable String userId) {
        if (!SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Authority.ROLE_ADMIN)) {
            String authorities = Authority.ROLE_USER.getAuthority();
            userDTOAdmin.setAuthorities(authorities);
        } else if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Authority.ROLE_ADMIN) && StringUtils.isNotEmpty(userDTOAdmin.getAuthorities())) {
            userDTOAdmin.setAuthorities(userDTOAdmin.getAuthorities());
        }
        User user = (conversionService.getObject().convert(userDTOAdmin, User.class));
        if (!Objects.equals(userId, userService.update(userId, user).getId())) {
            return new ResponseEntity<>("There is no user with id = " + userId,
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable String userId) {
        String id = userService.delete(userId);
        if (id == null) {
            return new ResponseEntity<>("There is no entity with id = " + userId,
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private <T extends AbstractDTO> List<T> getDtoList(Class<T> type, List<?> objects) {
        return (List<T>) conversionService.getObject().convert(objects,
                TypeDescriptor.valueOf(List.class),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(type)));
    }

    @RequestMapping(value = "/saveLines", method = RequestMethod.POST)
    public ResponseEntity<Object> saveLines(@RequestParam(value = "userId", required = false) final String userId,
                                            @RequestBody final LinesInfoDto linesInfoDto) {
        linesInfoDto.setId(UUID.randomUUID().toString());
        userService.saveLinesInfo(conversionService.getObject().convert(linesInfoDto, LinesInfo.class), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/bookmarks/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> getUserBookmarks(@PathVariable String userId) {

        User user = userService.find(userId);

        return new ResponseEntity<>(user.getLinesInfoList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/deleteBookmark/{bookmarkId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBookmark(@PathVariable String bookmarkId, @PathVariable String userId) {
        String id = userService.deleteBookmark(userId, bookmarkId);
        if (id == null) {
            return new ResponseEntity<>("There is no entity with id = " + bookmarkId,
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
