package com.gaon.bookmanagement.dto.response;

import com.gaon.bookmanagement.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JoinResponseDto {
    private Long id;
    private String username;
    private String role;

    public JoinResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.role = member.getRoleValue();
    }
}
