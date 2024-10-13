package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.team.dto.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Team registTeam(TeamRegistRequestDTO teamRegistRequestDTO, String accountId) {
        return teamRepository.save(new Team(
                accountId,
                teamRegistRequestDTO.getTeamName(),
                teamRegistRequestDTO.getIntroduction(),
                teamRegistRequestDTO.getMemberList()
        ));
    }

    public Team findTeamByTeamId(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    public List<Team> findTeamsByPmId(String pmId) {
        return teamRepository.findTeamByPmId(pmId);
    }

    public List<Team> findTeamsByTeamNameContaining(String teamName) {
        return teamRepository.findTeamsByTeamNameContaining(teamName);
    }

    public List<Team> findTeamsByMemberIdContaining(String memberId) {
        return teamRepository.findTeamsByMemberIdContaining(memberId);
    }

    @Transactional
    public Team updateTeam(TeamUpdateRequestDTO teamUpdateRequestDTO, String accountId) {
        Optional<Team> team = teamRepository.findById(teamUpdateRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            if(foundTeam.getPmId().equals(accountId)) {
                foundTeam.setTeamName(teamUpdateRequestDTO.getTeamName());
                foundTeam.setIntroduction(teamUpdateRequestDTO.getIntroduction());
                foundTeam.setMemberList(teamUpdateRequestDTO.getMemberList());
                return foundTeam;
            } else throw new IllegalArgumentException("pm이 아닌 계정으로 team 수정 시도");
        } else throw new IllegalArgumentException("해당 Team은 존재하지 않음");
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}
