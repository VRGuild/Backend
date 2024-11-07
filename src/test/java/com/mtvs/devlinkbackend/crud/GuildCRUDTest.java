package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.guild.service.GuildService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GuildCRUDTest {

}
