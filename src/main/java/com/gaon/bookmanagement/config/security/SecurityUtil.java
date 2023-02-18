package com.gaon.bookmanagement.config.security;

import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomException;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtil {

    public static Long getCurrentMember() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.CONTEXT_NOT_FIND);
        }

        return Long.parseLong(authentication.getName());
    }
}
