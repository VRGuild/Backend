package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.oauth2.dto.UserPartnerConvertRequestDTO;
import com.mtvs.devlinkbackend.team.dto.TeamRegistRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class UserPartnerRUDTest {
    private static Stream<Arguments> convertUserToUserPartner() {
        return Stream.of(
                Arguments.of(new UserPartnerConvertRequestDTO(

                ), "계정1"),
                Arguments.of(new UserPartnerConvertRequestDTO(

                ), "계정1")
        );
    }
}
